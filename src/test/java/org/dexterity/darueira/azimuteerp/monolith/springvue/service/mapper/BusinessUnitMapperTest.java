package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnitAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnitTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessUnitMapperTest {

    private BusinessUnitMapper businessUnitMapper;

    @BeforeEach
    void setUp() {
        businessUnitMapper = new BusinessUnitMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBusinessUnitSample1();
        var actual = businessUnitMapper.toEntity(businessUnitMapper.toDto(expected));
        assertBusinessUnitAllPropertiesEquals(expected, actual);
    }
}
