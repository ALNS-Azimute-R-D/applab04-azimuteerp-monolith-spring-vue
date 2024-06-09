package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TypeOfArtistTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TypeOfArtist getTypeOfArtistSample1() {
        return new TypeOfArtist().id(1L).acronym("acronym1").name("name1").description("description1");
    }

    public static TypeOfArtist getTypeOfArtistSample2() {
        return new TypeOfArtist().id(2L).acronym("acronym2").name("name2").description("description2");
    }

    public static TypeOfArtist getTypeOfArtistRandomSampleGenerator() {
        return new TypeOfArtist()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
