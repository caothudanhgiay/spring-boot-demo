package com.example.demo.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.config.DMRabbitMQConfig;
import com.example.demo.enity.DMUser;
import com.example.demo.repository.DMUserRepository;
import com.example.demo.request.DMUserRequest;

// Consumer nơi nhận các message từ rabbitmq và xử lý
@Component
public class DMMessageConsumer {

    @Autowired
    private DMUserRepository userRepository;

    // Lắng nghe liên tục trên Queue Text
    @RabbitListener(queues = DMRabbitMQConfig.TEXT_QUEUE)
    public void consumeMessageFromTextQueue(String payload) {
        System.out.println(
                "📥 [TEXT QUEUE] Đã nhận: " + payload + " | [Phản Hồi]: Cảm ơn, tôi đã nhận được tin nhắn text.");
    }

    // Lắng nghe liên tục trên Queue JSON
    @RabbitListener(queues = DMRabbitMQConfig.JSON_QUEUE)
    public void consumeMessageFromJsonQueue(DMUserRequest payload) {
        System.out.println("📥 [JSON QUEUE] Đã nhận dữ liệu: " + payload);
        try {
            DMUser user = new DMUser();
            if (payload.getName() != null) {
                user.setName(payload.getName());
            }
            if (payload.getEmail() != null) {
                user.setEmail(payload.getEmail());
            }

            userRepository.save(user);
            System.out.println("✅ Đã lưu thông tin User vào Database thành công!");
        } catch (Exception e) {
            System.err.println("❌ Có lỗi xảy ra khi lưu Database: " + e.getMessage());
        }
    }
}
