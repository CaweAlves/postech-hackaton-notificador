package org.fiap.notificador.infrastructure.adapter.sqs;

import org.fiap.notificador.application.port.driver.EnviarNotificacaoUseCase;
import org.fiap.notificador.infrastructure.adapter.sqs.dto.MensagemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SqsListenerAdapterTest {

    @Mock
    private EnviarNotificacaoUseCase enviarNotificacaoUseCase;

    @InjectMocks
    private SqsListenerAdapter sqsListenerAdapter;

    @Test
    void deveChamarCasoDeUso_QuandoReceberMensagem() {
        MensagemDTO mensagem = new MensagemDTO("user-xyz", "s3://path/to/file.zip");

        sqsListenerAdapter.receberMensagem(mensagem);

        verify(enviarNotificacaoUseCase).enviarNotificacao("user-xyz", "s3://path/to/file.zip");
    }
}
