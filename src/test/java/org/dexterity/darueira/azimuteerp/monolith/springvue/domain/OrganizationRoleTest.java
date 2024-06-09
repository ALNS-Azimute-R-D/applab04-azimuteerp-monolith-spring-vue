package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRoleTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRoleTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationRoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationRole.class);
        OrganizationRole organizationRole1 = getOrganizationRoleSample1();
        OrganizationRole organizationRole2 = new OrganizationRole();
        assertThat(organizationRole1).isNotEqualTo(organizationRole2);

        organizationRole2.setId(organizationRole1.getId());
        assertThat(organizationRole1).isEqualTo(organizationRole2);

        organizationRole2 = getOrganizationRoleSample2();
        assertThat(organizationRole1).isNotEqualTo(organizationRole2);
    }

    @Test
    void organizationTest() {
        OrganizationRole organizationRole = getOrganizationRoleRandomSampleGenerator();
        Organization organizationBack = getOrganizationRandomSampleGenerator();

        organizationRole.setOrganization(organizationBack);
        assertThat(organizationRole.getOrganization()).isEqualTo(organizationBack);

        organizationRole.organization(null);
        assertThat(organizationRole.getOrganization()).isNull();
    }

    @Test
    void organizationMemberRolesListTest() {
        OrganizationRole organizationRole = getOrganizationRoleRandomSampleGenerator();
        OrganizationMemberRole organizationMemberRoleBack = getOrganizationMemberRoleRandomSampleGenerator();

        organizationRole.addOrganizationMemberRolesList(organizationMemberRoleBack);
        assertThat(organizationRole.getOrganizationMemberRolesLists()).containsOnly(organizationMemberRoleBack);
        assertThat(organizationMemberRoleBack.getOrganizationRole()).isEqualTo(organizationRole);

        organizationRole.removeOrganizationMemberRolesList(organizationMemberRoleBack);
        assertThat(organizationRole.getOrganizationMemberRolesLists()).doesNotContain(organizationMemberRoleBack);
        assertThat(organizationMemberRoleBack.getOrganizationRole()).isNull();

        organizationRole.organizationMemberRolesLists(new HashSet<>(Set.of(organizationMemberRoleBack)));
        assertThat(organizationRole.getOrganizationMemberRolesLists()).containsOnly(organizationMemberRoleBack);
        assertThat(organizationMemberRoleBack.getOrganizationRole()).isEqualTo(organizationRole);

        organizationRole.setOrganizationMemberRolesLists(new HashSet<>());
        assertThat(organizationRole.getOrganizationMemberRolesLists()).doesNotContain(organizationMemberRoleBack);
        assertThat(organizationMemberRoleBack.getOrganizationRole()).isNull();
    }
}
