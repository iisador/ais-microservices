package ru.isador.ais.microservices.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.OrderRepository;

@SpringBootApplication
public class OrderApplication implements ApplicationListener<ApplicationStartedEvent> {

    private OrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        orderRepository.saveAll(List.of(
                new Order("ул. Пушкина, д. Колотушкина", "ящик вилок, ящик ложек"),
                new Order("деревня Козюлино", "картошка, маркошка"),
                new Order("608502, Костромская область, город Клин, проезд Чехова, 29", "мешок гвоздей")
        ));
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
