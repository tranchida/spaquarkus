package ch.tranchida.sample.quarkus.bill;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import io.quarkus.runtime.StartupEvent;

import java.math.BigDecimal;

@ApplicationScoped
public class StartupBillDataLoader {

    private final BillStorage storage;

    public StartupBillDataLoader(BillStorage storage) {
        this.storage = storage;
    }

    void onStart(@Observes StartupEvent ev) {
        // Populate the in-memory bill store with 5 test bills
        for (int i = 1; i <= 5; i++) {
            storage.save(new Bill(null, "Bill " + i, BigDecimal.valueOf(i * 10)));
        }
    }
}

