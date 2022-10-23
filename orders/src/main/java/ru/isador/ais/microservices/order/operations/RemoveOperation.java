package ru.isador.ais.microservices.order.operations;

import org.springframework.stereotype.Component;

import ru.isador.ais.microservices.order.data.OperationType;
import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.web.OrderChangeSet;

@Component
public class RemoveOperation extends Operation {

    @Override
    public boolean isAcceptable(OperationType type) {
        return OperationType.REMOVE == type;
    }

    @Override
    public void apply(Order order, OrderChangeSet changeSet) {
        order.getProducts().removeIf(product -> product.getProductId().equals(changeSet.productId()));
    }
}
