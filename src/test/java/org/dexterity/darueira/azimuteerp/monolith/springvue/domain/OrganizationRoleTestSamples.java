package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationRoleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OrganizationRole getOrganizationRoleSample1() {
        return new OrganizationRole().id(1L).roleName("roleName1");
    }

    public static OrganizationRole getOrganizationRoleSample2() {
        return new OrganizationRole().id(2L).roleName("roleName2");
    }

    public static OrganizationRole getOrganizationRoleRandomSampleGenerator() {
        return new OrganizationRole().id(longCount.incrementAndGet()).roleName(UUID.randomUUID().toString());
    }
}
