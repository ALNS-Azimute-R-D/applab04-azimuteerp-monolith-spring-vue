package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TypeOfActivityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TypeOfActivity getTypeOfActivitySample1() {
        return new TypeOfActivity()
            .id(1L)
            .acronym("acronym1")
            .name("name1")
            .description("description1")
            .handlerClazzName("handlerClazzName1");
    }

    public static TypeOfActivity getTypeOfActivitySample2() {
        return new TypeOfActivity()
            .id(2L)
            .acronym("acronym2")
            .name("name2")
            .description("description2")
            .handlerClazzName("handlerClazzName2");
    }

    public static TypeOfActivity getTypeOfActivityRandomSampleGenerator() {
        return new TypeOfActivity()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .handlerClazzName(UUID.randomUUID().toString());
    }
}
