package com.omnibus.backend.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @PostConstruct
    public void validateConnection() {
        logger.info("Validando conexión a base de datos PostgreSQL...");

        int maxAttempts = 5;
        int currentAttempt = 0;
        boolean connected = false;

        while (!connected && currentAttempt < maxAttempts) {
            try {
                currentAttempt++;
                logger.info("Intento de conexión a base de datos {} de {}", currentAttempt, maxAttempts);

                try (Connection conn = dataSource.getConnection()) {
                    if (conn.isValid(10)) {
                        connected = true;
                        logger.info("✅ Conexión a PostgreSQL establecida exitosamente!");
                    }
                }
            } catch (SQLException e) {
                logger.error("Error al conectar a PostgreSQL (intento {}/{}): {}",
                        currentAttempt, maxAttempts, e.getMessage());

                if (currentAttempt < maxAttempts) {
                    logger.info("Esperando 10 segundos antes del siguiente intento...");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        if (!connected) {
            logger.error("❌ No se pudo establecer conexión a la base de datos después de {} intentos", maxAttempts);
            logger.info("DATABASE_URL: {}", env.getProperty("DATABASE_URL", "No disponible"));
            logger.info("PGHOST: {}", env.getProperty("PGHOST", "No disponible"));
            logger.info("PGPORT: {}", env.getProperty("PGPORT", "No disponible"));
            logger.info("PGDATABASE: {}", env.getProperty("PGDATABASE", "No disponible"));
            logger.info("PGUSER: {}", env.getProperty("PGUSER", "No disponible"));

            // Esto imprime si la contraseña está presente, sin mostrarla
            logger.info("PGPASSWORD: {}",
                    env.getProperty("PGPASSWORD") != null ? "******** (presente)" : "No disponible");
        }
    }
}