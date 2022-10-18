package ru.isador.ais.microservices.order;

import javax.enterprise.context.ApplicationScoped;

import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.web.OrderView;

@ApplicationScoped
public class OrderConverter {

    public Order toModel(OrderView orderView) {
        return new Order(orderView.getDeliveryAddress(), orderView.getDescription());
    }

    public OrderView toView(Order order) {
        return new OrderView(order.getId(), order.getDeliveryAddress(), order.getDescription());
    }
}
