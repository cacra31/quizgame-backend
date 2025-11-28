package com.quizgame.global.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestKafkaProducer {

    private static final String TOPIC = "quizgame-test-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String msg) {
        kafkaTemplate.send(TOPIC, msg);
        System.out.println("📤 [Kafka Producer] sent: " + msg);
    }
}
