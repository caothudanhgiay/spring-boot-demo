package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DMRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Lưu giá trị vào Redis với key cụ thể.
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Lưu giá trị vào Redis với key cụ thể và thời gian hết hạn (TTL).
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * Lấy giá trị từ Redis bằng key.
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Kiểm tra sự tồn tại của key trong Redis.
     */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Xóa key từ Redis.
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
