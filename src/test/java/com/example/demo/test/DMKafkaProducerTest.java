package com.example.demo.test;

import com.example.demo.config.DMKafkaConfig;
import com.example.demo.service.DMKafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class DMKafkaProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private DMKafkaProducer kafkaProducer;

    @Test
    public void testSendTextMessage() {
        String message = "Hello Kafka";

        // Call the method
        kafkaProducer.sendTextMessage(message);

        // Verify that kafkaTemplate.send(...) was called exactly once with expected arguments
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(DMKafkaConfig.TEXT_TOPIC, message);
    }

    @Test
    public void testSendJsonObject() {
        // Sample data object
        Object data = new Object();

        // Call the method
        kafkaProducer.sendJsonObject(data);

        // Verify that kafkaTemplate.send(...) was called exactly once with expected arguments
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(DMKafkaConfig.JSON_TOPIC, data);
    }
}
