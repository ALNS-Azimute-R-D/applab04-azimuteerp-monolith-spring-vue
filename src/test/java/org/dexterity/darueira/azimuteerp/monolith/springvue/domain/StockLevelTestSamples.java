package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class StockLevelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static StockLevel getStockLevelSample1() {
        return new StockLevel().id(1L).remainingQuantity(1).commonAttributesDetailsJSON("commonAttributesDetailsJSON1");
    }

    public static StockLevel getStockLevelSample2() {
        return new StockLevel().id(2L).remainingQuantity(2).commonAttributesDetailsJSON("commonAttributesDetailsJSON2");
    }

    public static StockLevel getStockLevelRandomSampleGenerator() {
        return new StockLevel()
            .id(longCount.incrementAndGet())
            .remainingQuantity(intCount.incrementAndGet())
            .commonAttributesDetailsJSON(UUID.randomUUID().toString());
    }
}
