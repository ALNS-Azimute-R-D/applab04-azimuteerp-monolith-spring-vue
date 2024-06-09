package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmpAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmpTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RawAssetProcTmpMapperTest {

    private RawAssetProcTmpMapper rawAssetProcTmpMapper;

    @BeforeEach
    void setUp() {
        rawAssetProcTmpMapper = new RawAssetProcTmpMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRawAssetProcTmpSample1();
        var actual = rawAssetProcTmpMapper.toEntity(rawAssetProcTmpMapper.toDto(expected));
        assertRawAssetProcTmpAllPropertiesEquals(expected, actual);
    }
}
