package ch.tranchida.sample.quarkus.bill;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestForm;

import java.math.BigDecimal;
import java.util.List;

@Path("/bills")
public class BillResource {

    private final BillStorage storage;

    @Inject
    Template bills;

    @Inject
    @io.quarkus.qute.Location("bills-list.html")
    Template billsList;

    public BillResource(BillStorage storage) {
        this.storage = storage;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listAll() {
        return bills.data("bills", storage.listAll());
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance create(@RestForm String description, @RestForm BigDecimal amount) {
        storage.save(new Bill(null, description, amount));
        return billsList.data("bills", storage.listAll());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance delete(@PathParam("id") Long id) {
        storage.delete(id);
        return billsList.data("bills", storage.listAll());
    }

    @GET
    @Path("/api")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bill> listAllJson() {
        return storage.listAll();
    }
}
