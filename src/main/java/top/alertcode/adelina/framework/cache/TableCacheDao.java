package top.alertcode.adelina.framework.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 表的缓存，主要操作redis hset结构
 */
@Component
public class TableCacheDao {

    @Autowired
    private RedisTemplate redisTemplate;

    private HashOperations hashOperations;

    public TableCacheDao() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void add(String tableName, String key, String value) {
        hashOperations.put(tableName, key, value);
    }

    public void addAll(String tableName, Map<String, String> entry) {
        hashOperations.putAll(tableName, entry);
    }

    /**
     * @param tableName
     * @param key
     * @param value
     * @param expire    失效时间
     * @return
     */
    public void add(String tableName, String key, String value, int expire) {
        hashOperations.put(tableName, key, value);
        redisTemplate.expire(tableName, expire, TimeUnit.SECONDS);
    }

    public void addAll(String tableName, Map<String, String> entry, int expire) {
        hashOperations.putAll(tableName, entry);
        redisTemplate.expire(tableName, expire, TimeUnit.SECONDS);
    }

    public long delete(String tableName, String... keys) {
        return redisTemplate.opsForHash().delete(tableName, keys);
    }

    public boolean exists(String tableName, String key) {
        return hashOperations.hasKey(tableName, key);
    }

    public long size(String tableName) {
        return hashOperations.size(tableName);
    }

    public List<String> get(String tableName, String... keys) {
        return hashOperations.multiGet(tableName, Arrays.asList(keys));
    }

    public String get(String tableName, String key) {
        return Objects.toString(hashOperations.get(tableName, key));
    }

    public Map<String, String> getAll(String tableName) {
        return hashOperations.entries(tableName);
    }

    public Set<String> keys(String tableName) {
        return hashOperations.keys(tableName);
    }

    /*hash类型:返回 key 指定的哈希集包含的字段的数量*/
    public List<String> values(String tableName) {
        return hashOperations.values(tableName);
    }


}
