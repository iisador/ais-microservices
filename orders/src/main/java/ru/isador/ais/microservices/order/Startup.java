package ru.isador.ais.microservices.order;

import java.util.List;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;

import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.OrderRepository;

@Singleton
public class Startup {

    private OrderRepository orderRepository;

    @Transactional
    public void addTestOrders(@Observes StartupEvent evt) {
        orderRepository.save(List.of(
                new Order("ул. Пушкина, д. Колотушкина", "ящик вилок, ящик ложек"),
                new Order("деревня Козюлино", "картошка, маркошка"),
                new Order("608502, Костромская область, город Клин, проезд Чехова, 29", "мешок гвоздей")
        ));
    }

    @Inject
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
