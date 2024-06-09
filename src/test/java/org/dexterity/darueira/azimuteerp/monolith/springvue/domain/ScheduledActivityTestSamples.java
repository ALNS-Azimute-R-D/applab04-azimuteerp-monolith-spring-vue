package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduledActivityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ScheduledActivity getScheduledActivitySample1() {
        return new ScheduledActivity()
            .id(1L)
            .customizedName("customizedName1")
            .numberOfAttendees(1)
            .averageEvaluationInStars(1)
            .customAttributtesDetailsJSON("customAttributtesDetailsJSON1");
    }

    public static ScheduledActivity getScheduledActivitySample2() {
        return new ScheduledActivity()
            .id(2L)
            .customizedName("customizedName2")
            .numberOfAttendees(2)
            .averageEvaluationInStars(2)
            .customAttributtesDetailsJSON("customAttributtesDetailsJSON2");
    }

    public static ScheduledActivity getScheduledActivityRandomSampleGenerator() {
        return new ScheduledActivity()
            .id(longCount.incrementAndGet())
            .customizedName(UUID.randomUUID().toString())
            .numberOfAttendees(intCount.incrementAndGet())
            .averageEvaluationInStars(intCount.incrementAndGet())
            .customAttributtesDetailsJSON(UUID.randomUUID().toString());
    }
}
