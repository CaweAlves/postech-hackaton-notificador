package org.fiap.notificador;

import org.springframework.boot.SpringApplication;

public class TestNotificadorApplication {

	public static void main(String[] args) {
		SpringApplication.from(NotificadorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
