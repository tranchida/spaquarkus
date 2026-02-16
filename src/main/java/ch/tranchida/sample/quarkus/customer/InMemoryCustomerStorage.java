package ch.tranchida.sample.quarkus.customer;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.NoSuchElementException;

@ApplicationScoped
public class InMemoryCustomerStorage implements CustomerStorage {

    private final ConcurrentHashMap<Long, Customer> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public List<Customer> listAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Customer save(Customer customer) {
        long id = idSequence.incrementAndGet();
        Customer withId = new Customer(id, customer.name());
        store.put(id, withId);
        return withId;
    }

    @Override
    public Customer update(Long id, Customer customer) {
        if (!store.containsKey(id)) {
            throw new NoSuchElementException("Customer not found: " + id);
        }
        Customer updated = new Customer(id, customer.name());
        store.replace(id, updated);
        return updated;
    }

    @Override
    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}

