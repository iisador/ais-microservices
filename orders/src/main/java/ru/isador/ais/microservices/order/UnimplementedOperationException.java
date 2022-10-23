package ru.isador.ais.microservices.order;

import ru.isador.ais.microservices.order.data.OperationType;

public class UnimplementedOperationException extends RuntimeException {

    private final OperationType type;

    public UnimplementedOperationException(OperationType type) {
        super(null, null, false, false);
        this.type = type;
    }

    public OperationType getType() {
        return type;
    }
}
