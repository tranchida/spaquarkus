package ch.tranchida.sample.quarkus.customer;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;

import java.util.List;

@Path("/customers")
public class CustomerResource {

    private final CustomerStorage storage;

    @Inject
    Template customers;

    @Inject
    @io.quarkus.qute.Location("customers-list.html")
    Template customersList;

    public CustomerResource(CustomerStorage storage) {
        this.storage = storage;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listAll() {
        return customers.data("customers", storage.listAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance create(@RestForm String name) {
        storage.save(new Customer(null, name));
        return customersList.data("customers", storage.listAll());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance delete(@PathParam("id") Long id) {
        storage.delete(id);
        return customersList.data("customers", storage.listAll());
    }

    // Keep JSON API just in case or for other uses
    @GET
    @Path("/api")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> listAllJson() {
        return storage.listAll();
    }
}
