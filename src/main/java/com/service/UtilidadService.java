package com.service;

import com.domain.UtilidadMessage;
import com.repository.UtilidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UtilidadService {

    private static final Logger logger = LoggerFactory.getLogger(UtilidadService.class);

    private final UtilidadRepository utilidadRepository;

    public UtilidadService(UtilidadRepository utilidadRepository) {
        this.utilidadRepository = utilidadRepository;
    }

    /**
     * Procesa la utilidad recibida desde RabbitMQ.
     * Valida datos, guarda en BD y maneja errores.
     */
    public boolean procesarUtilidad(UtilidadMessage utilidad) {
        if (utilidad == null) {
            logger.error("❌ Error: UtilidadMessage es null");
            return false;
        }

        try {
            // (Opcional) Validar campos
            if (utilidad.getIdUsuario() == null) {
                logger.warn("⚠ Mensaje sin idUsuario, se descarta.");
                return false;
            }

            logger.info("📥 Procesando utilidad del usuario {}", utilidad.getIdUsuario());

            utilidadRepository.guardar(utilidad);

            logger.info("💾 Utilidad guardada correctamente");
            return true;

        } catch (Exception e) {
            logger.error("❌ Error procesando la utilidad: {}", e.getMessage(), e);
            return false;
        }
    }
}
