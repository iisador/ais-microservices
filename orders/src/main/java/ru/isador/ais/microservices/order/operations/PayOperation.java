package ru.isador.ais.microservices.order.operations;

import org.springframework.stereotype.Component;

import ru.isador.ais.microservices.order.IllegalOrderOperationException;
import ru.isador.ais.microservices.order.data.OperationType;
import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.OrderStatus;
import ru.isador.ais.microservices.order.web.OrderChangeSet;

@Component
public class PayOperation extends Operation {

    @Override
    public boolean isAcceptable(OperationType type) {
        return OperationType.PAY == type;
    }

    @Override
    public void apply(Order order, OrderChangeSet changeSet) {
        if(order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalOrderOperationException(OperationType.PAY);
        }
        order.setStatus(OrderStatus.COMPLETED);

        // fire message
    }
}
