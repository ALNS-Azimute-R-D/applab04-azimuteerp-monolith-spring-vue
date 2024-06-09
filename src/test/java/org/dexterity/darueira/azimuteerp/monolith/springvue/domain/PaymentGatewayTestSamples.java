package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentGatewayTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PaymentGateway getPaymentGatewaySample1() {
        return new PaymentGateway()
            .id(1L)
            .aliasCode("aliasCode1")
            .description("description1")
            .businessHandlerClazz("businessHandlerClazz1");
    }

    public static PaymentGateway getPaymentGatewaySample2() {
        return new PaymentGateway()
            .id(2L)
            .aliasCode("aliasCode2")
            .description("description2")
            .businessHandlerClazz("businessHandlerClazz2");
    }

    public static PaymentGateway getPaymentGatewayRandomSampleGenerator() {
        return new PaymentGateway()
            .id(longCount.incrementAndGet())
            .aliasCode(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .businessHandlerClazz(UUID.randomUUID().toString());
    }
}
