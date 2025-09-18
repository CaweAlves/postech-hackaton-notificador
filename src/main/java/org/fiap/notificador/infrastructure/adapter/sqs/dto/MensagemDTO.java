package org.fiap.notificador.infrastructure.adapter.sqs.dto;

public record MensagemDTO(
        String idDoUsuario,
        String localizacaoArquivoZip
) {
}
