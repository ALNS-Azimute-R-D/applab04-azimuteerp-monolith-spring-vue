package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPersonAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPersonTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeOfPersonMapperTest {

    private TypeOfPersonMapper typeOfPersonMapper;

    @BeforeEach
    void setUp() {
        typeOfPersonMapper = new TypeOfPersonMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTypeOfPersonSample1();
        var actual = typeOfPersonMapper.toEntity(typeOfPersonMapper.toDto(expected));
        assertTypeOfPersonAllPropertiesEquals(expected, actual);
    }
}
