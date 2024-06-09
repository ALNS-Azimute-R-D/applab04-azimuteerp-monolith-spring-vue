package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CustomerTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CustomerType getCustomerTypeSample1() {
        return new CustomerType().id(1L).name("name1").description("description1").businessHandlerClazz("businessHandlerClazz1");
    }

    public static CustomerType getCustomerTypeSample2() {
        return new CustomerType().id(2L).name("name2").description("description2").businessHandlerClazz("businessHandlerClazz2");
    }

    public static CustomerType getCustomerTypeRandomSampleGenerator() {
        return new CustomerType()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .businessHandlerClazz(UUID.randomUUID().toString());
    }
}
