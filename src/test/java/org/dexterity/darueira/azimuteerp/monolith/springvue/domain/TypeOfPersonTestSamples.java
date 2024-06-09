package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TypeOfPersonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TypeOfPerson getTypeOfPersonSample1() {
        return new TypeOfPerson().id(1L).code("code1").description("description1");
    }

    public static TypeOfPerson getTypeOfPersonSample2() {
        return new TypeOfPerson().id(2L).code("code2").description("description2");
    }

    public static TypeOfPerson getTypeOfPersonRandomSampleGenerator() {
        return new TypeOfPerson()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
