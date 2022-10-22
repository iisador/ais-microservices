package ru.isador.ais.microservices.client.web;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.isador.ais.microservices.client.ExistedClientException;
import ru.isador.ais.microservices.client.UserNotFoundException;

@RestControllerAdvice
public class ClientControllerAdvice {

    @ExceptionHandler(ExistedClientException.class)
    public ResponseEntity<SystemError> onExistedClientException(ExistedClientException e) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new SystemError("Ошибка создания пользователя",
                        String.format("Пользователь \"%s\" уже существует", e.getLogin())));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<SystemError> onUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new SystemError("Ошибка выполнения операции",
                        String.format("Пользователь \"%s\" не найден", e.getLogin())));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<SystemError> onUserNotFoundException(Throwable e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SystemError("Системная ошибка", throwableToString(e)));
    }

    private String throwableToString(Throwable e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
