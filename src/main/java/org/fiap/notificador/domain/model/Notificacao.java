package org.fiap.notificador.domain.model;

import lombok.Data;
import org.fiap.notificador.domain.exception.NotificacaoInvalidaException;

@Data
public class Notificacao {

    private final String destinatario;
    private final String assunto;
    private final String mensagem;

    public Notificacao(String destinatario, String assunto, String mensagem) {
        if (destinatario == null || destinatario.isBlank()) {
            throw new NotificacaoInvalidaException("O destinatário não pode ser nulo ou vazio.");
        }
        if (mensagem == null || mensagem.isBlank()) {
            throw new NotificacaoInvalidaException("A mensagem não pode ser nula ou vazia.");
        }

        this.destinatario = destinatario;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }
}