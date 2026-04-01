package com.example.demo.component;

import com.example.demo.config.DMKafkaConfig;
import com.example.demo.request.DMUserRequest;
import com.example.demo.enity.DMUser;
import com.example.demo.repository.DMUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DMKafkaConsumer {
    @Autowired
    private DMUserRepository dmUserRepository;

    @KafkaListener(topics = DMKafkaConfig.TEXT_TOPIC, groupId = "demo-kafka-group")
    public void receiveTextMessage(String message) {
        System.out.println("📥 [KAFKA CONSUMER] Đã nhận text message: " + message);
    }

    // Nếu cấu hình JSON đúng, Spring Kafka tự động map chuỗi JSON thành Object tương ứng
    @KafkaListener(topics = DMKafkaConfig.JSON_TOPIC, groupId = "demo-kafka-group")
    public void receiveJsonObject(DMUserRequest request) {
        System.out.println("📥 [KAFKA CONSUMER] Đã nhận JSON Object - User: " + request.getName() + ", Email: " + request.getEmail());

        // Chuyển đổi từ Request sang Entity để lưu vào DB
        DMUser user = new DMUser();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        dmUserRepository.save(user);
        System.out.println("✅ [KAFKA CONSUMER] Đã lưu User vào Database!");
    }
}
