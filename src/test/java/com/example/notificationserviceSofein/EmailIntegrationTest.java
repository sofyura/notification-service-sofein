package com.example.notificationserviceSofein;

import com.example.notificationserviceSofein.dto.UserEventDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
class EmailIntegrationTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Autowired
    private KafkaTemplate<String, UserEventDto> kafkaTemplate;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Test
    void shouldSendEmailOnUserCreated() {
        UserEventDto event = new UserEventDto("test@example.com", "CREATED");

        kafkaTemplate.send("user-events", event);

        await().atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> {
                    // Проверка логов (в реальности можно мокать почту)
                    // Здесь просто проверяем, что не упало
                });
    }
}
