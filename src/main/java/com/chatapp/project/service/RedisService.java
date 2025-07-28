package com.chatapp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private static final String USER_CONNECTION_PREFIX = "user:conn:";
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Lưu object vào Redis
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Lấy object từ Redis
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Xoá key
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    public void increaseConnection(String username) {
        String key = USER_CONNECTION_PREFIX + username;
        redisTemplate.opsForValue().increment(key);
    }
    public void decreaseConnection(String username) {
        String key = USER_CONNECTION_PREFIX + username;
        Long count = redisTemplate.opsForValue().decrement(key);

        if (count != null && count <= 0) {
            redisTemplate.delete(key); // offline hoàn toàn
        }
    }
    public boolean isUserOnline(String username) {
        String key = USER_CONNECTION_PREFIX + username;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null && Long.parseLong(value.toString()) > 0;
    }
    public Long getConnectionCount(String username) {
        String key = USER_CONNECTION_PREFIX + username;
        Object val = redisTemplate.opsForValue().get(key);
        return val != null ? Long.parseLong(val.toString()) : 0L;
    }
}
