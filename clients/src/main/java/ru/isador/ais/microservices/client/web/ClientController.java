package ru.isador.ais.microservices.client.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.isador.ais.microservices.client.ClientService;
import ru.isador.ais.microservices.client.UserNotFoundException;
import ru.isador.ais.microservices.client.data.Client;
import ru.isador.ais.microservices.client.data.ClientRepository;

@RestController
@RequestMapping(value = "/clients", produces = "application/json")
public class ClientController {

    private ClientService clientService;
    private ClientRepository clientRepository;
    private ClientModelAssembler clientModelAssembler;

    @GetMapping
    public Collection<EntityModel<Client>> list() {
        return clientRepository.findAll().stream()
                .map(clientModelAssembler::toModel)
                .toList();
    }

    @GetMapping("/{login}")
    public EntityModel<Client> get(@PathVariable("login") String login) {
        return clientRepository.findByLogin(login)
                .map(clientModelAssembler::toModel)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Client>> create(@RequestBody ClientInput newClient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientModelAssembler.toModel(clientService.registerNewClient(newClient)));
    }

    @PatchMapping("/{login}")
    public EntityModel<Client> update(@PathVariable("login") String login,
            @RequestBody ClientInput modifiedClient) {
        return clientModelAssembler.toModel(clientService.update(login, modifiedClient));
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<?> remove(@PathVariable("login") String login) {
        clientRepository.deleteByLogin(login);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setClientModelAssembler(ClientModelAssembler modelAssembler) {
        this.clientModelAssembler = modelAssembler;
    }
}
