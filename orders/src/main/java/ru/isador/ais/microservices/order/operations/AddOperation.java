package ru.isador.ais.microservices.order.operations;

import org.springframework.stereotype.Component;

import ru.isador.ais.microservices.order.data.OperationType;
import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.Product;
import ru.isador.ais.microservices.order.web.OrderChangeSet;

@Component
public class AddOperation extends Operation {

    @Override
    public boolean isAcceptable(OperationType type) {
        return OperationType.ADD == type;
    }

    @Override
    public void apply(Order order, OrderChangeSet changeSet) {
        Product product = order.getProducts().stream()
                .filter(p -> p.getProductId().equals(changeSet.productId()))
                .findFirst()
                .orElseGet(() -> order.addProduct(new Product(changeSet.productId(), order.getId(), changeSet.quantity())));
        product.setQuantity(changeSet.quantity());
    }
}
