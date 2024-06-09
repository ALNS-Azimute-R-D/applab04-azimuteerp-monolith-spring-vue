package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmediaAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmediaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TypeOfArtmediaMapperTest {

    private TypeOfArtmediaMapper typeOfArtmediaMapper;

    @BeforeEach
    void setUp() {
        typeOfArtmediaMapper = new TypeOfArtmediaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTypeOfArtmediaSample1();
        var actual = typeOfArtmediaMapper.toEntity(typeOfArtmediaMapper.toDto(expected));
        assertTypeOfArtmediaAllPropertiesEquals(expected, actual);
    }
}
