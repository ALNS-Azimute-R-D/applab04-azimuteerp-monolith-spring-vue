package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttributeAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttributeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationAttributeMapperTest {

    private OrganizationAttributeMapper organizationAttributeMapper;

    @BeforeEach
    void setUp() {
        organizationAttributeMapper = new OrganizationAttributeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOrganizationAttributeSample1();
        var actual = organizationAttributeMapper.toEntity(organizationAttributeMapper.toDto(expected));
        assertOrganizationAttributeAllPropertiesEquals(expected, actual);
    }
}
