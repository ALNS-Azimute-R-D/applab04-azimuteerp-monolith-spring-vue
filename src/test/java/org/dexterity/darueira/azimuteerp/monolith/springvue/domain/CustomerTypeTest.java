package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerTypeTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerType.class);
        CustomerType customerType1 = getCustomerTypeSample1();
        CustomerType customerType2 = new CustomerType();
        assertThat(customerType1).isNotEqualTo(customerType2);

        customerType2.setId(customerType1.getId());
        assertThat(customerType1).isEqualTo(customerType2);

        customerType2 = getCustomerTypeSample2();
        assertThat(customerType1).isNotEqualTo(customerType2);
    }

    @Test
    void customersListTest() {
        CustomerType customerType = getCustomerTypeRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        customerType.addCustomersList(customerBack);
        assertThat(customerType.getCustomersLists()).containsOnly(customerBack);
        assertThat(customerBack.getCustomerType()).isEqualTo(customerType);

        customerType.removeCustomersList(customerBack);
        assertThat(customerType.getCustomersLists()).doesNotContain(customerBack);
        assertThat(customerBack.getCustomerType()).isNull();

        customerType.customersLists(new HashSet<>(Set.of(customerBack)));
        assertThat(customerType.getCustomersLists()).containsOnly(customerBack);
        assertThat(customerBack.getCustomerType()).isEqualTo(customerType);

        customerType.setCustomersLists(new HashSet<>());
        assertThat(customerType.getCustomersLists()).doesNotContain(customerBack);
        assertThat(customerBack.getCustomerType()).isNull();
    }
}
