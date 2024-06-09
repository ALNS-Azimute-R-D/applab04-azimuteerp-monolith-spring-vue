package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationMembershipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationMembershipDTO.class);
        OrganizationMembershipDTO organizationMembershipDTO1 = new OrganizationMembershipDTO();
        organizationMembershipDTO1.setId(1L);
        OrganizationMembershipDTO organizationMembershipDTO2 = new OrganizationMembershipDTO();
        assertThat(organizationMembershipDTO1).isNotEqualTo(organizationMembershipDTO2);
        organizationMembershipDTO2.setId(organizationMembershipDTO1.getId());
        assertThat(organizationMembershipDTO1).isEqualTo(organizationMembershipDTO2);
        organizationMembershipDTO2.setId(2L);
        assertThat(organizationMembershipDTO1).isNotEqualTo(organizationMembershipDTO2);
        organizationMembershipDTO1.setId(null);
        assertThat(organizationMembershipDTO1).isNotEqualTo(organizationMembershipDTO2);
    }
}
