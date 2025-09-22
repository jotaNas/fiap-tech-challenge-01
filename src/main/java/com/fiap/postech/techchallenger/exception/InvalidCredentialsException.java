package com.fiap.postech.techchallenger.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Login ou senha inválidos");
    }
}