package ru.isador.ais.microservices.client.web;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.data.ClientRepository;

import static io.restassured.RestAssured.given;
import static ru.isador.ais.microservices.client.SystemRoles.USER;

@QuarkusTest
class ClientResourceTest {

    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository.exterminate();
        clientRepository.save(new Client("Тест Юзер Тестюзерович", "testPass", "testUser", USER));
    }

    @Test
    public void testCreateUserSuccess() {
        int clientCount = clientRepository.list().size();

        given()
                .contentType("application/json")
                .body("""
                        {
                          "name": "Новоюзер Новоюзерович",
                          "password": "newPass",
                          "login": "newUser"
                        }
                        """)
                .when().post("/api/clients")
                .then()
                .statusCode(201);

        int actualClients = clientRepository.list().size();

        Assertions.assertEquals(clientCount + 1, actualClients, "Error creating client");
    }

    @Test
    public void testCreateUserAlreadyExists() {
        given()
                .contentType("application/json")
                .body("""
                        {
                          "name": "Новоюзер Новоюзерович",
                          "password": "newPass",
                          "login": "newUser"
                        }
                        """)
                .when().post("/api/clients")
                .then()
                .statusCode(201);
        int clientCount = clientRepository.list().size();

        given()
                .contentType("application/json")
                .body("""
                        {
                          "name": "Новоюзер Новоюзерович",
                          "password": "newPass",
                          "login": "newUser"
                        }
                        """)
                .when().post("/api/clients")
                .then()
                .statusCode(400);

        int actualClients = clientRepository.list().size();

        Assertions.assertEquals(clientCount, actualClients, "Error computing existed client");
    }

    @Inject
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
