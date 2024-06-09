package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class InventoryTransactionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static InventoryTransaction getInventoryTransactionSample1() {
        return new InventoryTransaction().id(1L).invoiceId(1L).quantity(1).transactionComments("transactionComments1");
    }

    public static InventoryTransaction getInventoryTransactionSample2() {
        return new InventoryTransaction().id(2L).invoiceId(2L).quantity(2).transactionComments("transactionComments2");
    }

    public static InventoryTransaction getInventoryTransactionRandomSampleGenerator() {
        return new InventoryTransaction()
            .id(longCount.incrementAndGet())
            .invoiceId(longCount.incrementAndGet())
            .quantity(intCount.incrementAndGet())
            .transactionComments(UUID.randomUUID().toString());
    }
}
