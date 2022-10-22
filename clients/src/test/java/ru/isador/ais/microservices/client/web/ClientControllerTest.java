package ru.isador.ais.microservices.client.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.data.ClientRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.isador.ais.microservices.client.SystemRoles.USER;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
        clientRepository.save(new Client("Тест Юзер Тестюзерович", "testPass", "testUser", USER));
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        int clientCount = clientRepository.findAll().size();

        mockMvc.perform(post("/api/clients")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "Новоюзер Новоюзерович",
                                  "password": "newPass",
                                  "login": "newUser"
                                }
                                """))
                .andExpect(status().isCreated());

        int actualClients = clientRepository.findAll().size();

        assertEquals(clientCount + 1, actualClients, "Error creating client");
    }

    @Test
    public void testCreateUserAlreadyExists() throws Exception {
        mockMvc.perform(post("/api/clients")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "Новоюзер Новоюзерович",
                                  "password": "newPass",
                                  "login": "newUser"
                                }
                                """))
                .andExpect(status().isCreated());
        int clientCount = clientRepository.findAll().size();

        mockMvc.perform(post("/api/clients")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "Новоюзер Новоюзерович",
                                  "password": "newPass",
                                  "login": "newUser"
                                }
                                """))
                .andExpect(status().isAccepted());

        int actualClients = clientRepository.findAll().size();

        assertEquals(clientCount, actualClients, "Error computing existed client");
    }
}
