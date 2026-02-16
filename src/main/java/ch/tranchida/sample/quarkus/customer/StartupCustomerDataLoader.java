package ch.tranchida.sample.quarkus.customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class StartupCustomerDataLoader {

    private final CustomerStorage storage;

    public StartupCustomerDataLoader(CustomerStorage storage) {
        this.storage = storage;
    }

    void onStart(@Observes StartupEvent ev) {
        // Populate the in-memory store with 10 test customers
        for (int i = 1; i <= 10; i++) {
            storage.save(new Customer(null, "Customer " + i));
        }
    }
}
