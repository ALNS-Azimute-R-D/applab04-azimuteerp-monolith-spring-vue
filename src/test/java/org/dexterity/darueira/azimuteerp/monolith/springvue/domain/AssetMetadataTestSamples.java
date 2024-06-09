package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AssetMetadataTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AssetMetadata getAssetMetadataSample1() {
        return new AssetMetadata().id(1L).metadataDetailsJSON("metadataDetailsJSON1");
    }

    public static AssetMetadata getAssetMetadataSample2() {
        return new AssetMetadata().id(2L).metadataDetailsJSON("metadataDetailsJSON2");
    }

    public static AssetMetadata getAssetMetadataRandomSampleGenerator() {
        return new AssetMetadata().id(longCount.incrementAndGet()).metadataDetailsJSON(UUID.randomUUID().toString());
    }
}
