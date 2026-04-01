package com.example.demo.service;

import com.example.demo.config.DMRabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Producer nơi nhận các request message và ném vào rabbitmq
@Service
public class DMMessageProducer {

    @Autowired
    private RabbitTemplate template;

    public void sendMessage(String message) {
        // Gửi một chuỗi text đơn giản
        template.convertAndSend(DMRabbitMQConfig.EXCHANGE, DMRabbitMQConfig.TEXT_ROUTING_KEY, message);
        System.out.println("🚀 Đã gửi message: " + message);
    }

    public void sendObject(Object data) {
        // Có thể gửi trực tiếp một Object (Map, DTO, Entity...) vì đã cấu hình JSON
        // converter
        template.convertAndSend(DMRabbitMQConfig.EXCHANGE, DMRabbitMQConfig.JSON_ROUTING_KEY, data);
        System.out.println("🚀 Đã gửi data object: " + data);
    }
}
