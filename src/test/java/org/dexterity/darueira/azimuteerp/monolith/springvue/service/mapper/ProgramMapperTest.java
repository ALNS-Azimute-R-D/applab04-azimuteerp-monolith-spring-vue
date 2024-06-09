package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProgramAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProgramTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProgramMapperTest {

    private ProgramMapper programMapper;

    @BeforeEach
    void setUp() {
        programMapper = new ProgramMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProgramSample1();
        var actual = programMapper.toEntity(programMapper.toDto(expected));
        assertProgramAllPropertiesEquals(expected, actual);
    }
}
