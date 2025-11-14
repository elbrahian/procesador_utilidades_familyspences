package com.controller;

import com.domain.UtilidadMessage;
import com.messaging.UtilidadProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilidades")
public class UtilidadController {

    private static final Logger logger = LoggerFactory.getLogger(UtilidadController.class);

    private final UtilidadProducer utilidadProducer;

    public UtilidadController(UtilidadProducer utilidadProducer) {
        this.utilidadProducer = utilidadProducer;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestBody UtilidadMessage msg) {

        logger.info("📨 Recibida solicitud de envío de utilidad para usuario {}", msg.getIdUsuario());

        try {
            utilidadProducer.enviarMensaje(msg);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Mensaje enviado correctamente ✔");

        } catch (Exception e) {
            logger.error("❌ Error enviando mensaje a RabbitMQ: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error enviando el mensaje");
        }
    }
}
