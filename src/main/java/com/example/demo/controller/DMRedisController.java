package com.example.demo.controller;

import com.example.demo.service.DMRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class DMRedisController {

    private final DMRedisService redisService;

    /**
     * Endpoint để lưu trữ một giá trị vào Redis.
     * Ví dụ: POST /api/redis/set?key=myKey&value=myValue
     */
    @PostMapping("/set")
    public String setValue(@RequestParam("key") String key, @RequestParam("value") String value) {
        redisService.set(key, value);
        return "Giá trị đã được lưu vào Redis thành công cho key: " + key;
    }

    /**
     * Endpoint để lấy giá trị từ Redis.
     * Ví dụ: GET /api/redis/get/myKey
     */
    @GetMapping("/get/{key}")
    public Object getValue(@PathVariable("key") String key) {
        Object value = redisService.get(key);
        if (value == null) {
            return Map.of("error", "Không tìm thấy key trong Redis");
        }
        return Map.of("key", key, "value", value);
    }

    /**
     * Endpoint để xóa một key khỏi Redis.
     * Ví dụ: DELETE /api/redis/delete/myKey
     */
    @DeleteMapping("/delete/{key}")
    public String deleteKey(@PathVariable("key") String key) {
        if (redisService.exists(key)) {
            redisService.delete(key);
            return "Key '" + key + "' đã được xóa khỏi Redis";
        }
        return "Key '" + key + "' không tồn tại trong Redis";
    }
}
