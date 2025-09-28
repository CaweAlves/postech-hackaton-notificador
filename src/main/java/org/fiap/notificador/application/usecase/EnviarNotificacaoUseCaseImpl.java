package org.fiap.notificador.application.usecase;

import lombok.extern.slf4j.Slf4j;
import org.fiap.notificador.application.port.driven.EnvioNotificacao;
import org.fiap.notificador.application.port.driver.EnviarNotificacaoUseCase;
import org.fiap.notificador.domain.model.Job;
import org.fiap.notificador.domain.model.Notificacao;

import static org.fiap.notificador.domain.model.JobStatus.COMPLETED;

@Slf4j
public class EnviarNotificacaoUseCaseImpl implements EnviarNotificacaoUseCase {

    private final EnvioNotificacao envioNotificacao;

    public EnviarNotificacaoUseCaseImpl(EnvioNotificacao envioNotificacao) {
        this.envioNotificacao = envioNotificacao;
    }

    @Override
    public void enviarNotificacao(Job job) {
        String assunto;
        String corpoDaMensagem;
        if (COMPLETED.equals(job.getStatus())) {
            assunto = "Seu vídeo foi processado!";
            corpoDaMensagem = String.format(
                    "Olá! Seu vídeo foi processado com sucesso e está em: %s",
                    job.getResultObject()
            );
        } else {
            assunto = "Ocorreu um erro ao processar seu vídeo!";
            corpoDaMensagem = String.format(
                    "Olá! Ocorreu um erro ao processar seu vídeo: %s",
                    job.getErrorMsg()
            );
        }

        Notificacao notificacao = new Notificacao(job.getUser().getEmail(), assunto, corpoDaMensagem);

        this.envioNotificacao.enviar(notificacao);

        log.info("Caso de uso finalizado. Notificação encaminhada para o adaptador de saída.");
    }
}
