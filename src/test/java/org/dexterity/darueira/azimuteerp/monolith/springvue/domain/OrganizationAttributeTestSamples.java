package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationAttributeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OrganizationAttribute getOrganizationAttributeSample1() {
        return new OrganizationAttribute().id(1L).attributeName("attributeName1").attributeValue("attributeValue1");
    }

    public static OrganizationAttribute getOrganizationAttributeSample2() {
        return new OrganizationAttribute().id(2L).attributeName("attributeName2").attributeValue("attributeValue2");
    }

    public static OrganizationAttribute getOrganizationAttributeRandomSampleGenerator() {
        return new OrganizationAttribute()
            .id(longCount.incrementAndGet())
            .attributeName(UUID.randomUUID().toString())
            .attributeValue(UUID.randomUUID().toString());
    }
}
