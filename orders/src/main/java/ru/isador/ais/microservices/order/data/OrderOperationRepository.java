package ru.isador.ais.microservices.order.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOperationRepository extends JpaRepository<OrderOperation, OrderOperation.OrderOperationId> {
}
