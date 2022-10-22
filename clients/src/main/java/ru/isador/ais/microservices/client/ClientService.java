package ru.isador.ais.microservices.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.data.ClientRepository;
import ru.isador.ais.microservices.client.web.ClientInput;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public Client registerNewClient(ClientInput newClientModel) {
        if (clientRepository.findByLogin(newClientModel.getLogin()).isPresent()) {
            throw new ExistedClientException(newClientModel.getLogin());
        }
        Client newClient = new Client(newClientModel.getName(), newClientModel.getPassword(), newClientModel.getLogin());
        newClient.addRole(SystemRoles.USER);
        return clientRepository.save(newClient);
    }

    public Client update(String login, ClientInput modifiedClient) {
        return clientRepository.findByLogin(login)
                .map(c -> {
                    c.setPassword(modifiedClient.getPassword());
                    c.setName(modifiedClient.getName());
                    return c;
                })
                .map(clientRepository::save)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
