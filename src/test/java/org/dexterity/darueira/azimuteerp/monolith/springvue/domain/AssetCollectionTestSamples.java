package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AssetCollectionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AssetCollection getAssetCollectionSample1() {
        return new AssetCollection().id(1L).name("name1").fullFilenamePath("fullFilenamePath1");
    }

    public static AssetCollection getAssetCollectionSample2() {
        return new AssetCollection().id(2L).name("name2").fullFilenamePath("fullFilenamePath2");
    }

    public static AssetCollection getAssetCollectionRandomSampleGenerator() {
        return new AssetCollection()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .fullFilenamePath(UUID.randomUUID().toString());
    }
}
