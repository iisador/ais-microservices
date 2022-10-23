package ru.isador.ais.microservices.order.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    private UUID id;

    @Column
    private UUID clientId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "ORDER_ID")
    private Set<Product> products = new HashSet<>();

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {
    }

    public Order(UUID id, UUID clientId) {
        this.id = id;
        this.clientId = clientId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProducts(Set<Product> products) {
        this.products.addAll(products);
    }

    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
