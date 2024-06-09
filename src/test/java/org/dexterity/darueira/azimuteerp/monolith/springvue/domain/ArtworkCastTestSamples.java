package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ArtworkCastTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ArtworkCast getArtworkCastSample1() {
        return new ArtworkCast()
            .id(1L)
            .orderOfAppearance(1)
            .characterName("characterName1")
            .mainAssetUUID("mainAssetUUID1")
            .characterDetailsJSON("characterDetailsJSON1");
    }

    public static ArtworkCast getArtworkCastSample2() {
        return new ArtworkCast()
            .id(2L)
            .orderOfAppearance(2)
            .characterName("characterName2")
            .mainAssetUUID("mainAssetUUID2")
            .characterDetailsJSON("characterDetailsJSON2");
    }

    public static ArtworkCast getArtworkCastRandomSampleGenerator() {
        return new ArtworkCast()
            .id(longCount.incrementAndGet())
            .orderOfAppearance(intCount.incrementAndGet())
            .characterName(UUID.randomUUID().toString())
            .mainAssetUUID(UUID.randomUUID().toString())
            .characterDetailsJSON(UUID.randomUUID().toString());
    }
}
