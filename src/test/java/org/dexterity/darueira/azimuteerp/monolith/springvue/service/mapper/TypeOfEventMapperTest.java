package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEventAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEventTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeOfEventMapperTest {

    private TypeOfEventMapper typeOfEventMapper;

    @BeforeEach
    void setUp() {
        typeOfEventMapper = new TypeOfEventMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTypeOfEventSample1();
        var actual = typeOfEventMapper.toEntity(typeOfEventMapper.toDto(expected));
        assertTypeOfEventAllPropertiesEquals(expected, actual);
    }
}
