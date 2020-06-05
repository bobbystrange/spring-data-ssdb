package org.springframework.data.ssdb;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.ssdb.configuration.EnableSSDBRepositories;
import org.springframework.data.ssdb.core.SSDBTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootConfiguration
@EnableSSDBRepositories
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataSSDBApplicationTests {

    @Resource
    private SSDBTemplate ssdbTemplate;

    @Test
    public void contextLoads() throws Exception {

    }
}
