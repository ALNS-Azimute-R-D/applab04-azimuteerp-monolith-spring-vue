package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRoleAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRoleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationRoleMapperTest {

    private OrganizationRoleMapper organizationRoleMapper;

    @BeforeEach
    void setUp() {
        organizationRoleMapper = new OrganizationRoleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrganizationRoleSample1();
        var actual = organizationRoleMapper.toEntity(organizationRoleMapper.toDto(expected));
        assertOrganizationRoleAllPropertiesEquals(expected, actual);
    }
}
