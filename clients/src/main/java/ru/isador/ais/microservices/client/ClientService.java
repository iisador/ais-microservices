package ru.isador.ais.microservices.client;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.data.ClientRepository;
import ru.isador.ais.microservices.client.web.ClientView;

@ApplicationScoped
public class ClientService {

    private ClientRepository clientRepository;
    private ClientConverter clientConverter;

    public void registerNewClient(ClientView newClientView) {
        if (clientRepository.findByLogin(newClientView.getLogin()).isPresent()) {
            throw new ExistedClientException(newClientView.getLogin());
        }
        Client newClient = clientConverter.toModel(newClientView);
        clientRepository.save(newClient);
    }

    public ClientView update(String login, ClientView modifiedClient) {
        return clientRepository.findByLogin(login)
                .map(c -> {
                    c.setPassword(modifiedClient.getPassword());
                    c.setName(modifiedClient.getName());
                    return c;
                })
                .map(clientRepository::update)
                .map(clientConverter::toView)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Inject
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Inject
    public void setClientConverter(ClientConverter clientConverter) {
        this.clientConverter = clientConverter;
    }
}
