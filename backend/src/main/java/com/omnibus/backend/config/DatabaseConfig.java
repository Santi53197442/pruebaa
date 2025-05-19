package com.omnibus.backend.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    private final DataSource dataSource;

    public DatabaseConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void validarConexion() {
        logger.info("Validando conexión a base de datos PostgreSQL...");
        int intentos = 5;
        for (int i = 1; i <= intentos; i++) {
            try (Connection conexion = dataSource.getConnection()) {
                if (conexion.isValid(2)) {
                    logger.info("✅ Conexión a PostgreSQL establecida exitosamente!");
                    return;
                }
            } catch (SQLException e) {
                logger.error("Error al intentar conectarse a la base de datos (intento {} de {}): {}", i, intentos, e.getMessage());
                try {
                    Thread.sleep(2000); // Esperar 2 segundos antes de reintentar
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new RuntimeException("❌ No se pudo establecer la conexión a la base de datos después de varios intentos.");
    }
}
