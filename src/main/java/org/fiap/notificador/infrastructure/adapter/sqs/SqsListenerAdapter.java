package org.fiap.notificador.infrastructure.adapter.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fiap.notificador.application.port.driver.EnviarNotificacaoUseCase;
import org.fiap.notificador.infrastructure.adapter.sqs.dto.MensagemDTO;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsListenerAdapter {
    private final EnviarNotificacaoUseCase enviarNotificacaoUseCase;

    @SqsListener("${aws.sqs.queue-name}")
    public void receberMensagem(MensagemDTO mensagem) {
        log.info("Adaptador SQS recebeu a mensagem: {}", mensagem);
        enviarNotificacaoUseCase.enviarNotificacao(mensagem.idDoUsuario(), mensagem.localizacaoArquivoZip());
    }
}
