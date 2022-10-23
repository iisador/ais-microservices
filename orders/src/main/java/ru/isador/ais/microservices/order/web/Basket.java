package ru.isador.ais.microservices.order.web;

import java.util.Set;
import java.util.UUID;

public record Basket(UUID id, UUID clientId, Set<ProductInput> products) {
}
