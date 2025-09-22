package com.fiap.postech.techchallenger.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) { super("Usuário não encontrado: " + id); }
}