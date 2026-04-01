package com.example.demo.controller;

import com.example.demo.request.DMUserRequest;
import com.example.demo.service.DMKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class DMKafkaController {

    @Autowired
    private DMKafkaProducer kafkaProducer;

    @PostMapping("/send-text")
    public String sendText(@RequestParam("message") String message) {
        kafkaProducer.sendTextMessage(message);
        return "🔥 Kafka: Đã gửi text message lên topic!";
    }

    @PostMapping("/send-json")
    public String sendJson(@RequestBody DMUserRequest data) {
        kafkaProducer.sendJsonObject(data);
        return "🔥 Kafka: Đã gửi JSON object lên topic!";
    }
}
