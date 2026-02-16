package ch.tranchida.sample.quarkus.bill;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.NoSuchElementException;

@ApplicationScoped
public class InMemoryBillStorage implements BillStorage {

    private final ConcurrentHashMap<Long, Bill> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public List<Bill> listAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Bill save(Bill bill) {
        long id = idSequence.incrementAndGet();
        Bill withId = new Bill(id, bill.description(), bill.amount());
        store.put(id, withId);
        return withId;
    }

    @Override
    public Bill update(Long id, Bill bill) {
        if (!store.containsKey(id)) {
            throw new NoSuchElementException("Bill not found: " + id);
        }
        Bill updated = new Bill(id, bill.description(), bill.amount());
        store.replace(id, updated);
        return updated;
    }

    @Override
    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}

