package org.springframework.data.ssdb.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by tuke on 2019-05-20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan(value = {
        "org.springframework.data.ssdb.configuration"
})
@Import(SSDBRepositoriesConfiguration.class)
public @interface EnableSSDBRepositories {
}
