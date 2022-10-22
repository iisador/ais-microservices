package ru.isador.ais.microservices.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.OrderRepository;
import ru.isador.ais.microservices.order.web.OrderInput;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public Order update(Long id, OrderInput modifiedOrder) {
        return orderRepository.findById(id)
                .map(o -> {
                    o.setDeliveryAddress(modifiedOrder.getDeliveryAddress());
                    o.setDescription(modifiedOrder.getDescription());
                    return o;
                })
                .map(orderRepository::save)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order newOrder(OrderInput orderInput) {
        return orderRepository.save(new Order(orderInput.getDeliveryAddress(), orderInput.getDescription()));
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
