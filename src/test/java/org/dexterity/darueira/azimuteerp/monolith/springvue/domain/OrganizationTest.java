package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnitTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttributeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomainTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembershipTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRoleTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TenantTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganizationTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organization.class);
        Organization organization1 = getOrganizationSample1();
        Organization organization2 = new Organization();
        assertThat(organization1).isNotEqualTo(organization2);

        organization2.setId(organization1.getId());
        assertThat(organization1).isEqualTo(organization2);

        organization2 = getOrganizationSample2();
        assertThat(organization1).isNotEqualTo(organization2);
    }

    @Test
    void tenantTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        organization.setTenant(tenantBack);
        assertThat(organization.getTenant()).isEqualTo(tenantBack);

        organization.tenant(null);
        assertThat(organization.getTenant()).isNull();
    }

    @Test
    void typeOfOrganizationTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        TypeOfOrganization typeOfOrganizationBack = getTypeOfOrganizationRandomSampleGenerator();

        organization.setTypeOfOrganization(typeOfOrganizationBack);
        assertThat(organization.getTypeOfOrganization()).isEqualTo(typeOfOrganizationBack);

        organization.typeOfOrganization(null);
        assertThat(organization.getTypeOfOrganization()).isNull();
    }

    @Test
    void organizationParentTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        organization.setOrganizationParent(organizationBack);
        assertThat(organization.getOrganizationParent()).isEqualTo(organizationBack);

        organization.organizationParent(null);
        assertThat(organization.getOrganizationParent()).isNull();
    }

    @Test
    void organizationDomainsListTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        OrganizationDomain organizationDomainBack = getOrganizationDomainRandomSampleGenerator();

        organization.addOrganizationDomainsList(organizationDomainBack);
        assertThat(organization.getOrganizationDomainsLists()).containsOnly(organizationDomainBack);
        assertThat(organizationDomainBack.getOrganization()).isEqualTo(organization);

        organization.removeOrganizationDomainsList(organizationDomainBack);
        assertThat(organization.getOrganizationDomainsLists()).doesNotContain(organizationDomainBack);
        assertThat(organizationDomainBack.getOrganization()).isNull();

        organization.organizationDomainsLists(new HashSet<>(Set.of(organizationDomainBack)));
        assertThat(organization.getOrganizationDomainsLists()).containsOnly(organizationDomainBack);
        assertThat(organizationDomainBack.getOrganization()).isEqualTo(organization);

        organization.setOrganizationDomainsLists(new HashSet<>());
        assertThat(organization.getOrganizationDomainsLists()).doesNotContain(organizationDomainBack);
        assertThat(organizationDomainBack.getOrganization()).isNull();
    }

    @Test
    void organizationAttributesListTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        OrganizationAttribute organizationAttributeBack = getOrganizationAttributeRandomSampleGenerator();

        organization.addOrganizationAttributesList(organizationAttributeBack);
        assertThat(organization.getOrganizationAttributesLists()).containsOnly(organizationAttributeBack);
        assertThat(organizationAttributeBack.getOrganization()).isEqualTo(organization);

        organization.removeOrganizationAttributesList(organizationAttributeBack);
        assertThat(organization.getOrganizationAttributesLists()).doesNotContain(organizationAttributeBack);
        assertThat(organizationAttributeBack.getOrganization()).isNull();

        organization.organizationAttributesLists(new HashSet<>(Set.of(organizationAttributeBack)));
        assertThat(organization.getOrganizationAttributesLists()).containsOnly(organizationAttributeBack);
        assertThat(organizationAttributeBack.getOrganization()).isEqualTo(organization);

        organization.setOrganizationAttributesLists(new HashSet<>());
        assertThat(organization.getOrganizationAttributesLists()).doesNotContain(organizationAttributeBack);
        assertThat(organizationAttributeBack.getOrganization()).isNull();
    }

    @Test
    void businessUnitsListTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        BusinessUnit businessUnitBack = getBusinessUnitRandomSampleGenerator();

        organization.addBusinessUnitsList(businessUnitBack);
        assertThat(organization.getBusinessUnitsLists()).containsOnly(businessUnitBack);
        assertThat(businessUnitBack.getOrganization()).isEqualTo(organization);

        organization.removeBusinessUnitsList(businessUnitBack);
        assertThat(organization.getBusinessUnitsLists()).doesNotContain(businessUnitBack);
        assertThat(businessUnitBack.getOrganization()).isNull();

        organization.businessUnitsLists(new HashSet<>(Set.of(businessUnitBack)));
        assertThat(organization.getBusinessUnitsLists()).containsOnly(businessUnitBack);
        assertThat(businessUnitBack.getOrganization()).isEqualTo(organization);

        organization.setBusinessUnitsLists(new HashSet<>());
        assertThat(organization.getBusinessUnitsLists()).doesNotContain(businessUnitBack);
        assertThat(businessUnitBack.getOrganization()).isNull();
    }

    @Test
    void childrenOrganizationsListTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        organization.addChildrenOrganizationsList(organizationBack);
        assertThat(organization.getChildrenOrganizationsLists()).containsOnly(organizationBack);
        assertThat(organizationBack.getOrganizationParent()).isEqualTo(organization);

        organization.removeChildrenOrganizationsList(organizationBack);
        assertThat(organization.getChildrenOrganizationsLists()).doesNotContain(organizationBack);
        assertThat(organizationBack.getOrganizationParent()).isNull();

        organization.childrenOrganizationsLists(new HashSet<>(Set.of(organizationBack)));
        assertThat(organization.getChildrenOrganizationsLists()).containsOnly(organizationBack);
        assertThat(organizationBack.getOrganizationParent()).isEqualTo(organization);

        organization.setChildrenOrganizationsLists(new HashSet<>());
        assertThat(organization.getChildrenOrganizationsLists()).doesNotContain(organizationBack);
        assertThat(organizationBack.getOrganizationParent()).isNull();
    }

    @Test
    void organizationRolesListTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        OrganizationRole organizationRoleBack = getOrganizationRoleRandomSampleGenerator();

        organization.addOrganizationRolesList(organizationRoleBack);
        assertThat(organization.getOrganizationRolesLists()).containsOnly(organizationRoleBack);
        assertThat(organizationRoleBack.getOrganization()).isEqualTo(organization);

        organization.removeOrganizationRolesList(organizationRoleBack);
        assertThat(organization.getOrganizationRolesLists()).doesNotContain(organizationRoleBack);
        assertThat(organizationRoleBack.getOrganization()).isNull();

        organization.organizationRolesLists(new HashSet<>(Set.of(organizationRoleBack)));
        assertThat(organization.getOrganizationRolesLists()).containsOnly(organizationRoleBack);
        assertThat(organizationRoleBack.getOrganization()).isEqualTo(organization);

        organization.setOrganizationRolesLists(new HashSet<>());
        assertThat(organization.getOrganizationRolesLists()).doesNotContain(organizationRoleBack);
        assertThat(organizationRoleBack.getOrganization()).isNull();
    }

    @Test
    void organizationMembershipsListTest() {
        Organization organization = getOrganizationRandomSampleGenerator();
        OrganizationMembership organizationMembershipBack = getOrganizationMembershipRandomSampleGenerator();

        organization.addOrganizationMembershipsList(organizationMembershipBack);
        assertThat(organization.getOrganizationMembershipsLists()).containsOnly(organizationMembershipBack);
        assertThat(organizationMembershipBack.getOrganization()).isEqualTo(organization);

        organization.removeOrganizationMembershipsList(organizationMembershipBack);
        assertThat(organization.getOrganizationMembershipsLists()).doesNotContain(organizationMembershipBack);
        assertThat(organizationMembershipBack.getOrganization()).isNull();

        organization.organizationMembershipsLists(new HashSet<>(Set.of(organizationMembershipBack)));
        assertThat(organization.getOrganizationMembershipsLists()).containsOnly(organizationMembershipBack);
        assertThat(organizationMembershipBack.getOrganization()).isEqualTo(organization);

        organization.setOrganizationMembershipsLists(new HashSet<>());
        assertThat(organization.getOrganizationMembershipsLists()).doesNotContain(organizationMembershipBack);
        assertThat(organizationMembershipBack.getOrganization()).isNull();
    }
}
