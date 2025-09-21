package org.fiap.notificador.application.usecase;

import org.fiap.notificador.application.port.driven.EnvioNotificacao;
import org.fiap.notificador.domain.model.Notificacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EnviarNotificacaoUseCaseImplTest {

    @Mock
    private EnvioNotificacao envioNotificacao;

    @InjectMocks
    private EnviarNotificacaoUseCaseImpl enviarNotificacaoUseCase;

    @Test
    void deveEnviarNotificacao_ComDadosCorretos() {
        String idUsuario = "user-abc";
        String localizacao = "s3://bucket/arquivo.zip";

        enviarNotificacaoUseCase.enviarNotificacao(idUsuario, localizacao);

        ArgumentCaptor<Notificacao> notificacaoCaptor = ArgumentCaptor.forClass(Notificacao.class);
        verify(envioNotificacao).enviar(notificacaoCaptor.capture());

        Notificacao notificacaoCapturada = notificacaoCaptor.getValue();
        assertEquals(idUsuario, notificacaoCapturada.getDestinatario());
        assertEquals("Seu vídeo foi processado!", notificacaoCapturada.getAssunto());
        assertTrue(notificacaoCapturada.getMensagem().contains(idUsuario));
        assertTrue(notificacaoCapturada.getMensagem().contains(localizacao));
    }
}
