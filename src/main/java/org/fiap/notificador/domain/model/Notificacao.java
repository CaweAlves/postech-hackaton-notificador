package org.fiap.notificador.domain.model;

import lombok.Data;

@Data
public class Notificacao {

    private final String destinatario;
    private final String assunto;
    private final String mensagem;
}