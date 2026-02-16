package ch.tranchida.sample.quarkus.bill;

import java.util.List;
import java.util.Optional;

public interface BillStorage {
    List<Bill> listAll();
    Optional<Bill> findById(Long id);
    Bill save(Bill bill);
    Bill update(Long id, Bill bill);
    boolean delete(Long id);
}

