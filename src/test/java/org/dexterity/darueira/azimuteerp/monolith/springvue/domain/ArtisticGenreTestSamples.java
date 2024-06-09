package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArtisticGenreTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ArtisticGenre getArtisticGenreSample1() {
        return new ArtisticGenre().id(1L).acronym("acronym1").name("name1").description("description1");
    }

    public static ArtisticGenre getArtisticGenreSample2() {
        return new ArtisticGenre().id(2L).acronym("acronym2").name("name2").description("description2");
    }

    public static ArtisticGenre getArtisticGenreRandomSampleGenerator() {
        return new ArtisticGenre()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
