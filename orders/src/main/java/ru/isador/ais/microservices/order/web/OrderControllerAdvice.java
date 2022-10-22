package ru.isador.ais.microservices.order.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.isador.ais.microservices.order.OrderNotFoundException;

@RestControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> onOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Order %s not found", e.getId()));
    }
}
