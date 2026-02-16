package ch.tranchida.sample.quarkus.bill;

import java.math.BigDecimal;

public record Bill(Long id, String description, BigDecimal amount) { }

