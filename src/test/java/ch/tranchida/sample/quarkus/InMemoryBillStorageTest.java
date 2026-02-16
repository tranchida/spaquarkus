package ch.tranchida.sample.quarkus;

import ch.tranchida.sample.quarkus.bill.Bill;
import ch.tranchida.sample.quarkus.bill.InMemoryBillStorage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryBillStorageTest {

    @Test
    void saveAndFindAndListAllAndDelete() {
        InMemoryBillStorage storage = new InMemoryBillStorage();

        Bill b1 = storage.save(new Bill(null, "Test1", BigDecimal.valueOf(10)));
        assertNotNull(b1.id());
        assertEquals("Test1", b1.description());

        Bill b2 = storage.save(new Bill(null, "Test2", BigDecimal.valueOf(20)));
        assertNotNull(b2.id());
        assertNotEquals(b1.id(), b2.id());

        List<Bill> all = storage.listAll();
        assertEquals(2, all.size());

        assertTrue(storage.findById(b1.id()).isPresent());
        assertEquals("Test1", storage.findById(b1.id()).get().description());

        boolean deleted = storage.delete(b1.id());
        assertTrue(deleted);
        assertFalse(storage.findById(b1.id()).isPresent());
    }

    @Test
    void updateExistingAndFailOnMissing() {
        InMemoryBillStorage storage = new InMemoryBillStorage();
        Bill b1 = storage.save(new Bill(null, "Orig", BigDecimal.valueOf(5)));

        Bill updated = storage.update(b1.id(), new Bill(null, "Updated", BigDecimal.valueOf(15)));
        assertEquals(b1.id(), updated.id());
        assertEquals("Updated", updated.description());
        assertEquals(0, updated.amount().compareTo(BigDecimal.valueOf(15)));

        assertThrows(NoSuchElementException.class, () -> storage.update(999L, new Bill(null, "X", BigDecimal.ZERO)));
    }
}

