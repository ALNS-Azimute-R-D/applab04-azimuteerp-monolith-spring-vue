package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BusinessUnitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BusinessUnit getBusinessUnitSample1() {
        return new BusinessUnit().id(1L).acronym("acronym1").hierarchicalLevel("hierarchicalLevel1").name("name1");
    }

    public static BusinessUnit getBusinessUnitSample2() {
        return new BusinessUnit().id(2L).acronym("acronym2").hierarchicalLevel("hierarchicalLevel2").name("name2");
    }

    public static BusinessUnit getBusinessUnitRandomSampleGenerator() {
        return new BusinessUnit()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .hierarchicalLevel(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString());
    }
}
