package com.fiap.postech.techchallenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ProblemDetail handleEmail(EmailAlreadyUsedException ex, ServletWebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        pd.setType(URI.create("https://example.com/problems/email-already-used"));
        pd.setTitle("E-mail já em uso");
        enrich(pd, request);
        return pd;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleNotFound(UserNotFoundException ex, ServletWebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setType(URI.create("https://example.com/problems/user-not-found"));
        pd.setTitle("Usuário não encontrado");
        enrich(pd, request);
        return pd;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalid(InvalidCredentialsException ex, ServletWebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        pd.setType(URI.create("https://example.com/problems/invalid-credentials"));
        pd.setTitle("Credenciais inválidas");
        enrich(pd, request);
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, ServletWebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Erro de validação");
        pd.setDetail("Campos inválidos ou ausentes");
        pd.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList());
        pd.setType(URI.create("https://example.com/problems/validation-error"));
        enrich(pd, request);
        return pd;
    }

    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
    public ProblemDetail handleIllegal(RuntimeException ex, ServletWebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setType(URI.create("https://example.com/problems/invalid-request"));
        pd.setTitle("Requisição inválida");
        enrich(pd, request);
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, ServletWebRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado");
        pd.setType(URI.create("https://example.com/problems/internal-error"));
        pd.setTitle("Erro interno");
        enrich(pd, request);
        return pd;
    }

    private void enrich(ProblemDetail pd, ServletWebRequest request) {
        pd.setProperty("timestamp", OffsetDateTime.now());
        pd.setProperty("path", request.getRequest().getRequestURI());
    }
}
