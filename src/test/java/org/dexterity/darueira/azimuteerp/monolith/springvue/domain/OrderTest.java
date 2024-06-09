package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InvoiceTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrderItemTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrderTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = getOrderSample1();
        Order order2 = new Order();
        assertThat(order1).isNotEqualTo(order2);

        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);

        order2 = getOrderSample2();
        assertThat(order1).isNotEqualTo(order2);
    }

    @Test
    void orderItemsListTest() {
        Order order = getOrderRandomSampleGenerator();
        OrderItem orderItemBack = getOrderItemRandomSampleGenerator();

        order.addOrderItemsList(orderItemBack);
        assertThat(order.getOrderItemsLists()).containsOnly(orderItemBack);
        assertThat(orderItemBack.getOrder()).isEqualTo(order);

        order.removeOrderItemsList(orderItemBack);
        assertThat(order.getOrderItemsLists()).doesNotContain(orderItemBack);
        assertThat(orderItemBack.getOrder()).isNull();

        order.orderItemsLists(new HashSet<>(Set.of(orderItemBack)));
        assertThat(order.getOrderItemsLists()).containsOnly(orderItemBack);
        assertThat(orderItemBack.getOrder()).isEqualTo(order);

        order.setOrderItemsLists(new HashSet<>());
        assertThat(order.getOrderItemsLists()).doesNotContain(orderItemBack);
        assertThat(orderItemBack.getOrder()).isNull();
    }

    @Test
    void invoiceTest() {
        Order order = getOrderRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        order.setInvoice(invoiceBack);
        assertThat(order.getInvoice()).isEqualTo(invoiceBack);

        order.invoice(null);
        assertThat(order.getInvoice()).isNull();
    }

    @Test
    void customerTest() {
        Order order = getOrderRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        order.setCustomer(customerBack);
        assertThat(order.getCustomer()).isEqualTo(customerBack);

        order.customer(null);
        assertThat(order.getCustomer()).isNull();
    }
}
