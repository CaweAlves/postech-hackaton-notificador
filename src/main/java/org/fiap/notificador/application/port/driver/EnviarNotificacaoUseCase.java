package org.fiap.notificador.application.port.driver;

import org.fiap.notificador.domain.model.Job;

public interface EnviarNotificacaoUseCase {
    void enviarNotificacao(Job job);
}
