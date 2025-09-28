package org.fiap.notificador.application.usecase;

import org.fiap.notificador.application.port.driven.EnvioNotificacao;
import org.fiap.notificador.domain.model.AppUser;
import org.fiap.notificador.domain.model.Job;
import org.fiap.notificador.domain.model.JobStatus;
import org.fiap.notificador.domain.model.Notificacao;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
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
        String email = "user-abc@mail.com";
        String localizacao = "s3://bucket/arquivo.zip";

        Job job = new Job();
        AppUser user = new AppUser(UUID.randomUUID(), email, "admin");
        job.setUser(user);
        job.setStatus(JobStatus.COMPLETED);
        enviarNotificacaoUseCase.enviarNotificacao(job);

        ArgumentCaptor<Notificacao> notificacaoCaptor = ArgumentCaptor.forClass(Notificacao.class);
        verify(envioNotificacao).enviar(notificacaoCaptor.capture());

        Notificacao notificacaoCapturada = notificacaoCaptor.getValue();
        assertEquals(email, notificacaoCapturada.getDestinatario());
        assertEquals("Seu vídeo foi processado!", notificacaoCapturada.getAssunto());
        Assertions.assertNotNull(notificacaoCapturada.getMensagem());
    }
}
