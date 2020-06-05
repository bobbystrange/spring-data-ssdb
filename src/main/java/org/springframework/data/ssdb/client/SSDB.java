package org.springframework.data.ssdb.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

public class SSDB implements SSDBCommand, Closeable {
    @Getter
    @Setter
    boolean hasReturnResource = false;
    private SSDBLink link;
    @Getter
    private String host;
    @Getter
    private int port;

    public SSDB(String host, int port) {
        this(host, port, 0);
    }

    public SSDB(String host, int port, int timeout_ms) {
        this.host = host;
        this.port = port;
        link = new SSDBLink(host, port, timeout_ms);
    }

    public void close() {
        link.close();
    }

    /* auth */

    @Override
    public void ping() {
        link.request("ping").getAsVoid();
    }

    public void auth(String password) {
        link.request("auth", password).getAsVoid();
    }

    @Override
    public long dbsize() {
        return link.request("dbsize").getAsLong();
    }

    @Override
    public void flushdb(String... types) {
        link.request("flushdb", types).getAsVoid();
    }

    @Override
    public void slaveof(String id, String host, int port, String auth, Long last_seq, String last_key) {
        SSDBResponse resp;
        if (last_key != null)
            resp = link.request("slaveof", id, host, port + "", auth, last_seq + "", last_key + "");
        else if (last_seq != null)
            resp = link.request("slaveof", id, host, port + "", auth, last_seq + "");
        else if (auth != null)
            resp = link.request("slaveof", id, host, port + "", auth);
        else
            resp = link.request("slaveof", id, host, port + "");
        resp.getAsVoid();
    }

    /* kv */

    public void set(String key, String val) {
        link.request("set", key, val).getAsVoid();
    }

    @Override
    public void setx(String key, String value, long ttl) {
        link.request("setx", key, value, ttl + "").getAsVoid();
    }

    @Override
    public void setnx(String key, String value) {
        link.request("setbx", key, value).getAsVoid();
    }

    @Override
    public void expire(String key, long ttl) {
        link.request("expire", key, ttl + "").getAsVoid();
    }

    @Override
    public long ttl(String key) {
        return link.request("ttl", key).getAsLong();
    }

    @Override
    public String getset(String key, String value) {
        return link.request("getset", key, value).getAsString();
    }

    public void del(String key) {
        link.request("del", key).getAsVoid();
    }

    public String get(String key) {
        return link.request("get", key).getAsString();
    }

    private List<String> _scan(String cmd, String key_start, String key_end, int limit) {
        if (key_start == null) {
            key_start = "";
        }
        if (key_end == null) {
            key_end = "";
        }
        return link.request(cmd, key_start, key_end, (new Integer(limit)).toString()).getAsList();
    }

    public List<String> scan(String key_start, String key_end, int limit) {
        return _scan("scan", key_start, key_end, limit);
    }

    public List<String> rscan(String key_start, String key_end, int limit) {
        return _scan("rscan", key_start, key_end, limit);
    }

    public long incr(String key, long by) {
        return link.request("incr", key, (new Long(by)).toString()).getAsLong();
    }

    @Override
    public boolean exists(String key) {
        return link.request("exists", key).getAsBoolean();
    }

    @Override
    public List<String> keys(String key_start, String key_end, int limit) {
        return link.request("keys", key_start, key_end, limit + "").getAsList();
    }

    @Override
    public List<String> rkeys(String key_start, String key_end, int limit) {
        return link.request("rkeys", key_start, key_end, limit + "").getAsList();
    }

    /* hashmap */

    public void hset(String name, String key, String val) {
        link.request("hset", name, key, val).getAsVoid();
    }

    public void hdel(String name, String key) {
        link.request("hdel", name, key).getAsVoid();

    }

    public String hget(String name, String key) {
        return link.request("hget", name, key).getAsString();
    }

    private Map<String, String> _hscan(String cmd, String name, String key_start, String key_end, int limit) {
        if (key_start == null) {
            key_start = "";
        }
        if (key_end == null) {
            key_end = "";
        }
        return link.request(cmd, name, key_start, key_end, (new Integer(limit)).toString()).getAsHashMap();
    }

    public Map<String, String> hscan(String name, String key_start, String key_end, int limit) {
        return this._hscan("hscan", name, key_start, key_end, limit);
    }

    public Map<String, String> hrscan(String name, String key_start, String key_end, int limit) {
        return this._hscan("hrscan", name, key_start, key_end, limit);
    }

    public long hincr(String name, String key, long by) {
        return link.request("hincr", name, key, (new Long(by)).toString()).getAsLong();
    }

    @Override
    public boolean hexists(String name, String key) {
        return link.request("hexists", name, key).getAsBoolean();
    }

    @Override
    public long hsize(String name) {
        return link.request("hsize", name).getAsLong();
    }

    @Override
    public List<String> hkeys(String name) {
        return link.request("hkeys", name).getAsList();
    }

    /* zset */

    public void zset(String name, String key, long score) {
        link.request("zset", name, key, (new Long(score)).toString()).getAsVoid();
    }

    public void zdel(String name, String key) {
        link.request("zdel", name, key).getAsVoid();
    }

    public Long zget(String name, String key) {
        return link.request("zget", name, key).getAsLong();
    }

    private Map<String, Long> _zscan(String cmd, String name, String key_start, Long score_start, Long score_end, int limit) {
        String ss = "";
        if (score_start != null) {
            ss = score_start.toString();
        }
        String se = "";
        if (score_end != null) {
            se = score_end.toString();
        }
        return link.request(cmd, name, key_start, ss, se, (new Integer(limit)).toString()).getAsZset();
    }

    public Map<String, Long> zscan(String name, String key_start, long score_start, long score_end, int limit) {
        return this._zscan("zscan", name, key_start, score_start, score_end, limit);
    }

    public Map<String, Long> zrscan(String name, String key_start, long score_start, long score_end, int limit) {
        return this._zscan("zrscan", name, key_start, score_start, score_end, limit);
    }

    public long zincr(String name, String key, long by) {
        return link.request("zincr", name, key, by + "").getAsLong();
    }

    @Override
    public boolean zexists(String name, String key) {
        return link.request("zexists", name, key).getAsBoolean();
    }

    @Override
    public long zsize(String name) {
        return link.request("zsize", name).getAsLong();
    }

    @Override
    public List<String> zkeys(String name) {
        return link.request("zkeys", name).getAsList();
    }

    @Override
    public List<String> zrange(String name, int offset, int limit) {
        return link.request("zrange", name, offset + "", limit + "").getAsList();
    }

    /* multi */

    public List<String> multi_get(String... keys) {
        return link.request("multi_get", keys).getAsList();
    }

    public void multi_set(String... kvs) {
        if (kvs.length % 2 != 0) {
            throw new SSDBException("Invalid arguments count");
        }
        link.request("multi_set", kvs).getAsVoid();
    }

    public void multi_del(String... keys) {
        link.request("multi_del", keys).getAsVoid();
    }

    @Override
    public void qpush_front(String name, String... items) {
        String[] args = new String[items.length + 1];
        System.arraycopy(items, 0, args, 1, items.length);
        args[0] = name;
        link.request("qpush_front", args).getAsVoid();
    }

    @Override
    public void qpush_back(String name, String... items) {
        String[] args = new String[items.length + 1];
        System.arraycopy(items, 0, args, 1, items.length);
        args[0] = name;
        link.request("qpush_back", args).getAsVoid();
    }

    @Override
    public String qpop_front(String name) {
        return link.request("qpop_front", name).getAsString();
    }

    @Override
    public String qpop_back(String name) {
        return link.request("qpop_back", name).getAsString();
    }

    @Override
    public List<String> qpop_front(String name, int size) {
        return link.request("qpop_front", name, size + "").getAsList();
    }

    @Override
    public List<String> qpop_back(String name, int size) {
        return link.request("qpop_back", name, size + "").getAsList();
    }

    @Override
    public String qfront(String name) {
        return link.request("qfront", name).getAsString();
    }

    @Override
    public String qback(String name) {
        return link.request("qback", name).getAsString();
    }

    @Override
    public long qsize(String name) {
        return link.request("qsize", name).getAsLong();
    }

    @Override
    public String qget(String name, int index) {
        return link.request("qget", name, index + "").getAsString();

    }

    @Override
    public void qset(String name, int index, String item) {
        link.request("qset", name, index + "", item).getAsVoid();
    }

    @Override
    public List<String> qrange(String name, int offset, int limit) {
        return link.request("qrange", name, offset + "", limit + "").getAsList();
    }

    @Override
    public List<String> qslice(String name, int begin, int end) {
        return link.request("qslice", name, begin + "", end + "").getAsList();
    }

    @Override
    public void qtrim_front(String name, int size) {
        link.request("qtrim_front", name, size + "").getAsVoid();
    }

    @Override
    public void qtrim_back(String name, int size) {
        link.request("qtrim_back", name, size + "").getAsVoid();
    }

}
