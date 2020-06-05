package org.springframework.data.ssdb.pool;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.data.ssdb.client.SSDB;
import org.springframework.data.ssdb.client.SSDBException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Create by tuke on 2019-05-21
 */
@Slf4j
@Data
@NoArgsConstructor
public class SSDBPooledObjectFactory implements PooledObjectFactory<SSDB> {

    private final ConcurrentLinkedQueue<HostAndPort> availableQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<HostAndPort> deadQueue = new ConcurrentLinkedQueue<>();
    private final AtomicReference<HostAndPort> addressAtomicReference = new AtomicReference<>();
    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private int soTimeout;
    private String password;

    @Override
    public PooledObject<SSDB> makeObject() throws Exception {
        HostAndPort address;

        if (availableQueue.isEmpty()) {
            submitCheckDeadQueue();
        }

        address = availableQueue.poll();

        if (address == null) {
            address = addressAtomicReference.get();
        } else {
            HostAndPort newAddress = addressAtomicReference.getAndSet(address);
            synchronized (availableQueue) {
                if (newAddress != null) {
                    availableQueue.offer(newAddress);
                }
            }
        }

        if (address == null) {
            throw new SSDBException("no available addresses");
        }
        SSDB ssdb = null;
        try {
            ssdb = new SSDB(address.getHost(), address.getPort(), soTimeout);

            if (password != null) {
                ssdb.auth(password);
            }
        } catch (Exception e) {
            if (ssdb != null) {
                ssdb.close();
            }
            synchronized (availableQueue) {
                availableQueue.remove(address);
            }
            synchronized (deadQueue) {
                if (!deadQueue.contains(address)) {
                    deadQueue.offer(address);
                }
            }
            throw e;
        }

        return new DefaultPooledObject<>(ssdb);
    }

    @Override
    public void destroyObject(PooledObject<SSDB> p) throws Exception {
        try {
            p.getObject().close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public boolean validateObject(PooledObject<SSDB> p) {
        SSDB ssdb = p.getObject();
        try {
            HostAndPort address = this.addressAtomicReference.get();
            String host = ssdb.getHost();
            int port = address.getPort();
            boolean eq = address.getHost().equals(host) && address.getPort() == port;
            if (!eq) return false;

            log.info("ping when validate the socket:\t{}", address);
            ssdb.ping();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void activateObject(PooledObject<SSDB> p) throws Exception {
        // The default implementation is a no-op.
    }

    @Override
    public void passivateObject(PooledObject p) throws Exception {
        // The default implementation is a no-op.
    }

    public void initialize(List<HostAndPort> hostAndPorts) {
        for (HostAndPort hostAndPort : hostAndPorts) {
            decideDeadOrAlive(hostAndPort);
        }
    }

    private void submitCheckDeadQueue() {
        es.submit(this::checkDeadQueue);
    }

    private void checkDeadQueue() {
        boolean debug = log.isDebugEnabled();
        if (debug) {
            log.info("before checkDeadQueue, availableQueue {} and deadQueue {}", availableQueue, deadQueue);
        }
        List<HostAndPort> hostAndPorts = new ArrayList<>(deadQueue);
        deadQueue.clear();
        for (HostAndPort hostAndPort : hostAndPorts) {
            decideDeadOrAlive(hostAndPort);
        }
        if (debug) {
            log.info("after checkDeadQueue, availableQueue {} and deadQueue {}", availableQueue, deadQueue);
        }
    }

    private void decideDeadOrAlive(HostAndPort hostAndPort) {
        try (SSDB ssdb = new SSDB(hostAndPort.getHost(), hostAndPort.getPort(), soTimeout)) {
            ssdb.ping();
            synchronized (availableQueue) {
                if (!availableQueue.contains(hostAndPort)) {
                    availableQueue.offer(hostAndPort);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            synchronized (deadQueue) {
                if (!deadQueue.contains(hostAndPort)) {
                    deadQueue.offer(hostAndPort);
                }
            }
        }
    }

}
