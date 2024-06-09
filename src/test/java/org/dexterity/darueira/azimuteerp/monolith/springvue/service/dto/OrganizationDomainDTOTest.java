package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationDomainDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationDomainDTO.class);
        OrganizationDomainDTO organizationDomainDTO1 = new OrganizationDomainDTO();
        organizationDomainDTO1.setId(1L);
        OrganizationDomainDTO organizationDomainDTO2 = new OrganizationDomainDTO();
        assertThat(organizationDomainDTO1).isNotEqualTo(organizationDomainDTO2);
        organizationDomainDTO2.setId(organizationDomainDTO1.getId());
        assertThat(organizationDomainDTO1).isEqualTo(organizationDomainDTO2);
        organizationDomainDTO2.setId(2L);
        assertThat(organizationDomainDTO1).isNotEqualTo(organizationDomainDTO2);
        organizationDomainDTO1.setId(null);
        assertThat(organizationDomainDTO1).isNotEqualTo(organizationDomainDTO2);
    }
}
