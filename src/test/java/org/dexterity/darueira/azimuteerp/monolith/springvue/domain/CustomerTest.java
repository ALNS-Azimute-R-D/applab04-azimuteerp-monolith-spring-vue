package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerTypeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.DistrictTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrderTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = getCustomerSample1();
        Customer customer2 = new Customer();
        assertThat(customer1).isNotEqualTo(customer2);

        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    void buyerPersonTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        customer.setBuyerPerson(personBack);
        assertThat(customer.getBuyerPerson()).isEqualTo(personBack);

        customer.buyerPerson(null);
        assertThat(customer.getBuyerPerson()).isNull();
    }

    @Test
    void customerTypeTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        CustomerType customerTypeBack = getCustomerTypeRandomSampleGenerator();

        customer.setCustomerType(customerTypeBack);
        assertThat(customer.getCustomerType()).isEqualTo(customerTypeBack);

        customer.customerType(null);
        assertThat(customer.getCustomerType()).isNull();
    }

    @Test
    void districtTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        District districtBack = getDistrictRandomSampleGenerator();

        customer.setDistrict(districtBack);
        assertThat(customer.getDistrict()).isEqualTo(districtBack);

        customer.district(null);
        assertThat(customer.getDistrict()).isNull();
    }

    @Test
    void ordersListTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        customer.addOrdersList(orderBack);
        assertThat(customer.getOrdersLists()).containsOnly(orderBack);
        assertThat(orderBack.getCustomer()).isEqualTo(customer);

        customer.removeOrdersList(orderBack);
        assertThat(customer.getOrdersLists()).doesNotContain(orderBack);
        assertThat(orderBack.getCustomer()).isNull();

        customer.ordersLists(new HashSet<>(Set.of(orderBack)));
        assertThat(customer.getOrdersLists()).containsOnly(orderBack);
        assertThat(orderBack.getCustomer()).isEqualTo(customer);

        customer.setOrdersLists(new HashSet<>());
        assertThat(customer.getOrdersLists()).doesNotContain(orderBack);
        assertThat(orderBack.getCustomer()).isNull();
    }
}
