package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Customer getCustomerSample1() {
        return new Customer()
            .id(1L)
            .customerBusinessCode("customerBusinessCode1")
            .fullname("fullname1")
            .customAttributesDetailsJSON("customAttributesDetailsJSON1");
    }

    public static Customer getCustomerSample2() {
        return new Customer()
            .id(2L)
            .customerBusinessCode("customerBusinessCode2")
            .fullname("fullname2")
            .customAttributesDetailsJSON("customAttributesDetailsJSON2");
    }

    public static Customer getCustomerRandomSampleGenerator() {
        return new Customer()
            .id(longCount.incrementAndGet())
            .customerBusinessCode(UUID.randomUUID().toString())
            .fullname(UUID.randomUUID().toString())
            .customAttributesDetailsJSON(UUID.randomUUID().toString());
    }
}
