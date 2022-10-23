package ru.isador.ais.microservices.order.operations;

import ru.isador.ais.microservices.order.data.OperationType;
import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.web.OrderChangeSet;

public abstract class Operation {

    abstract boolean isAcceptable(OperationType type);

    public abstract void apply(Order order, OrderChangeSet changeSet);
}
