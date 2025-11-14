package com.messaging;

import com.config.RabbitMQConfig;
import com.domain.UtilidadMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UtilidadProducer {

    private final RabbitTemplate rabbitTemplate;

    public UtilidadProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensaje(UtilidadMessage mensaje) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.NOTIFICATION_EXCHANGE,      // ✔ Exchange correcto
                RabbitMQConfig.NOTIFICATION_ROUTING_KEY,   // ✔ Routing Key correcta
                mensaje
        );

        System.out.println("📤 Mensaje enviado a RabbitMQ: " + mensaje.getIdUsuario());
    }
}

