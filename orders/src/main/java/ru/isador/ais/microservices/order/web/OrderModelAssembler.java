package ru.isador.ais.microservices.order.web;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ru.isador.ais.microservices.order.data.Order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order entity) {
        EntityModel<Order> model = EntityModel.of(entity);
        model.add(linkTo(methodOn(OrderController.class).get(entity.getId())).withSelfRel());
        model.add(linkTo(methodOn(OrderController.class).remove(entity.getId())).withRel("delete"));
        model.add(linkTo(methodOn(OrderController.class).update(entity.getId(), new OrderChangeSet(null, null, null, null))).withRel(IanaLinkRelations.EDIT));
        return model;
    }
}
