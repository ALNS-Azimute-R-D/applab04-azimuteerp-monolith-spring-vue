package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProvinceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Province getProvinceSample1() {
        return new Province().id(1L).acronym("acronym1").name("name1");
    }

    public static Province getProvinceSample2() {
        return new Province().id(2L).acronym("acronym2").name("name2");
    }

    public static Province getProvinceRandomSampleGenerator() {
        return new Province().id(longCount.incrementAndGet()).acronym(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
