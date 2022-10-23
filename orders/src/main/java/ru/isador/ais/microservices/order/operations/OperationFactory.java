package ru.isador.ais.microservices.order.operations;

import java.util.Set;

import org.springframework.stereotype.Component;

import ru.isador.ais.microservices.order.UnimplementedOperationException;
import ru.isador.ais.microservices.order.UnknownOperationException;
import ru.isador.ais.microservices.order.data.OperationType;
import ru.isador.ais.microservices.order.web.OrderChangeSet;

@Component
public class OperationFactory {

    private final Set<Operation> operations;

    public OperationFactory(Set<Operation> operations) {
        this.operations = operations;
    }

    public Operation get(OrderChangeSet changeSet) {
        OperationType type;
        try {
            type = OperationType.valueOf(String.valueOf(changeSet.operation()).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownOperationException(changeSet.operation());
        }

        return operations.stream()
                .filter(o -> o.isAcceptable(type))
                .findFirst()
                .orElseThrow(() -> new UnimplementedOperationException(type));
    }
}
