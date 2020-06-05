package org.springframework.data.ssdb.client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Create by tuke on 2019-05-19
 */
public interface SSDBCommand {
    Charset CS = StandardCharsets.UTF_8;

    void ping();

    void auth(String password);

    long dbsize();

    void flushdb(String... type);

    default void slaveof(String id, String host, int port) {
        slaveof(id, host, port, null, null, null);
    }

    /**
     * @param id       - required，master's id
     * @param host     - required，master's hostname or IP address
     * @param port     - required，master's port
     * @param auth     - optional，master's auth code
     * @param last_seq - optional，the start of replication seq
     * @param last_key - optional，the lower bound of data to copy
     */
    void slaveof(String id, String host, int port, String auth, Long last_seq, String last_key);

    void set(String key, String value);

    // ttl number of seconds to live
    void setx(String key, String value, long ttl);

    void setnx(String key, String value);

    void expire(String key, long ttl);

    long ttl(String key);

    String getset(String key, String value);

    void del(String key);

    String get(String key);

    default List<String> scan() {
        return scan("", "");
    }

    default List<String> scan(String key_start, String key_end) {
        return scan(key_start, key_end, -1);
    }

    List<String> scan(String key_start, String key_end, int limit);

    List<String> rscan(String key_start, String key_end, int limit);

    long incr(String key, long by);

    boolean exists(String key);

    List<String> keys(String key_start, String key_end, int limit);

    default List<String> keys() {
        return keys("", "", -1);
    }

    List<String> rkeys(String key_start, String key_end, int limit);

    default List<String> rkeys() {
        return rkeys("", "", -1);
    }

    void hset(String name, String key, String value);

    void hdel(String name, String key);

    String hget(String name, String key);

    Map<String, String> hscan(String name, String key_start, String key_end, int limit);

    Map<String, String> hrscan(String name, String key_start, String key_end, int limit);

    long hincr(String name, String key, long by);

    boolean hexists(String name, String key);

    long hsize(String name);

    List<String> hkeys(String name);

    void zset(String name, String key, long score);

    void zdel(String name, String key);

    Long zget(String name, String key);

    Map<String, Long> zscan(String name, String key_start, long score_start, long score_end, int limit);

    Map<String, Long> zrscan(String name, String key_start, long score_start, long score_end, int limit);

    long zincr(String name, String key, long by);

    boolean zexists(String name, String key);

    long zsize(String name);

    List<String> zkeys(String name);

    List<String> zrange(String name, int offset, int limit);

    List<String> multi_get(String... keys);

    void multi_set(String... kvs);

    void multi_del(String... keys);

    void qpush_front(String name, String... items);

    void qpush_back(String name, String... items);

    String qpop_front(String name);

    String qpop_back(String name);

    List<String> qpop_front(String name, int size);

    List<String> qpop_back(String name, int size);

    default void qpush(String name, String... items) {
        qpush_back(name, items);
    }

    default String qpop(String name) {
        return qpop_front(name);
    }

    default List<String> qpop(String name, int size) {
        return qpop_front(name, size);
    }

    String qfront(String name);

    String qback(String name);

    long qsize(String name);

    String qget(String name, int index);

    void qset(String name, int index, String item);

    List<String> qrange(String name, int offset, int limit);

    List<String> qslice(String name, int begin, int end);

    void qtrim_front(String name, int size);

    void qtrim_back(String name, int size);

}
