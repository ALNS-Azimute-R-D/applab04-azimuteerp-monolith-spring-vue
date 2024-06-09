package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationMemberRoleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationMemberRoleDTO.class);
        OrganizationMemberRoleDTO organizationMemberRoleDTO1 = new OrganizationMemberRoleDTO();
        organizationMemberRoleDTO1.setId(1L);
        OrganizationMemberRoleDTO organizationMemberRoleDTO2 = new OrganizationMemberRoleDTO();
        assertThat(organizationMemberRoleDTO1).isNotEqualTo(organizationMemberRoleDTO2);
        organizationMemberRoleDTO2.setId(organizationMemberRoleDTO1.getId());
        assertThat(organizationMemberRoleDTO1).isEqualTo(organizationMemberRoleDTO2);
        organizationMemberRoleDTO2.setId(2L);
        assertThat(organizationMemberRoleDTO1).isNotEqualTo(organizationMemberRoleDTO2);
        organizationMemberRoleDTO1.setId(null);
        assertThat(organizationMemberRoleDTO1).isNotEqualTo(organizationMemberRoleDTO2);
    }
}
