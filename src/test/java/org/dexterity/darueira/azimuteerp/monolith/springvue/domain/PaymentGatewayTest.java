package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InvoiceTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGatewayTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentGatewayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentGateway.class);
        PaymentGateway paymentGateway1 = getPaymentGatewaySample1();
        PaymentGateway paymentGateway2 = new PaymentGateway();
        assertThat(paymentGateway1).isNotEqualTo(paymentGateway2);

        paymentGateway2.setId(paymentGateway1.getId());
        assertThat(paymentGateway1).isEqualTo(paymentGateway2);

        paymentGateway2 = getPaymentGatewaySample2();
        assertThat(paymentGateway1).isNotEqualTo(paymentGateway2);
    }

    @Test
    void invoicesAsPreferrableListTest() {
        PaymentGateway paymentGateway = getPaymentGatewayRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        paymentGateway.addInvoicesAsPreferrableList(invoiceBack);
        assertThat(paymentGateway.getInvoicesAsPreferrableLists()).containsOnly(invoiceBack);
        assertThat(invoiceBack.getPreferrablePaymentGateway()).isEqualTo(paymentGateway);

        paymentGateway.removeInvoicesAsPreferrableList(invoiceBack);
        assertThat(paymentGateway.getInvoicesAsPreferrableLists()).doesNotContain(invoiceBack);
        assertThat(invoiceBack.getPreferrablePaymentGateway()).isNull();

        paymentGateway.invoicesAsPreferrableLists(new HashSet<>(Set.of(invoiceBack)));
        assertThat(paymentGateway.getInvoicesAsPreferrableLists()).containsOnly(invoiceBack);
        assertThat(invoiceBack.getPreferrablePaymentGateway()).isEqualTo(paymentGateway);

        paymentGateway.setInvoicesAsPreferrableLists(new HashSet<>());
        assertThat(paymentGateway.getInvoicesAsPreferrableLists()).doesNotContain(invoiceBack);
        assertThat(invoiceBack.getPreferrablePaymentGateway()).isNull();
    }

    @Test
    void paymentsListTest() {
        PaymentGateway paymentGateway = getPaymentGatewayRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        paymentGateway.addPaymentsList(paymentBack);
        assertThat(paymentGateway.getPaymentsLists()).containsOnly(paymentBack);
        assertThat(paymentBack.getPaymentGateway()).isEqualTo(paymentGateway);

        paymentGateway.removePaymentsList(paymentBack);
        assertThat(paymentGateway.getPaymentsLists()).doesNotContain(paymentBack);
        assertThat(paymentBack.getPaymentGateway()).isNull();

        paymentGateway.paymentsLists(new HashSet<>(Set.of(paymentBack)));
        assertThat(paymentGateway.getPaymentsLists()).containsOnly(paymentBack);
        assertThat(paymentBack.getPaymentGateway()).isEqualTo(paymentGateway);

        paymentGateway.setPaymentsLists(new HashSet<>());
        assertThat(paymentGateway.getPaymentsLists()).doesNotContain(paymentBack);
        assertThat(paymentBack.getPaymentGateway()).isNull();
    }
}
