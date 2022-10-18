package ru.isador.ais.microservices.client;

import javax.enterprise.context.ApplicationScoped;

import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.web.ClientView;

@ApplicationScoped
public class ClientConverter {

    public Client toModel(ClientView clientView) {
        return new Client(clientView.getName(), clientView.getPassword(), clientView.getLogin(), SystemRoles.USER);
    }

    public ClientView toView(Client client) {
        return new ClientView(client.getName(), client.getLogin(), client.getPassword());
    }
}
