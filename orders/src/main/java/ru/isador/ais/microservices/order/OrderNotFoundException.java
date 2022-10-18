package ru.isador.ais.microservices.order;

public class OrderNotFoundException extends RuntimeException {

    private final Long id;

    public OrderNotFoundException(Long id) {
        super(null, null, false, false);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
