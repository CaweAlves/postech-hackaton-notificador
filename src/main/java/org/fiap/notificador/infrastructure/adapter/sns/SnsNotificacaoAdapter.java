package org.fiap.notificador.infrastructure.adapter.sns;

import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiap.notificador.application.port.driven.EnvioNotificacao;
import org.fiap.notificador.domain.exception.FalhaComunicacaoException;
import org.fiap.notificador.domain.model.Notificacao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnsNotificacaoAdapter implements EnvioNotificacao {

    private final SnsTemplate snsTemplate;

    @Value("${aws.sns.topic-arn}")
    private String arnTopicoSns;

    @Override
    public void enviar(Notificacao notificacao) {
        try {
            snsTemplate.sendNotification(this.arnTopicoSns, notificacao.getMensagem(), notificacao.getAssunto());
            log.info("Adaptador SNS publicou a notificação com sucesso.");
        } catch (Exception e) {
            log.error("Erro ao publicar notificação no SNS: {}", e.getMessage());
            throw new FalhaComunicacaoException("Falha ao enviar notificação via SNS.", e);
        }
    }
}
