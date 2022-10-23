package ru.isador.ais.microservices.order;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.isador.ais.microservices.order.data.OperationType;
import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.OrderOperation;
import ru.isador.ais.microservices.order.data.OrderOperationRepository;
import ru.isador.ais.microservices.order.data.OrderRepository;
import ru.isador.ais.microservices.order.data.OrderStatus;
import ru.isador.ais.microservices.order.data.Product;
import ru.isador.ais.microservices.order.operations.OperationFactory;
import ru.isador.ais.microservices.order.web.Basket;
import ru.isador.ais.microservices.order.web.OrderChangeSet;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderOperationRepository operationRepository;
    private OperationFactory operationFactory;
    private BonusCalculator bonusCalculator;
    private MessagePublisher messagePublisher;

    @Transactional
    public Order update(UUID orderId, OrderChangeSet changeSet) {
        if (operationRepository.existsById(new OrderOperation.OrderOperationId(changeSet.id(), orderId))) {
            throw new ExistedOrderOperationException(changeSet.id(), orderId);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        operationFactory.get(changeSet).apply(order, changeSet);
        orderRepository.save(order);
        saveOperation(orderId, changeSet);

        if (OperationType.PAY == OperationType.valueOf(changeSet.operation().toUpperCase())) {
            int bonus = bonusCalculator.getBonus(order);
            messagePublisher.sendMessage(String.format("%s:%s", order.getClientId(), bonus));
        }
        return order;
    }

    private void saveOperation(UUID orderId, OrderChangeSet changeSet) {
        OrderOperation operation = new OrderOperation();
        operation.setId(new OrderOperation.OrderOperationId(changeSet.id(), orderId));
        operation.setOperationType(OperationType.valueOf(changeSet.operation().toUpperCase()));
        if (changeSet.quantity() != null) {
            operation.setChangeSet(String.format("{ \"productId\": \"%s\", \"quantity\": \"%d\" }", changeSet.productId(), changeSet.quantity()));
        } else {
            operation.setChangeSet(String.format("{ \"productId\": \"%s\" }", changeSet.productId()));
        }

        operationRepository.save(operation);
    }

    public Order create(Basket basket) {
        if (orderRepository.existsById(basket.id())) {
            throw new ExistedOrderException(basket.id());
        }

        return orderRepository.save(newOrder(basket));
    }

    private Order newOrder(Basket basket) {
        Order o = new Order(basket.id(), basket.clientId());
        o.addProducts(basket.products().stream()
                .map(pi -> new Product(o.getId(), pi.id(), pi.quantity()))
                .collect(Collectors.toSet()));
        o.setStatus(OrderStatus.DRAFT);
        return o;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOperationRepository(OrderOperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Autowired
    public void setOperationFactory(OperationFactory operationFactory) {
        this.operationFactory = operationFactory;
    }

    @Autowired
    public void setBonusCalculator(BonusCalculator bonusCalculator) {
        this.bonusCalculator = bonusCalculator;
    }

    @Autowired
    public void setMessagePublisher(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }
}
