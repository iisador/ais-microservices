package ru.isador.ais.microservices.order.web;

public class OrderView {

    private Long id;
    private String deliveryAddress;
    private String description;

    public OrderView() {
    }

    public OrderView(Long id, String deliveryAddress, String description) {
        this.id = id;
        this.deliveryAddress = deliveryAddress;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
