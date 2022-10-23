package ru.isador.ais.microservices.order;

import ru.isador.ais.microservices.order.data.Order;

public interface BonusCalculator {

    int getBonus(Order order);
}
