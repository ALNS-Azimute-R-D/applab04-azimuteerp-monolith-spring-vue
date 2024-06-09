package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationMemberRoleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OrganizationMemberRole getOrganizationMemberRoleSample1() {
        return new OrganizationMemberRole().id(1L);
    }

    public static OrganizationMemberRole getOrganizationMemberRoleSample2() {
        return new OrganizationMemberRole().id(2L);
    }

    public static OrganizationMemberRole getOrganizationMemberRoleRandomSampleGenerator() {
        return new OrganizationMemberRole().id(longCount.incrementAndGet());
    }
}
