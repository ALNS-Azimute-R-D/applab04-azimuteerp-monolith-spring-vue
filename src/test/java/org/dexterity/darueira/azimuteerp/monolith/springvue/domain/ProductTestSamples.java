package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(1L)
            .productSKU("productSKU1")
            .productName("productName1")
            .description("description1")
            .reorderLevel(1)
            .targetLevel(1)
            .quantityPerUnit("quantityPerUnit1")
            .minimumReorderQuantity(1)
            .suggestedCategory("suggestedCategory1");
    }

    public static Product getProductSample2() {
        return new Product()
            .id(2L)
            .productSKU("productSKU2")
            .productName("productName2")
            .description("description2")
            .reorderLevel(2)
            .targetLevel(2)
            .quantityPerUnit("quantityPerUnit2")
            .minimumReorderQuantity(2)
            .suggestedCategory("suggestedCategory2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .productSKU(UUID.randomUUID().toString())
            .productName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .reorderLevel(intCount.incrementAndGet())
            .targetLevel(intCount.incrementAndGet())
            .quantityPerUnit(UUID.randomUUID().toString())
            .minimumReorderQuantity(intCount.incrementAndGet())
            .suggestedCategory(UUID.randomUUID().toString());
    }
}
