package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Payment getPaymentSample1() {
        return new Payment().id(1L).installmentNumber(1).customAttributesDetailsJSON("customAttributesDetailsJSON1");
    }

    public static Payment getPaymentSample2() {
        return new Payment().id(2L).installmentNumber(2).customAttributesDetailsJSON("customAttributesDetailsJSON2");
    }

    public static Payment getPaymentRandomSampleGenerator() {
        return new Payment()
            .id(longCount.incrementAndGet())
            .installmentNumber(intCount.incrementAndGet())
            .customAttributesDetailsJSON(UUID.randomUUID().toString());
    }
}
