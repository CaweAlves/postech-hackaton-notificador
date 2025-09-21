package org.fiap.notificador.domain.model;

import org.fiap.notificador.domain.exception.NotificacaoInvalidaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificacaoTest {

    @Test
    void deveCriarNotificacao_QuandoDadosSaoValidos() {
        String destinatario = "user-123";
        String assunto = "Teste";
        String mensagem = "Mensagem de teste";

        assertDoesNotThrow(() -> {
            new Notificacao(destinatario, assunto, mensagem);
        });
    }

    @Test
    void deveLancarExcecao_QuandoDestinatarioEhNulo() {
        String destinatario = null;
        String assunto = "Teste";
        String mensagem = "Mensagem de teste";

        var exception = assertThrows(NotificacaoInvalidaException.class, () -> {
            new Notificacao(destinatario, assunto, mensagem);
        });
        assertEquals("O destinatário não pode ser nulo ou vazio.", exception.getMessage());
    }

    @Test
    void deveLancarExcecao_QuandoMensagemEhVazia() {
        String destinatario = "user-123";
        String assunto = "Teste";
        String mensagem = " ";

        var exception = assertThrows(NotificacaoInvalidaException.class, () -> {
            new Notificacao(destinatario, assunto, mensagem);
        });
        assertEquals("A mensagem não pode ser nula ou vazia.", exception.getMessage());
    }
}
