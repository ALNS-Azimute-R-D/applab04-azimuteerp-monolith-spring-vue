package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttributeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationTestSamples.*;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationAttributeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationAttribute.class);
        OrganizationAttribute organizationAttribute1 = getOrganizationAttributeSample1();
        OrganizationAttribute organizationAttribute2 = new OrganizationAttribute();
        assertThat(organizationAttribute1).isNotEqualTo(organizationAttribute2);

        organizationAttribute2.setId(organizationAttribute1.getId());
        assertThat(organizationAttribute1).isEqualTo(organizationAttribute2);

        organizationAttribute2 = getOrganizationAttributeSample2();
        assertThat(organizationAttribute1).isNotEqualTo(organizationAttribute2);
    }

    @Test
    void organizationTest() {
        OrganizationAttribute organizationAttribute = getOrganizationAttributeRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        organizationAttribute.setOrganization(organizationBack);
        assertThat(organizationAttribute.getOrganization()).isEqualTo(organizationBack);

        organizationAttribute.organization(null);
        assertThat(organizationAttribute.getOrganization()).isNull();
    }
}
