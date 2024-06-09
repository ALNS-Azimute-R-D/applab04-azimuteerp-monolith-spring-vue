package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Organization getOrganizationSample1() {
        return new Organization()
            .id(1L)
            .acronym("acronym1")
            .businessCode("businessCode1")
            .hierarchicalLevel("hierarchicalLevel1")
            .name("name1")
            .description("description1")
            .businessHandlerClazz("businessHandlerClazz1")
            .mainContactPersonDetailsJSON("mainContactPersonDetailsJSON1")
            .technicalEnvironmentsDetailsJSON("technicalEnvironmentsDetailsJSON1")
            .customAttributesDetailsJSON("customAttributesDetailsJSON1");
    }

    public static Organization getOrganizationSample2() {
        return new Organization()
            .id(2L)
            .acronym("acronym2")
            .businessCode("businessCode2")
            .hierarchicalLevel("hierarchicalLevel2")
            .name("name2")
            .description("description2")
            .businessHandlerClazz("businessHandlerClazz2")
            .mainContactPersonDetailsJSON("mainContactPersonDetailsJSON2")
            .technicalEnvironmentsDetailsJSON("technicalEnvironmentsDetailsJSON2")
            .customAttributesDetailsJSON("customAttributesDetailsJSON2");
    }

    public static Organization getOrganizationRandomSampleGenerator() {
        return new Organization()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .businessCode(UUID.randomUUID().toString())
            .hierarchicalLevel(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .businessHandlerClazz(UUID.randomUUID().toString())
            .mainContactPersonDetailsJSON(UUID.randomUUID().toString())
            .technicalEnvironmentsDetailsJSON(UUID.randomUUID().toString())
            .customAttributesDetailsJSON(UUID.randomUUID().toString());
    }
}
