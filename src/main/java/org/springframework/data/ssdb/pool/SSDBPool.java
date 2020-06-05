package org.springframework.data.ssdb.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.ssdb.client.SSDB;
import org.springframework.data.ssdb.client.SSDBException;

import java.io.Closeable;
import java.util.NoSuchElementException;

/**
 * Create by tuke on 2019-05-21
 */
public class SSDBPool extends GenericObjectPool<SSDB> implements Closeable {

    public SSDBPool(PooledObjectFactory<SSDB> factory) {
        super(factory);
    }

    public SSDBPool(PooledObjectFactory<SSDB> factory, GenericObjectPoolConfig<SSDB> config) {
        super(factory, config);
    }

    public SSDBPool(PooledObjectFactory<SSDB> factory, GenericObjectPoolConfig<SSDB> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }

    public SSDB getResource() {
        try {
            SSDB ssdb = borrowObject();
            ssdb.setHasReturnResource(false);
            return ssdb;
        } catch (NoSuchElementException nse) {
            if (null == nse.getCause()) { // The exception was caused by an exhausted pool
                throw new SSDBException(
                        "Could not get a resource since the pool is exhausted", nse);
            }
            // Otherwise, the exception was caused by the implemented activateObject() or ValidateObject()
            throw new SSDBException("Could not get a resource from the pool", nse);
        } catch (Exception e) {
            throw new SSDBException("Could not get a resource from the pool, caused by " + e.getMessage());
        }
    }

    public void returnResource(final SSDB resource) {
        if (resource != null) {
            if (resource.isHasReturnResource()) return;
            try {
                try {
                    returnObject(resource);
                } catch (Exception e) {
                    throw new SSDBException("Could not return the resource to the pool", e);
                }
            } catch (Exception e) {
                returnBrokenResource(resource);
                throw new SSDBException("Resource is returned to the pool as broken", e);
            }
        }
    }

    public void returnBrokenResource(final SSDB resource) {

        if (resource != null) {
            try {
                // avoid to call returnResource
                resource.setHasReturnResource(true);
                invalidateObject(resource);
            } catch (Exception e) {
                throw new SSDBException("Could not return the broken resource to the pool", e);
            }
        }
    }

}
