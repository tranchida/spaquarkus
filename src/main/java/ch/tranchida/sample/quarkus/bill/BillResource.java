package ch.tranchida.sample.quarkus.bill;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Path("/api/bills")
public class BillResource {

    private final BillStorage storage;

    public BillResource(BillStorage storage) {
        this.storage = storage;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bill> listAll() {
        return storage.listAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return storage.findById(id)
                .map(b -> Response.ok(b).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Bill bill) {
        Bill created = storage.save(bill);
        return Response.created(URI.create("/bills/" + created.id())).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Bill bill) {
        try {
            Bill updated = storage.update(id, bill);
            return Response.ok(updated).build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean removed = storage.delete(id);
        return removed ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}

