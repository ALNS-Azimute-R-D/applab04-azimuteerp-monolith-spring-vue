package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Article getArticleSample1() {
        return new Article()
            .id(1L)
            .inventoryProductId(1L)
            .skuCode("skuCode1")
            .customName("customName1")
            .customDescription("customDescription1");
    }

    public static Article getArticleSample2() {
        return new Article()
            .id(2L)
            .inventoryProductId(2L)
            .skuCode("skuCode2")
            .customName("customName2")
            .customDescription("customDescription2");
    }

    public static Article getArticleRandomSampleGenerator() {
        return new Article()
            .id(longCount.incrementAndGet())
            .inventoryProductId(longCount.incrementAndGet())
            .skuCode(UUID.randomUUID().toString())
            .customName(UUID.randomUUID().toString())
            .customDescription(UUID.randomUUID().toString());
    }
}
