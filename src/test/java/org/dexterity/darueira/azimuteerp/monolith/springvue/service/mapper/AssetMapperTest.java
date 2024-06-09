package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetMapperTest {

    private AssetMapper assetMapper;

    @BeforeEach
    void setUp() {
        assetMapper = new AssetMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAssetSample1();
        var actual = assetMapper.toEntity(assetMapper.toDto(expected));
        assertAssetAllPropertiesEquals(expected, actual);
    }
}
