package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TypeOfArtmediaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TypeOfArtmedia getTypeOfArtmediaSample1() {
        return new TypeOfArtmedia().id(1L).acronym("acronym1").name("name1").description("description1");
    }

    public static TypeOfArtmedia getTypeOfArtmediaSample2() {
        return new TypeOfArtmedia().id(2L).acronym("acronym2").name("name2").description("description2");
    }

    public static TypeOfArtmedia getTypeOfArtmediaRandomSampleGenerator() {
        return new TypeOfArtmedia()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
