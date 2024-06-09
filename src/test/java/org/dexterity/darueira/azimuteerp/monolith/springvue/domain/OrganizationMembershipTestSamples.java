package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OrganizationMembershipTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OrganizationMembership getOrganizationMembershipSample1() {
        return new OrganizationMembership().id(1L);
    }

    public static OrganizationMembership getOrganizationMembershipSample2() {
        return new OrganizationMembership().id(2L);
    }

    public static OrganizationMembership getOrganizationMembershipRandomSampleGenerator() {
        return new OrganizationMembership().id(longCount.incrementAndGet());
    }
}
