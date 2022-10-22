package ru.isador.ais.microservices.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.data.ClientRepository;

import static ru.isador.ais.microservices.client.SystemRoles.ADMIN;
import static ru.isador.ais.microservices.client.SystemRoles.TECH;
import static ru.isador.ais.microservices.client.SystemRoles.USER;

@SpringBootApplication
public class ClientApplication implements ApplicationListener<ApplicationStartedEvent> {

    private ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        clientRepository.saveAll(List.of(
                new Client("Тест Юзер Тестюзерович", "testPass", "testUser", USER),
                new Client("Админ Админыч", "testPass", "testAdmin", ADMIN),
                new Client("Техюзер Техюзверич", "techPass", "techUser", USER, ADMIN, TECH)
        ));
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
