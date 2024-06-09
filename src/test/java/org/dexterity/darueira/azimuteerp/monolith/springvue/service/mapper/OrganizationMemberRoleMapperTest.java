package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRoleAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRoleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationMemberRoleMapperTest {

    private OrganizationMemberRoleMapper organizationMemberRoleMapper;

    @BeforeEach
    void setUp() {
        organizationMemberRoleMapper = new OrganizationMemberRoleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrganizationMemberRoleSample1();
        var actual = organizationMemberRoleMapper.toEntity(organizationMemberRoleMapper.toDto(expected));
        assertOrganizationMemberRoleAllPropertiesEquals(expected, actual);
    }
}
