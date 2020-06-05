package org.springframework.data.ssdb.configuration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.data.ssdb.client.SSDB;
import org.springframework.data.ssdb.core.SSDBTemplate;
import org.springframework.data.ssdb.pool.HostAndPort;
import org.springframework.data.ssdb.pool.SSDBPool;
import org.springframework.data.ssdb.pool.SSDBPooledObjectFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by tuke on 2019-05-21
 */
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class SSDBRepositoriesConfiguration {

    @Resource
    private SSDBProperties properties;

    @ConditionalOnMissingBean
    @Bean
    public SSDBPool ssdbPool() {
        String host = properties.getHost();
        int port = properties.getPort();
        String addresses = properties.getAddresses();

        int soTimeout = properties.getSoTimeout();
        String password = properties.getPassword();

        SSDBPooledObjectFactory factory = new SSDBPooledObjectFactory();
        if (soTimeout > 0)
            factory.setSoTimeout(soTimeout);
        factory.setPassword(password);

        List<HostAndPort> hostAndPorts = new ArrayList<>();
        if (StringUtils.isEmpty(addresses)) {
            hostAndPorts.add(new HostAndPort(host, port));
        } else {
            Arrays.stream(addresses.split(","))
                    .map(String::trim)
                    .map(it -> it.split(":")).forEach(it -> {
                hostAndPorts.add(new HostAndPort(it[0], Integer.valueOf(it[1])));
            });
        }

        factory.initialize(hostAndPorts);

        GenericObjectPoolConfig<SSDB> config = new GenericObjectPoolConfig<>();
        return new SSDBPool(factory, config);
    }

    @ConditionalOnMissingBean
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SSDBTemplate ssdbTemplate(
            @Autowired SSDBPool ssdbPool) {
        return new SSDBTemplate(ssdbPool);
    }

}
