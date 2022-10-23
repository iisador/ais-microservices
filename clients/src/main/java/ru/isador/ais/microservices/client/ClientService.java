package ru.isador.ais.microservices.client;

import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
                .orElseThrow(() -> new ClientNotFoundException(login));
    }

    @RabbitListener(queues = "bonuses")
    public void updateClientBonusBalance(String message) {
        UUID clientUuid = UUID.fromString(message.substring(0, message.indexOf(':')));
        clientRepository.findById(clientUuid)
                .ifPresent(client -> {
                            client.incBonuses(Integer.parseInt(message.substring(message.indexOf(':') + 1)));
                            clientRepository.save(client);
                        }
                );
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
