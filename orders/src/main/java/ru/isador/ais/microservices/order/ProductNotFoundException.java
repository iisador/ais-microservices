package ru.isador.ais.microservices.order;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    private final UUID id;

    public ProductNotFoundException(UUID id) {
        super(null, null, false, false);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
