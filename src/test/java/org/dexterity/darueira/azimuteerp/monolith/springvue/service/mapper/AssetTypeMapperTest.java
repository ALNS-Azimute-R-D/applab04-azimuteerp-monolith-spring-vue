package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTypeAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetTypeMapperTest {

    private AssetTypeMapper assetTypeMapper;

    @BeforeEach
    void setUp() {
        assetTypeMapper = new AssetTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAssetTypeSample1();
        var actual = assetTypeMapper.toEntity(assetTypeMapper.toDto(expected));
        assertAssetTypeAllPropertiesEquals(expected, actual);
    }
}
