package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EventTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Event getEventSample1() {
        return new Event().id(1L).acronym("acronym1").name("name1").description("description1").fullDescription("fullDescription1");
    }

    public static Event getEventSample2() {
        return new Event().id(2L).acronym("acronym2").name("name2").description("description2").fullDescription("fullDescription2");
    }

    public static Event getEventRandomSampleGenerator() {
        return new Event()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .fullDescription(UUID.randomUUID().toString());
    }
}
