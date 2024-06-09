package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ArtworkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Artwork getArtworkSample1() {
        return new Artwork()
            .id(1L)
            .artworkTitle("artworkTitle1")
            .productionYear(1)
            .seasonNumber(1)
            .episodeOrSequenceNumber(1)
            .registerIdInIMDB("registerIdInIMDB1")
            .assetsCollectionUUID("assetsCollectionUUID1")
            .contentDetailsJSON("contentDetailsJSON1");
    }

    public static Artwork getArtworkSample2() {
        return new Artwork()
            .id(2L)
            .artworkTitle("artworkTitle2")
            .productionYear(2)
            .seasonNumber(2)
            .episodeOrSequenceNumber(2)
            .registerIdInIMDB("registerIdInIMDB2")
            .assetsCollectionUUID("assetsCollectionUUID2")
            .contentDetailsJSON("contentDetailsJSON2");
    }

    public static Artwork getArtworkRandomSampleGenerator() {
        return new Artwork()
            .id(longCount.incrementAndGet())
            .artworkTitle(UUID.randomUUID().toString())
            .productionYear(intCount.incrementAndGet())
            .seasonNumber(intCount.incrementAndGet())
            .episodeOrSequenceNumber(intCount.incrementAndGet())
            .registerIdInIMDB(UUID.randomUUID().toString())
            .assetsCollectionUUID(UUID.randomUUID().toString())
            .contentDetailsJSON(UUID.randomUUID().toString());
    }
}
