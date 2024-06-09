package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TypeOfOrganizationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TypeOfOrganization getTypeOfOrganizationSample1() {
        return new TypeOfOrganization()
            .id(1L)
            .acronym("acronym1")
            .name("name1")
            .description("description1")
            .businessHandlerClazz("businessHandlerClazz1");
    }

    public static TypeOfOrganization getTypeOfOrganizationSample2() {
        return new TypeOfOrganization()
            .id(2L)
            .acronym("acronym2")
            .name("name2")
            .description("description2")
            .businessHandlerClazz("businessHandlerClazz2");
    }

    public static TypeOfOrganization getTypeOfOrganizationRandomSampleGenerator() {
        return new TypeOfOrganization()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .businessHandlerClazz(UUID.randomUUID().toString());
    }
}
