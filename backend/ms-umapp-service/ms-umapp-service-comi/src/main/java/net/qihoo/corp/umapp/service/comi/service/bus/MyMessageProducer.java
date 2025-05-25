package net.qihoo.corp.umapp.service.comi.service.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @quthor cnn
 * @date 2024/4/8
 */
@Component
public class MyMessageProducer {

    public static final String TOPIC_NAME = "comi_dev";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }
}
