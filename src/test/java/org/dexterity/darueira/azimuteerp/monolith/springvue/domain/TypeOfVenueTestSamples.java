package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TypeOfVenueTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TypeOfVenue getTypeOfVenueSample1() {
        return new TypeOfVenue().id(1L).acronym("acronym1").name("name1").description("description1").handlerClazzName("handlerClazzName1");
    }

    public static TypeOfVenue getTypeOfVenueSample2() {
        return new TypeOfVenue().id(2L).acronym("acronym2").name("name2").description("description2").handlerClazzName("handlerClazzName2");
    }

    public static TypeOfVenue getTypeOfVenueRandomSampleGenerator() {
        return new TypeOfVenue()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .handlerClazzName(UUID.randomUUID().toString());
    }
}
