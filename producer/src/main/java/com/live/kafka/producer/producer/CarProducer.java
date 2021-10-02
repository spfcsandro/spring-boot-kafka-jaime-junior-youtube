package com.live.kafka.producer.producer;

import com.live.kafka.producer.controller.CarDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarProducer {

    private final String topic;
    private final KafkaTemplate<String, CarDTO> kafkaTemplate;

    public CarProducer(@Value("${topic.name}") String topic, KafkaTemplate<String, CarDTO> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(CarDTO carDTO){
        kafkaTemplate.send(topic, carDTO).addCallback(
                success -> log.info("Message send " + success.getProducerRecord().value()),
                failure -> log.info("Message failure " + failure.getMessage())
        );
    }
}
