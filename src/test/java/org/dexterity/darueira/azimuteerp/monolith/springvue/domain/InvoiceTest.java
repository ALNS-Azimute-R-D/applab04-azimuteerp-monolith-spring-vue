package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InvoiceTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrderTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGatewayTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchasedTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoice.class);
        Invoice invoice1 = getInvoiceSample1();
        Invoice invoice2 = new Invoice();
        assertThat(invoice1).isNotEqualTo(invoice2);

        invoice2.setId(invoice1.getId());
        assertThat(invoice1).isEqualTo(invoice2);

        invoice2 = getInvoiceSample2();
        assertThat(invoice1).isNotEqualTo(invoice2);
    }

    @Test
    void preferrablePaymentGatewayTest() {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        PaymentGateway paymentGatewayBack = getPaymentGatewayRandomSampleGenerator();

        invoice.setPreferrablePaymentGateway(paymentGatewayBack);
        assertThat(invoice.getPreferrablePaymentGateway()).isEqualTo(paymentGatewayBack);

        invoice.preferrablePaymentGateway(null);
        assertThat(invoice.getPreferrablePaymentGateway()).isNull();
    }

    @Test
    void ordersListTest() {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        invoice.addOrdersList(orderBack);
        assertThat(invoice.getOrdersLists()).containsOnly(orderBack);
        assertThat(orderBack.getInvoice()).isEqualTo(invoice);

        invoice.removeOrdersList(orderBack);
        assertThat(invoice.getOrdersLists()).doesNotContain(orderBack);
        assertThat(orderBack.getInvoice()).isNull();

        invoice.ordersLists(new HashSet<>(Set.of(orderBack)));
        assertThat(invoice.getOrdersLists()).containsOnly(orderBack);
        assertThat(orderBack.getInvoice()).isEqualTo(invoice);

        invoice.setOrdersLists(new HashSet<>());
        assertThat(invoice.getOrdersLists()).doesNotContain(orderBack);
        assertThat(orderBack.getInvoice()).isNull();
    }

    @Test
    void ticketsPurchasedListTest() {
        Invoice invoice = getInvoiceRandomSampleGenerator();
        TicketPurchased ticketPurchasedBack = getTicketPurchasedRandomSampleGenerator();

        invoice.addTicketsPurchasedList(ticketPurchasedBack);
        assertThat(invoice.getTicketsPurchasedLists()).containsOnly(ticketPurchasedBack);
        assertThat(ticketPurchasedBack.getInvoice()).isEqualTo(invoice);

        invoice.removeTicketsPurchasedList(ticketPurchasedBack);
        assertThat(invoice.getTicketsPurchasedLists()).doesNotContain(ticketPurchasedBack);
        assertThat(ticketPurchasedBack.getInvoice()).isNull();

        invoice.ticketsPurchasedLists(new HashSet<>(Set.of(ticketPurchasedBack)));
        assertThat(invoice.getTicketsPurchasedLists()).containsOnly(ticketPurchasedBack);
        assertThat(ticketPurchasedBack.getInvoice()).isEqualTo(invoice);

        invoice.setTicketsPurchasedLists(new HashSet<>());
        assertThat(invoice.getTicketsPurchasedLists()).doesNotContain(ticketPurchasedBack);
        assertThat(ticketPurchasedBack.getInvoice()).isNull();
    }
}
