package com.example.demo.service;

import com.example.demo.config.DMKafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DMKafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTextMessage(String message) {
        kafkaTemplate.send(DMKafkaConfig.TEXT_TOPIC, message);
        System.out.println("🚀 [KAFKA] Đã gửi text message: " + message);
    }

    public void sendJsonObject(Object data) {
        kafkaTemplate.send(DMKafkaConfig.JSON_TOPIC, data);
        System.out.println("🚀 [KAFKA] Đã gửi JSON object: " + data);
    }
}
