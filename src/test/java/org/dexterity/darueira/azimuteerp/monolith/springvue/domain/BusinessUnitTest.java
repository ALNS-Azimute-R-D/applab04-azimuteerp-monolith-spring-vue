package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnitTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnitTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessUnit.class);
        BusinessUnit businessUnit1 = getBusinessUnitSample1();
        BusinessUnit businessUnit2 = new BusinessUnit();
        assertThat(businessUnit1).isNotEqualTo(businessUnit2);

        businessUnit2.setId(businessUnit1.getId());
        assertThat(businessUnit1).isEqualTo(businessUnit2);

        businessUnit2 = getBusinessUnitSample2();
        assertThat(businessUnit1).isNotEqualTo(businessUnit2);
    }

    @Test
    void organizationTest() {
        BusinessUnit businessUnit = getBusinessUnitRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        businessUnit.setOrganization(organizationBack);
        assertThat(businessUnit.getOrganization()).isEqualTo(organizationBack);

        businessUnit.organization(null);
        assertThat(businessUnit.getOrganization()).isNull();
    }

    @Test
    void businessUnitParentTest() {
        BusinessUnit businessUnit = getBusinessUnitRandomSampleGenerator();
        BusinessUnit businessUnitBack = getBusinessUnitRandomSampleGenerator();

        businessUnit.setBusinessUnitParent(businessUnitBack);
        assertThat(businessUnit.getBusinessUnitParent()).isEqualTo(businessUnitBack);

        businessUnit.businessUnitParent(null);
        assertThat(businessUnit.getBusinessUnitParent()).isNull();
    }

    @Test
    void childrenBusinessUnitsListTest() {
        BusinessUnit businessUnit = getBusinessUnitRandomSampleGenerator();
        BusinessUnit businessUnitBack = getBusinessUnitRandomSampleGenerator();

        businessUnit.addChildrenBusinessUnitsList(businessUnitBack);
        assertThat(businessUnit.getChildrenBusinessUnitsLists()).containsOnly(businessUnitBack);
        assertThat(businessUnitBack.getBusinessUnitParent()).isEqualTo(businessUnit);

        businessUnit.removeChildrenBusinessUnitsList(businessUnitBack);
        assertThat(businessUnit.getChildrenBusinessUnitsLists()).doesNotContain(businessUnitBack);
        assertThat(businessUnitBack.getBusinessUnitParent()).isNull();

        businessUnit.childrenBusinessUnitsLists(new HashSet<>(Set.of(businessUnitBack)));
        assertThat(businessUnit.getChildrenBusinessUnitsLists()).containsOnly(businessUnitBack);
        assertThat(businessUnitBack.getBusinessUnitParent()).isEqualTo(businessUnit);

        businessUnit.setChildrenBusinessUnitsLists(new HashSet<>());
        assertThat(businessUnit.getChildrenBusinessUnitsLists()).doesNotContain(businessUnitBack);
        assertThat(businessUnitBack.getBusinessUnitParent()).isNull();
    }
}
