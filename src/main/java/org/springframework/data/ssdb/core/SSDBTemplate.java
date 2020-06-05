package org.springframework.data.ssdb.core;

import org.springframework.data.ssdb.client.SSDB;
import org.springframework.data.ssdb.client.SSDBCommand;
import org.springframework.data.ssdb.client.SSDBException;
import org.springframework.data.ssdb.pool.SSDBPool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Create by tuke on 2019-05-20
 */
@Component
public class SSDBTemplate extends SSDBTemplateMXBean implements SSDBCommand {

    public SSDBTemplate(SSDBPool ssdbPool) {
        super(ssdbPool);
    }

    @Override
    public void ping() {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.ping();
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void auth(String password) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.auth(password);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public long dbsize() {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.dbsize();
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }

    }

    @Override
    public void flushdb(String... type) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.flushdb(type);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void slaveof(String id, String host, int port, String auth, Long last_seq, String last_key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.slaveof(id, host, port, auth, last_seq, last_key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void set(String key, String value) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.set(key, value);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void setx(String key, String value, long ttl) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.setx(key, value, ttl);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void setnx(String key, String value) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.setnx(key, value);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void expire(String key, long ttl) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.expire(key, ttl);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public long ttl(String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.ttl(key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public String getset(String key, String value) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.getset(key, value);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void del(String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.del(key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public String get(String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.get(key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> scan(String key_start, String key_end, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.scan(key_start, key_end, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> rscan(String key_start, String key_end, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.rscan(key_start, key_end, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public long incr(String key, long by) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.incr(key, by);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public boolean exists(String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.exists(key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> keys(String key_start, String key_end, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.keys(key_start, key_end, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> rkeys(String key_start, String key_end, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.rkeys(key_start, key_end, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void hset(String name, String key, String value) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.hset(name, key, value);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void hdel(String name, String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.hdel(name, key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public String hget(String name, String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.hget(name, key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public Map<String, String> hscan(String name, String key_start, String key_end, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.hscan(name, key_start, key_end, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public Map<String, String> hrscan(String name, String key_start, String key_end, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.hrscan(name, key_start, key_end, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public long hincr(String name, String key, long by) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.hincr(name, key, by);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public boolean hexists(String name, String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.hexists(name, key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public long hsize(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.hsize(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> hkeys(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.hkeys(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void zset(String name, String key, long score) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.zset(name, key, score);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void zdel(String name, String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.zdel(name, key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public Long zget(String name, String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.zget(name, key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public Map<String, Long> zscan(String name, String key_start, long score_start, long score_end, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.zscan(name, key_start, score_start, score_end, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public Map<String, Long> zrscan(String name, String key_start, long score_start, long score_end, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.zrscan(name, key_start, score_start, score_end, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public long zincr(String name, String key, long by) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.zincr(name, key, by);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public boolean zexists(String name, String key) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.zexists(name, key);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public long zsize(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.zsize(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> zkeys(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.zkeys(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> zrange(String name, int offset, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.zrange(name, offset, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> multi_get(String... keys) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.multi_get(keys);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void multi_set(String... kvs) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.multi_set(kvs);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void multi_del(String... keys) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.multi_del(keys);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void qpush_front(String name, String... items) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.qpush_front(name, items);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void qpush_back(String name, String... items) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.qpush_back(name, items);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public String qpop_front(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qpop_front(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public String qpop_back(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qpop_back(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> qpop_front(String name, int size) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qpop_front(name, size);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> qpop_back(String name, int size) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qpop_back(name, size);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public String qfront(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qfront(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public String qback(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qback(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public long qsize(String name) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qsize(name);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public String qget(String name, int index) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qget(name, index);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void qset(String name, int index, String item) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.qset(name, index, item);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> qrange(String name, int offset, int limit) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qrange(name, offset, limit);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public List<String> qslice(String name, int begin, int end) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            return ssdb.qslice(name, begin, end);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void qtrim_front(String name, int size) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.qtrim_front(name, size);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

    @Override
    public void qtrim_back(String name, int size) {
        SSDB ssdb = ssdbPool.getResource();
        try {
            ssdb.qtrim_back(name, size);
        } catch (SSDBException e) {
            ssdbPool.returnBrokenResource(ssdb);
            throw e;
        } finally {
            ssdbPool.returnResource(ssdb);
        }
    }

}
