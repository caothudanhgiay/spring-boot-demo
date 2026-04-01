package com.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class DMKafkaConfig {

    public static final String TEXT_TOPIC = "demo_kafka_text_topic";
    public static final String JSON_TOPIC = "demo_kafka_json_topic";

    // Khởi tạo Topic cho String
    @Bean
    public NewTopic textTopic() {
        return TopicBuilder.name(TEXT_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    // Khởi tạo Topic cho JSON Object
    @Bean
    public NewTopic jsonTopic() {
        return TopicBuilder.name(JSON_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
