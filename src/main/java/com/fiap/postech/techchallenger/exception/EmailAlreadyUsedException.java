package com.fiap.postech.techchallenger.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) { super("E-mail já utilizado: " + email); }
}
