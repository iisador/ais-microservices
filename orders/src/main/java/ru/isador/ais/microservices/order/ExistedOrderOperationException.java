package ru.isador.ais.microservices.order;

import java.util.UUID;

public class ExistedOrderOperationException extends RuntimeException {

    private final UUID operationId;
    private final UUID orderId;

    public ExistedOrderOperationException(UUID operationId, UUID orderId) {
        super(null, null, false, false);
        this.operationId = operationId;
        this.orderId = orderId;
    }

    public UUID getOperationId() {
        return operationId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
