package ru.isador.ais.microservices.order.operations;

import org.springframework.stereotype.Component;

import ru.isador.ais.microservices.order.ProductNotFoundException;
import ru.isador.ais.microservices.order.data.OperationType;
import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.Product;
import ru.isador.ais.microservices.order.web.OrderChangeSet;

@Component
public class PlusOperation extends Operation {

    @Override
    public boolean isAcceptable(OperationType type) {
        return OperationType.PLUS == type;
    }

    @Override
    public void apply(Order order, OrderChangeSet changeSet) {
        Product product = order.getProducts().stream()
                .filter(p -> p.getProductId().equals(changeSet.productId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(changeSet.productId()));
        product.setQuantity(product.getQuantity() + changeSet.quantity());
    }
}
