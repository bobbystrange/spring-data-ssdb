package org.springframework.data.ssdb;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.ssdb.core.SSDBTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Create by tuke on 2019-05-21
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SSDBCommandTest {

    @Resource
    private SSDBTemplate ssdbTemplate;

    @Test
    public void multi() {
        for (char c = 'a'; c <= 'f'; c++) {
            for (char i = 'a'; i <= 'c'; i++) {
                String key = c + "" + i;
                ssdbTemplate.set(key, c % 4 + "");

                String v = ssdbTemplate.get(key);
                log.info("get {} {}", key, v);

                if (c % 4 == 0) {
                    ssdbTemplate.zdel("chars:zset", key);
                }
            }
        }

        ssdbTemplate.scan("", "", -1).forEach(System.out::println);
    }

    @Test
    public void zset() {
        for (char c = 'a'; c <= 'f'; c++) {
            for (char i = 'a'; i <= 'c'; i++) {
                String key = c + "" + i;
                ssdbTemplate.zset("chars:zset", key, c % 4);

                Long score = ssdbTemplate.zget("chars:zset", key);
                log.info("zset chars:zset {} {}", key, score);

                if (score == 0) {
                    ssdbTemplate.zdel("chars:zset", key);
                } else {
                    ssdbTemplate.zincr("chars:zset", key, score * score);

                }
            }
        }

        ssdbTemplate.zscan("chars:zset", "", 0, 16, -1)
                .forEach((k, v) -> System.out.println(k + "\t" + v));

        System.out.println("\n\n");
        ssdbTemplate.zrange("chars:zset", 0, 16)
                .forEach(System.out::println);
    }

    @Test
    public void list() {
        for (char c = 'a'; c <= 'f'; c++) {
            for (char i = 'a'; i <= 'c'; i++) {
                String key = c + "" + i;
                ssdbTemplate.qpush("chars:list", key);

                String key2 = ssdbTemplate.qback("chars:list");
                log.info("list chars:list {} {}", key, key2);

                if (i % 4 == 0) {
                    ssdbTemplate.zdel("chars:list", key);
                }
            }
        }

        ssdbTemplate.qrange("chars:list", 0, 16)
                .forEach(System.out::println);

        System.out.println("\n\n");
        ssdbTemplate.qpop("chars:list", 8)
                .forEach(System.out::println);
        System.out.println("\n\n");
        ssdbTemplate.qrange("chars:list", 0, -1)
                .forEach(System.out::println);
    }

}
