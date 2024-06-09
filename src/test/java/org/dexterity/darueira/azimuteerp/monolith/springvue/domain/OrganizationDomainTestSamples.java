package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationDomainTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OrganizationDomain getOrganizationDomainSample1() {
        return new OrganizationDomain().id(1L).domainAcronym("domainAcronym1").name("name1").businessHandlerClazz("businessHandlerClazz1");
    }

    public static OrganizationDomain getOrganizationDomainSample2() {
        return new OrganizationDomain().id(2L).domainAcronym("domainAcronym2").name("name2").businessHandlerClazz("businessHandlerClazz2");
    }

    public static OrganizationDomain getOrganizationDomainRandomSampleGenerator() {
        return new OrganizationDomain()
            .id(longCount.incrementAndGet())
            .domainAcronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .businessHandlerClazz(UUID.randomUUID().toString());
    }
}
