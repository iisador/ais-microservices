package ru.isador.ais.microservices.order;

public class IllegalQuantityException extends RuntimeException {

    private final Integer quantity;

    public IllegalQuantityException(Integer quantity) {
        super(null, null, false, false);
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
