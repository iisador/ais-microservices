package ru.isador.ais.microservices.order.data;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_operations")
public class OrderOperation implements Serializable {

    @EmbeddedId
    private OrderOperationId id;

    @Column
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @Column
    private String changeSet;

    public OrderOperationId getId() {
        return id;
    }

    public void setId(OrderOperationId id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getChangeSet() {
        return changeSet;
    }

    public void setChangeSet(String changeSet) {
        this.changeSet = changeSet;
    }

    @Embeddable
    public static class OrderOperationId implements Serializable {

        @Column
        private UUID id;

        @Column
        private UUID orderId;

        public OrderOperationId() {
        }

        public OrderOperationId(UUID id, UUID orderId) {
            this.id = id;
            this.orderId = orderId;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public UUID getOrderId() {
            return orderId;
        }

        public void setOrderId(UUID orderId) {
            this.orderId = orderId;
        }
    }
}
