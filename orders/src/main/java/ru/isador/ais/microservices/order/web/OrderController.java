package ru.isador.ais.microservices.order.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.isador.ais.microservices.order.OrderNotFoundException;
import ru.isador.ais.microservices.order.OrderService;
import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.OrderRepository;

@RestController
@RequestMapping(value = "/api/orders", produces = "application/json")
public class OrderController {

    private OrderRepository orderRepository;
    private OrderModelAssembler orderModelAssembler;
    private OrderService orderService;

    @GetMapping
    public Collection<EntityModel<Order>> list() {
        return orderRepository.findAll().stream()
                .map(orderModelAssembler::toModel)
                .toList();
    }

    @GetMapping("/{id}")
    public EntityModel<Order> get(@PathVariable("id") Long id) {
        return orderRepository.findById(id)
                .map(orderModelAssembler::toModel)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @PostMapping
    public EntityModel<Order> create(OrderInput newOrder) {
        return orderModelAssembler.toModel(orderService.newOrder(newOrder));
    }

    @PatchMapping("/{id}")
    public EntityModel<Order> update(@PathVariable("id") Long id,
            @RequestBody OrderInput modifiedClient) {
        return orderModelAssembler.toModel(orderService.update(id, modifiedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok("OK");
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderModelAssembler(OrderModelAssembler orderModelAssembler) {
        this.orderModelAssembler = orderModelAssembler;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
