package ru.isador.ais.microservices.order;

import java.util.UUID;

public class ExistedOrderException extends RuntimeException {

    private final UUID id;

    public ExistedOrderException(UUID id) {
        super(null, null, false, false);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
