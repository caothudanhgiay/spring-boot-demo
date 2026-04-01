package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class DMRabbitMQConfig {

    public static final String TEXT_QUEUE = "demo_text_queue";
    public static final String JSON_QUEUE = "demo_json_queue";
    public static final String EXCHANGE = "demo_exchange";
    public static final String TEXT_ROUTING_KEY = "demo_text_routing_key";
    public static final String JSON_ROUTING_KEY = "demo_json_routing_key";

    // 1a. Khởi tạo Text Queue
    @Bean
    public Queue textQueue() {
        return new Queue(TEXT_QUEUE, true);
    }

    // 1b. Khởi tạo JSON Queue
    @Bean
    public Queue jsonQueue() {
        return new Queue(JSON_QUEUE, true);
    }

    // 2. Khởi tạo Exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    // 3a. Bind Text Queue vào Exchange
    @Bean
    public Binding textBinding(@Qualifier("textQueue") Queue textQueue, TopicExchange exchange) {
        return BindingBuilder.bind(textQueue).to(exchange).with(TEXT_ROUTING_KEY);
    }

    // 3b. Bind JSON Queue vào Exchange
    @Bean
    public Binding jsonBinding(@Qualifier("jsonQueue") Queue jsonQueue, TopicExchange exchange) {
        return BindingBuilder.bind(jsonQueue).to(exchange).with(JSON_ROUTING_KEY);
    }

    // 4. (Tùy chọn nhưng khuyên dùng) Cấu hình JSON Converter để gửi Java Object
    @Bean
    @NonNull
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(@NonNull ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
