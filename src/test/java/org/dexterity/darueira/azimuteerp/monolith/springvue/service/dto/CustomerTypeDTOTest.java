package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTypeDTO.class);
        CustomerTypeDTO customerTypeDTO1 = new CustomerTypeDTO();
        customerTypeDTO1.setId(1L);
        CustomerTypeDTO customerTypeDTO2 = new CustomerTypeDTO();
        assertThat(customerTypeDTO1).isNotEqualTo(customerTypeDTO2);
        customerTypeDTO2.setId(customerTypeDTO1.getId());
        assertThat(customerTypeDTO1).isEqualTo(customerTypeDTO2);
        customerTypeDTO2.setId(2L);
        assertThat(customerTypeDTO1).isNotEqualTo(customerTypeDTO2);
        customerTypeDTO1.setId(null);
        assertThat(customerTypeDTO1).isNotEqualTo(customerTypeDTO2);
    }
}
