package ru.isador.ais.microservices.client;

import java.util.List;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;

import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.data.ClientRepository;

import static ru.isador.ais.microservices.client.SystemRoles.ADMIN;
import static ru.isador.ais.microservices.client.SystemRoles.TECH;
import static ru.isador.ais.microservices.client.SystemRoles.USER;

@Singleton
public class Startup {

    private ClientRepository clientRepository;

    @Transactional
    public void addTestUsers(@Observes StartupEvent evt) {
        clientRepository.save(List.of(
                new Client("Тест Юзер Тестюзерович", "testPass", "testUser", USER),
                new Client("Админ Админыч", "testPass", "testAdmin", ADMIN),
                new Client("Техюзер Техюзверич", "techPass", "techUser", USER, ADMIN, TECH)
        ));
    }

    @Inject
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
