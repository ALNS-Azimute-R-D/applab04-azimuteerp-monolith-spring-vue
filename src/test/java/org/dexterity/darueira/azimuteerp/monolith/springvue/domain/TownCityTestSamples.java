package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TownCityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TownCity getTownCitySample1() {
        return new TownCity().id(1L).acronym("acronym1").name("name1");
    }

    public static TownCity getTownCitySample2() {
        return new TownCity().id(2L).acronym("acronym2").name("name2");
    }

    public static TownCity getTownCityRandomSampleGenerator() {
        return new TownCity().id(longCount.incrementAndGet()).acronym(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
