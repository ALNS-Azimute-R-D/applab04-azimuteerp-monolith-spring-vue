package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembershipAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembershipTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationMembershipMapperTest {

    private OrganizationMembershipMapper organizationMembershipMapper;

    @BeforeEach
    void setUp() {
        organizationMembershipMapper = new OrganizationMembershipMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrganizationMembershipSample1();
        var actual = organizationMembershipMapper.toEntity(organizationMembershipMapper.toDto(expected));
        assertOrganizationMembershipAllPropertiesEquals(expected, actual);
    }
}
