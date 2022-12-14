package ru.isador.ais.microservices.order;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

    private final UUID id;

    public OrderNotFoundException(UUID id) {
        super(null, null, false, false);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
