package ru.isador.ais.microservices.order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ru.isador.ais.microservices.order.data.OrderRepository;
import ru.isador.ais.microservices.order.web.OrderView;

@ApplicationScoped
public class OrderService {

    private OrderRepository orderRepository;
    private OrderConverter orderConverter;

    public OrderView update(Long id, OrderView modifiedOrder) {
        return orderRepository.find(id)
                .map(o -> {
                    o.setDeliveryAddress(modifiedOrder.getDeliveryAddress());
                    o.setDescription(modifiedOrder.getDescription());
                    return o;
                })
                .map(orderRepository::update)
                .map(orderConverter::toView)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Inject
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Inject
    public void setOrderConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }
}
