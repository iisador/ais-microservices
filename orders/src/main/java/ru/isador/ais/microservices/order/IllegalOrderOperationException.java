package ru.isador.ais.microservices.order;

import ru.isador.ais.microservices.order.data.OperationType;

public class IllegalOrderOperationException extends RuntimeException {

    private final OperationType type;

    public IllegalOrderOperationException(OperationType type) {
        super(null, null, false, false);
        this.type = type;
    }

    public OperationType getType() {
        return type;
    }
}
