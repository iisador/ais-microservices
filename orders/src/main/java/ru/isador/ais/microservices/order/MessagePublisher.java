package ru.isador.ais.microservices.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    private RabbitTemplate rabbitTemplate;
    private Binding binding;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), message);
        logger.info("Message sent: {}", message);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setBinding(Binding binding) {
        this.binding = binding;
    }
}
