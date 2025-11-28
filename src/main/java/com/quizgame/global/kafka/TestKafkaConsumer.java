package com.quizgame.global.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestKafkaConsumer {

    @KafkaListener(topics = "quizgame-test-topic", groupId = "quizgame-test-group")
    public void consume(String msg) {
        System.out.println("📥 [Kafka Consumer] received: " + msg);
    }
}
