package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRoleTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembershipTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationMembershipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationMembership.class);
        OrganizationMembership organizationMembership1 = getOrganizationMembershipSample1();
        OrganizationMembership organizationMembership2 = new OrganizationMembership();
        assertThat(organizationMembership1).isNotEqualTo(organizationMembership2);

        organizationMembership2.setId(organizationMembership1.getId());
        assertThat(organizationMembership1).isEqualTo(organizationMembership2);

        organizationMembership2 = getOrganizationMembershipSample2();
        assertThat(organizationMembership1).isNotEqualTo(organizationMembership2);
    }

    @Test
    void organizationTest() {
        OrganizationMembership organizationMembership = getOrganizationMembershipRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        organizationMembership.setOrganization(organizationBack);
        assertThat(organizationMembership.getOrganization()).isEqualTo(organizationBack);

        organizationMembership.organization(null);
        assertThat(organizationMembership.getOrganization()).isNull();
    }

    @Test
    void personTest() {
        OrganizationMembership organizationMembership = getOrganizationMembershipRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        organizationMembership.setPerson(personBack);
        assertThat(organizationMembership.getPerson()).isEqualTo(personBack);

        organizationMembership.person(null);
        assertThat(organizationMembership.getPerson()).isNull();
    }

    @Test
    void organizationMemberRolesListTest() {
        OrganizationMembership organizationMembership = getOrganizationMembershipRandomSampleGenerator();
        OrganizationMemberRole organizationMemberRoleBack = getOrganizationMemberRoleRandomSampleGenerator();

        organizationMembership.addOrganizationMemberRolesList(organizationMemberRoleBack);
        assertThat(organizationMembership.getOrganizationMemberRolesLists()).containsOnly(organizationMemberRoleBack);
        assertThat(organizationMemberRoleBack.getOrganizationMembership()).isEqualTo(organizationMembership);

        organizationMembership.removeOrganizationMemberRolesList(organizationMemberRoleBack);
        assertThat(organizationMembership.getOrganizationMemberRolesLists()).doesNotContain(organizationMemberRoleBack);
        assertThat(organizationMemberRoleBack.getOrganizationMembership()).isNull();

        organizationMembership.organizationMemberRolesLists(new HashSet<>(Set.of(organizationMemberRoleBack)));
        assertThat(organizationMembership.getOrganizationMemberRolesLists()).containsOnly(organizationMemberRoleBack);
        assertThat(organizationMemberRoleBack.getOrganizationMembership()).isEqualTo(organizationMembership);

        organizationMembership.setOrganizationMemberRolesLists(new HashSet<>());
        assertThat(organizationMembership.getOrganizationMemberRolesLists()).doesNotContain(organizationMemberRoleBack);
        assertThat(organizationMemberRoleBack.getOrganizationMembership()).isNull();
    }
}
