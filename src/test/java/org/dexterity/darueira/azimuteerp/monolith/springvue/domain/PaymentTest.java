package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGatewayTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentTestSamples.*;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = getPaymentSample1();
        Payment payment2 = new Payment();
        assertThat(payment1).isNotEqualTo(payment2);

        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);

        payment2 = getPaymentSample2();
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    void paymentGatewayTest() {
        Payment payment = getPaymentRandomSampleGenerator();
        PaymentGateway paymentGatewayBack = getPaymentGatewayRandomSampleGenerator();

        payment.setPaymentGateway(paymentGatewayBack);
        assertThat(payment.getPaymentGateway()).isEqualTo(paymentGatewayBack);

        payment.paymentGateway(null);
        assertThat(payment.getPaymentGateway()).isNull();
    }
}
