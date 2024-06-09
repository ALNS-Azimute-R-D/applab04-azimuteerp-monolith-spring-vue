package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationRoleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationRoleDTO.class);
        OrganizationRoleDTO organizationRoleDTO1 = new OrganizationRoleDTO();
        organizationRoleDTO1.setId(1L);
        OrganizationRoleDTO organizationRoleDTO2 = new OrganizationRoleDTO();
        assertThat(organizationRoleDTO1).isNotEqualTo(organizationRoleDTO2);
        organizationRoleDTO2.setId(organizationRoleDTO1.getId());
        assertThat(organizationRoleDTO1).isEqualTo(organizationRoleDTO2);
        organizationRoleDTO2.setId(2L);
        assertThat(organizationRoleDTO1).isNotEqualTo(organizationRoleDTO2);
        organizationRoleDTO1.setId(null);
        assertThat(organizationRoleDTO1).isNotEqualTo(organizationRoleDTO2);
    }
}
