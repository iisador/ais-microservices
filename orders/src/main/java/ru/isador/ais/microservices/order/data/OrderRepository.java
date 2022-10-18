package ru.isador.ais.microservices.order.data;

import java.util.Collection;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class OrderRepository {

    private EntityManager entityManager;

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Order> find(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    public Collection<Order> list() {
        return entityManager.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    @Transactional
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Transactional
    public void save(Collection<Order> orders) {
        orders.forEach(entityManager::persist);
    }

    @Transactional
    public Order update(Order order) {
        return entityManager.merge(order);
    }

    @Transactional
    public void remove(Long id) {
        find(id).ifPresent(entityManager::remove);
    }
}
