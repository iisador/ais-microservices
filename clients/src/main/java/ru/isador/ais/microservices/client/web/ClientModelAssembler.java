package ru.isador.ais.microservices.client.web;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.web.ClientController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClientModelAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {

    @Override
    public EntityModel<Client> toModel(Client client) {
        EntityModel<Client> model = EntityModel.of(client);
        model.add(linkTo(methodOn(ClientController.class).get(client.getLogin())).withSelfRel());
        model.add(linkTo(methodOn(ClientController.class).remove(client.getLogin())).withRel("delete"));
        model.add(linkTo(methodOn(ClientController.class).update(client.getLogin(), null)).withRel(IanaLinkRelations.EDIT));
        return model;
    }
}
