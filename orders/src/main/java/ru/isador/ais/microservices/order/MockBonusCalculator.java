package ru.isador.ais.microservices.order;

import java.util.random.RandomGeneratorFactory;

import org.springframework.stereotype.Component;

import ru.isador.ais.microservices.order.data.Order;
import ru.isador.ais.microservices.order.data.Product;

@Component
public class MockBonusCalculator implements BonusCalculator {

    /** Баллы = 10% от общей стоимости заказа. */
    @Override
    public int getBonus(Order order) {
        double sum = order.getProducts().stream()
                .map(Product::getQuantity)
                .map(d -> d * getPrice())
                .reduce(0.0, Double::sum);
        return (int) (sum * 0.1);
    }

    private double getPrice() {
        return RandomGeneratorFactory.getDefault().create().nextDouble(100.0);
    }
}
