package org.fiap.notificador.application.port.driven;

import org.fiap.notificador.domain.model.Notificacao;

public interface EnvioNotificacao {
    void enviar(Notificacao noticacao);
}
