package org.springframework.data.ssdb;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.ssdb.core.SSDBTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Create by tuke on 2019-05-21
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SSDBPoolTest {
    @Resource
    private SSDBTemplate ssdbTemplate;

    @Test
    public void testPool() throws Exception {

        int taskCount = 64;
        final CountDownLatch latch = new CountDownLatch(taskCount);
        for (int i = 0; i < taskCount; i++) {
            final int k = i;
            new Thread(() -> {
                try {
                    //log.info("enter thread {}", k);
                    log.info("[{}] pool status:\tidle={}, active={}, waiters={}, testsPerEvictionRun={}, " +
                                    "({}, {}, {}, {}, {}, {})",
                            k,
                            ssdbTemplate.getNumIdle(), // 0
                            ssdbTemplate.getNumActive(), // 8
                            ssdbTemplate.getNumWaiters(), // many
                            ssdbTemplate.getNumTestsPerEvictionRun(), // 3

                            ssdbTemplate.getCreatedCount(), // 8
                            ssdbTemplate.getBorrowedCount(), // huge
                            ssdbTemplate.getReturnedCount(), // huge - 8
                            ssdbTemplate.getDestroyedCount(), // 0
                            ssdbTemplate.getDestroyedByEvictorCount(), // 0
                            ssdbTemplate.getDestroyedByBorrowValidationCount() // 0
                    );
                    testNoOutput();
                    testNoOutput();
                    Thread.sleep(3_000);
                    //log.info("exit thread {}", k);
                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
    }

    @Test
    public void testOutput() {
        ssdbTemplate.ping();

        ssdbTemplate.del("foo");
        log.info("foo:\t{}", ssdbTemplate.get("foo"));
        ssdbTemplate.set("foo", "bar");
        log.info("foo:\t{}", ssdbTemplate.get("foo"));

        for (int i = 0; i < 128; i++) {
            //log.info("setting {}", i);
            ssdbTemplate.set(i + "", UUID.randomUUID().toString());
        }

        ssdbTemplate.scan("", "", -1);
        //response.print();
    }

    private void testNoOutput() throws Exception {
        ssdbTemplate.ping();
        ssdbTemplate.del("foo");
        ssdbTemplate.get("foo");
        ssdbTemplate.set("foo", "bar");
        ssdbTemplate.get("foo");

        for (int i = 0; i < 128; i++) {
            ssdbTemplate.set(i + "", UUID.randomUUID().toString());
        }
        ssdbTemplate.scan("", "", -1);
    }
}
