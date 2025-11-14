package com.repository;

import com.domain.UtilidadMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UtilidadRepository {

    private static final Logger logger = LoggerFactory.getLogger(UtilidadRepository.class);


    public void guardar(UtilidadMessage utilidad) {
        try {
            // LOG profesional
            logger.info("📚 Guardando utilidad para usuario {}", utilidad.getIdUsuario());

            // Simulación de persistencia (por ahora)
            // Aquí luego integras MongoDB, PostgreSQL, MySQL, etc.
            // ej: jpaRepository.save(entidad)

        } catch (Exception e) {
            logger.error("❌ Error guardando utilidad: {}", e.getMessage(), e);
            throw e; // se relanza para que el Service pueda manejarlo
        }
    }
}
