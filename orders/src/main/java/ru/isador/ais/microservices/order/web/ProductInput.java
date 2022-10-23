package ru.isador.ais.microservices.order.web;

import java.util.UUID;

public record ProductInput(UUID id, int quantity) {
}
