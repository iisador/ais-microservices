package ru.isador.ais.microservices.order.web;

import java.util.UUID;

public record OrderChangeSet(UUID id, String operation, UUID productId, Integer quantity) {
}
