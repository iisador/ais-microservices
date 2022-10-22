package ru.isador.ais.microservices.client.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.isador.ais.microservices.client.ExistedClientException;
import ru.isador.ais.microservices.client.UserNotFoundException;

@RestControllerAdvice
public class ClientControllerAdvice {

    @ExceptionHandler(ExistedClientException.class)
    public ResponseEntity<?> onExistedClientException(ExistedClientException e) {
        return ResponseEntity.accepted()
                .body(String.format("User %s already registered", e.getLogin()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> onUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("User %s not found", e.getLogin()));
    }
}
