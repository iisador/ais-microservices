package ru.isador.ais.microservices.order.web;

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
import javax.ws.rs.core.Response;

import ru.isador.ais.microservices.order.OrderConverter;
import ru.isador.ais.microservices.order.OrderNotFoundException;
import ru.isador.ais.microservices.order.OrderService;
import ru.isador.ais.microservices.order.data.OrderRepository;

@Path("/api/orders")
@Produces("application/json")
@Consumes("application/json")
public class OrderResource {

    private OrderRepository orderRepository;
    private OrderConverter orderConverter;
    private OrderService orderService;

    @GET
    public Collection<OrderView> list() {
        return orderRepository.list().stream()
                .map(orderConverter::toView)
                .toList();
    }

    @GET
    @Path("/{id}")
    public OrderView get(@PathParam("id") Long id) {
        return orderRepository.find(id)
                .map(orderConverter::toView)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @POST
    public OrderView create(OrderView newOrder) {
        return orderConverter.toView(orderRepository.save(orderConverter.toModel(newOrder)));
    }

    @PATCH
    @Path("/{id}")
    public OrderView update(@PathParam("id") Long id,
            OrderView modifiedClient) {
        return orderService.update(id, modifiedClient);
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") Long id) {
        orderRepository.remove(id);
        return Response.ok("OK").build();
    }

    @Inject
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Inject
    public void setOrderConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    @Inject
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
