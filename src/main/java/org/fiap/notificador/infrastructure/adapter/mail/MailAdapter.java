package org.fiap.notificador.infrastructure.adapter.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiap.notificador.application.port.driven.EnvioNotificacao;
import org.fiap.notificador.domain.exception.FalhaComunicacaoException;
import org.fiap.notificador.domain.model.Notificacao;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailAdapter implements EnvioNotificacao {

    private final JavaMailSender mailSender;

    @Override
    public void enviar(Notificacao notificacao) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(notificacao.getDestinatario());
            mailMessage.setSubject(notificacao.getAssunto());
            mailMessage.setText(notificacao.getMensagem());
            mailMessage.setFrom("no-reply@hackathon.com");
            mailSender.send(mailMessage);
            log.info("✅ E-mail enviado com sucesso para {}", notificacao.getDestinatario());
        } catch (Exception e) {
            log.error("❌ Erro ao enviar e-mail: {}", e.getMessage(), e);
            throw new FalhaComunicacaoException("Falha ao enviar notificação via SNS.", e);
        }
    }
}
