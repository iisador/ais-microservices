package ru.isador.ais.microservices.client.web;

import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ru.isador.ais.microservices.client.ClientConverter;
import ru.isador.ais.microservices.client.ClientService;
import ru.isador.ais.microservices.client.ExistedClientException;
import ru.isador.ais.microservices.client.UserNotFoundException;
import ru.isador.ais.microservices.client.data.ClientRepository;

@Path("/api/clients")
@Produces("application/json")
@Consumes("application/json")
public class ClientResource {

    private ClientService clientService;
    private ClientRepository clientRepository;
    private ClientConverter clientConverter;

    @GET
    public Collection<ClientView> list() {
        return clientRepository.list().stream()
                .map(clientConverter::toView)
                .toList();
    }

    @GET
    @Path("/{login}")
    public ClientView get(@PathParam("login") String login) {
        return clientRepository.findByLogin(login)
                .map(clientConverter::toView)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @POST
    public Response create(ClientView newClient, @Context UriInfo uriInfo) {
        try {
            clientService.registerNewClient(newClient);
            return Response.created(uriInfo.getAbsolutePathBuilder().path(newClient.getLogin()).build()).build();
        } catch (ExistedClientException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User already exists")
                    .build();
        }
    }

    @PATCH
    @Path("/{login}")
    public ClientView update(@PathParam("login") String login,
            ClientView modifiedClient) {
        return clientService.update(login, modifiedClient);
    }

    @DELETE
    @Path("/{login}")
    public Response remove(@PathParam("login") String login) {
        clientRepository.removeByLogin(login);
        return Response.ok("OK").build();
    }

    @Inject
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
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
