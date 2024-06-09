package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationAttributeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationAttributeDTO.class);
        OrganizationAttributeDTO organizationAttributeDTO1 = new OrganizationAttributeDTO();
        organizationAttributeDTO1.setId(1L);
        OrganizationAttributeDTO organizationAttributeDTO2 = new OrganizationAttributeDTO();
        assertThat(organizationAttributeDTO1).isNotEqualTo(organizationAttributeDTO2);
        organizationAttributeDTO2.setId(organizationAttributeDTO1.getId());
        assertThat(organizationAttributeDTO1).isEqualTo(organizationAttributeDTO2);
        organizationAttributeDTO2.setId(2L);
        assertThat(organizationAttributeDTO1).isNotEqualTo(organizationAttributeDTO2);
        organizationAttributeDTO1.setId(null);
        assertThat(organizationAttributeDTO1).isNotEqualTo(organizationAttributeDTO2);
    }
}
