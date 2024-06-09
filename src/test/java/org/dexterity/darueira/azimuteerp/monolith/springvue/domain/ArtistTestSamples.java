package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArtistTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Artist getArtistSample1() {
        return new Artist()
            .id(1L)
            .acronym("acronym1")
            .publicName("publicName1")
            .realName("realName1")
            .biographyDetailsJSON("biographyDetailsJSON1");
    }

    public static Artist getArtistSample2() {
        return new Artist()
            .id(2L)
            .acronym("acronym2")
            .publicName("publicName2")
            .realName("realName2")
            .biographyDetailsJSON("biographyDetailsJSON2");
    }

    public static Artist getArtistRandomSampleGenerator() {
        return new Artist()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .publicName(UUID.randomUUID().toString())
            .realName(UUID.randomUUID().toString())
            .biographyDetailsJSON(UUID.randomUUID().toString());
    }
}
