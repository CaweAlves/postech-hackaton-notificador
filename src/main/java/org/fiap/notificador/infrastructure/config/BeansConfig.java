package org.fiap.notificador.infrastructure.config;

import org.fiap.notificador.application.port.driven.EnvioNotificacao;
import org.fiap.notificador.application.port.driver.EnviarNotificacaoUseCase;
import org.fiap.notificador.application.usecase.EnviarNotificacaoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    public EnviarNotificacaoUseCase enviarNotificacaoUseCase(EnvioNotificacao envioNotificacao) {
        return new EnviarNotificacaoUseCaseImpl(envioNotificacao);
    }
}
