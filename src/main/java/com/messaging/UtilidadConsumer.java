package com.messaging;

import com.config.RabbitMQConfig;
import com.domain.UtilidadMessage;
import com.service.UtilidadService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UtilidadConsumer {

    private final UtilidadService utilidadService;

    public UtilidadConsumer(UtilidadService utilidadService) {
        this.utilidadService = utilidadService;
    }


    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void recibirMensaje(UtilidadMessage mensaje) {
        boolean procesado = utilidadService.procesarUtilidad(mensaje);

        if (procesado) {
            System.out.println("✔ Mensaje procesado");
        } else {
            System.out.println("❌ Mensaje rechazado o con error");
        }
    }

}
