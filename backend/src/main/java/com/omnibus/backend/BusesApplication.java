package com.omnibus.backend;

import com.omnibus.backend.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusesApplication implements CommandLineRunner {

	@Autowired
	private DatabaseConfig databaseConfig;

	public static void main(String[] args) {
		SpringApplication.run(BusesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		databaseConfig.validarConexion();
	}
}
