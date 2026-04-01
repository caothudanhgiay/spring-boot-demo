package com.example.demo.test;

import com.example.demo.component.DMKafkaConsumer;
import com.example.demo.config.DMKafkaConfig;
import com.example.demo.service.DMKafkaProducer;
import com.example.demo.request.DMUserRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import com.example.demo.repository.DMUserRepository;
import org.junit.jupiter.api.Assertions;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@SpringBootTest(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.auto-offset-reset=earliest"
})
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = { DMKafkaConfig.TEXT_TOPIC, DMKafkaConfig.JSON_TOPIC })
public class DMKafkaIntegrationTest {

    @Autowired
    private DMKafkaProducer kafkaProducer;
    
    @Autowired
    private DMUserRepository dmUserRepository;

    // Sử dụng Spy để theo dõi bean DMKafkaConsumer có sẵn trong Spring Context
    @SpyBean
    private DMKafkaConsumer kafkaConsumer;

    @Test
    public void testProducerToConsumerTextFlow() {
        String testMessage = "Xin chao tu bai test Tich Hop";

        // 1. Dùng Producer đẩy tin vào môi trường Kafka giả lập
        kafkaProducer.sendTextMessage(testMessage);

        // 2. Sử dụng Awaitility để đợi Consumer nhận được tin nhắn (tối đa 10s)
        await().atMost(10, SECONDS).untilAsserted(() -> {
            Mockito.verify(kafkaConsumer, Mockito.times(1)).receiveTextMessage(testMessage);
        });
    }

    @Test
    public void testProducerToConsumerJsonFlow() {
        DMUserRequest userRequest = new DMUserRequest();
        userRequest.setId(1L);
        userRequest.setName("test1");
        userRequest.setEmail("test1@example.com");

        // 1. Gửi JSON object
        kafkaProducer.sendJsonObject(userRequest);

        // 2. Kiểm chứng Consumer đã nhận được đúng object này
        await().atMost(10, SECONDS).untilAsserted(() -> {
            Mockito.verify(kafkaConsumer, Mockito.times(1)).receiveJsonObject(Mockito.any(DMUserRequest.class));
            
            // 3. Kiểm chứng dữ liệu đã thực sự được lưu vào Database
            boolean exists = dmUserRepository.findAll().stream()
                    .anyMatch(u -> "test1".equals(u.getName()) && "test1@example.com".equals(u.getEmail()));
            Assertions.assertTrue(exists, "User phai duoc luu vao DB sau khi message duoc tieu thu");
        });
    }
}
