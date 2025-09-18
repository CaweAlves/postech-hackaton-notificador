package org.fiap.notificador.application.port.driver;

public interface EnviarNotificacaoUseCase {
    void enviarNotificacao(String idDoUsuario, String mensagem);
}
