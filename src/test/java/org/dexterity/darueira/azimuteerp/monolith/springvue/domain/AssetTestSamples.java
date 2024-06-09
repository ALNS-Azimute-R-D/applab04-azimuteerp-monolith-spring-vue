package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AssetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Asset getAssetSample1() {
        return new Asset().id(1L).name("name1").fullFilenamePath("fullFilenamePath1");
    }

    public static Asset getAssetSample2() {
        return new Asset().id(2L).name("name2").fullFilenamePath("fullFilenamePath2");
    }

    public static Asset getAssetRandomSampleGenerator() {
        return new Asset()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .fullFilenamePath(UUID.randomUUID().toString());
    }
}
