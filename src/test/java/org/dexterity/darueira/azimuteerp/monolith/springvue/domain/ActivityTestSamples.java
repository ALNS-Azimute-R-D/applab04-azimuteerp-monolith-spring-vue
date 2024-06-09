package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ActivityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Activity getActivitySample1() {
        return new Activity()
            .id(1L)
            .name("name1")
            .shortDescription("shortDescription1")
            .fullDescription("fullDescription1")
            .mainGoals("mainGoals1");
    }

    public static Activity getActivitySample2() {
        return new Activity()
            .id(2L)
            .name("name2")
            .shortDescription("shortDescription2")
            .fullDescription("fullDescription2")
            .mainGoals("mainGoals2");
    }

    public static Activity getActivityRandomSampleGenerator() {
        return new Activity()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .shortDescription(UUID.randomUUID().toString())
            .fullDescription(UUID.randomUUID().toString())
            .mainGoals(UUID.randomUUID().toString());
    }
}
