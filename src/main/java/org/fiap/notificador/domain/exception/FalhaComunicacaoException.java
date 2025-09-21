package org.fiap.notificador.domain.exception;

public class FalhaComunicacaoException extends RuntimeException {

    public FalhaComunicacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
