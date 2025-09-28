package org.fiap.notificador.infrastructure.adapter.mail;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.fiap.notificador.domain.exception.FalhaComunicacaoException;
import org.fiap.notificador.domain.model.Notificacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailAdapterTest {

    @Mock
    private SnsTemplate snsTemplate;

    @InjectMocks
    private MailAdapter snsAdapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(snsAdapter, "arnTopicoSns", "arn:aws:sns:us-east-1:000000000000:test-topic");
    }

    @Test
    void deveChamarSnsTemplate_QuandoEnviarNotificacao() {
        Notificacao notificacao = new Notificacao("user-1", "Assunto", "Mensagem");

        snsAdapter.enviar(notificacao);

        verify(snsTemplate).sendNotification(eq("arn:aws:sns:us-east-1:000000000000:test-topic"), eq("Mensagem"), eq("Assunto"));
    }

    @Test
    void deveLancarFalhaComunicacaoException_QuandoSnsTemplateFalha() {
        Notificacao notificacao = new Notificacao("user-1", "Assunto", "Mensagem");
        doThrow(new RuntimeException("AWS Error")).when(snsTemplate).sendNotification(anyString(), anyString(), anyString());

        assertThrows(FalhaComunicacaoException.class, () -> {
            snsAdapter.enviar(notificacao);
        });
    }
}
