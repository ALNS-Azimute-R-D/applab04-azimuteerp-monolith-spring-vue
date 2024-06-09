package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGatewayAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGatewayTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentGatewayMapperTest {

    private PaymentGatewayMapper paymentGatewayMapper;

    @BeforeEach
    void setUp() {
        paymentGatewayMapper = new PaymentGatewayMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPaymentGatewaySample1();
        var actual = paymentGatewayMapper.toEntity(paymentGatewayMapper.toDto(expected));
        assertPaymentGatewayAllPropertiesEquals(expected, actual);
    }
}
