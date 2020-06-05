package org.springframework.data.ssdb.core;

import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.DefaultPooledObjectInfo;
import org.apache.commons.pool2.impl.GenericObjectPoolMXBean;
import org.springframework.data.ssdb.pool.SSDBPool;

import java.util.Set;

/**
 * Create by tuke on 2019-05-21
 */
@RequiredArgsConstructor
public class SSDBTemplateMXBean implements GenericObjectPoolMXBean {
    protected final SSDBPool ssdbPool;

    @Override
    public boolean getBlockWhenExhausted() {
        return ssdbPool.getBlockWhenExhausted();
    }

    @Override
    public boolean getFairness() {
        return ssdbPool.getFairness();
    }

    @Override
    public boolean getLifo() {
        return ssdbPool.getLifo();
    }

    @Override
    public int getMaxIdle() {
        return ssdbPool.getMaxIdle();
    }

    @Override
    public int getMaxTotal() {
        return ssdbPool.getMaxTotal();
    }

    @Override
    public long getMaxWaitMillis() {
        return ssdbPool.getMaxWaitMillis();
    }

    @Override
    public long getMinEvictableIdleTimeMillis() {
        return ssdbPool.getMinEvictableIdleTimeMillis();
    }

    @Override
    public int getMinIdle() {
        return ssdbPool.getMinIdle();
    }

    @Override
    public int getNumActive() {
        return ssdbPool.getNumActive();
    }

    @Override
    public int getNumIdle() {
        return ssdbPool.getNumIdle();
    }

    @Override
    public int getNumTestsPerEvictionRun() {
        return ssdbPool.getNumTestsPerEvictionRun();
    }

    @Override
    public boolean getTestOnCreate() {
        return ssdbPool.getTestOnCreate();
    }

    @Override
    public boolean getTestOnBorrow() {
        return ssdbPool.getTestOnBorrow();
    }

    @Override
    public boolean getTestOnReturn() {
        return ssdbPool.getTestOnReturn();
    }

    @Override
    public boolean getTestWhileIdle() {
        return ssdbPool.getTestWhileIdle();
    }

    @Override
    public long getTimeBetweenEvictionRunsMillis() {
        return ssdbPool.getTimeBetweenEvictionRunsMillis();
    }

    @Override
    public boolean isClosed() {
        return ssdbPool.isClosed();
    }

    @Override
    public long getBorrowedCount() {
        return ssdbPool.getBorrowedCount();
    }

    @Override
    public long getReturnedCount() {
        return ssdbPool.getReturnedCount();
    }

    @Override
    public long getCreatedCount() {
        return ssdbPool.getCreatedCount();
    }

    @Override
    public long getDestroyedCount() {
        return ssdbPool.getDestroyedCount();
    }

    @Override
    public long getDestroyedByEvictorCount() {
        return ssdbPool.getDestroyedByEvictorCount();
    }

    @Override
    public long getDestroyedByBorrowValidationCount() {
        return ssdbPool.getDestroyedByBorrowValidationCount();
    }

    @Override
    public long getMeanActiveTimeMillis() {
        return ssdbPool.getMeanActiveTimeMillis();
    }

    @Override
    public long getMeanIdleTimeMillis() {
        return ssdbPool.getMeanIdleTimeMillis();
    }

    @Override
    public long getMeanBorrowWaitTimeMillis() {
        return ssdbPool.getMeanBorrowWaitTimeMillis();
    }

    @Override
    public long getMaxBorrowWaitTimeMillis() {
        return ssdbPool.getMaxBorrowWaitTimeMillis();
    }

    @Override
    public String getCreationStackTrace() {
        return ssdbPool.getCreationStackTrace();
    }

    @Override
    public int getNumWaiters() {
        return ssdbPool.getNumWaiters();
    }

    @Override
    public boolean isAbandonedConfig() {
        return ssdbPool.isAbandonedConfig();
    }

    @Override
    public boolean getLogAbandoned() {
        return ssdbPool.getLogAbandoned();
    }

    @Override
    public boolean getRemoveAbandonedOnBorrow() {
        return ssdbPool.getRemoveAbandonedOnBorrow();
    }

    @Override
    public boolean getRemoveAbandonedOnMaintenance() {
        return ssdbPool.getRemoveAbandonedOnMaintenance();
    }

    @Override
    public int getRemoveAbandonedTimeout() {
        return ssdbPool.getRemoveAbandonedTimeout();
    }

    @Override
    public String getFactoryType() {
        return ssdbPool.getFactoryType();
    }

    @Override
    public Set<DefaultPooledObjectInfo> listAllObjects() {
        return ssdbPool.listAllObjects();
    }
}
