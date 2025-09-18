package org.fiap.notificador.application.usecase;

import lombok.extern.slf4j.Slf4j;
import org.fiap.notificador.application.port.driven.EnvioNotificacao;
import org.fiap.notificador.application.port.driver.EnviarNotificacaoUseCase;
import org.fiap.notificador.domain.model.Notificacao;

@Slf4j
public class EnviarNotificacaoUseCaseImpl implements EnviarNotificacaoUseCase {

    private final EnvioNotificacao envioNotificacao;

    public EnviarNotificacaoUseCaseImpl(EnvioNotificacao envioNotificacao) {
        this.envioNotificacao = envioNotificacao;
    }

    @Override
    public void enviarNotificacao(String idDoUsuario, String localizacaoDoArquivo) {
        String assunto = "Seu vídeo foi processado!";
        String corpoDaMensagem = String.format(
                "Olá, usuário %s! Seu vídeo foi processado com sucesso e está em: %s",
                idDoUsuario, localizacaoDoArquivo
        );

        Notificacao notificacao = new Notificacao(idDoUsuario, assunto, corpoDaMensagem);

        this.envioNotificacao.enviar(notificacao);

        log.info("Caso de uso finalizado. Notificação encaminhada para o adaptador de saída.");
    }
}
