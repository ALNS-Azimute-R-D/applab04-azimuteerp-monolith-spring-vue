package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProgramTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Program getProgramSample1() {
        return new Program()
            .id(1L)
            .acronym("acronym1")
            .name("name1")
            .description("description1")
            .fullDescription("fullDescription1")
            .targetPublic("targetPublic1");
    }

    public static Program getProgramSample2() {
        return new Program()
            .id(2L)
            .acronym("acronym2")
            .name("name2")
            .description("description2")
            .fullDescription("fullDescription2")
            .targetPublic("targetPublic2");
    }

    public static Program getProgramRandomSampleGenerator() {
        return new Program()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .fullDescription(UUID.randomUUID().toString())
            .targetPublic(UUID.randomUUID().toString());
    }
}
