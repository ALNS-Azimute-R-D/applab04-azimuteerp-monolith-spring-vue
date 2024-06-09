package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenreAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenreTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArtisticGenreMapperTest {

    private ArtisticGenreMapper artisticGenreMapper;

    @BeforeEach
    void setUp() {
        artisticGenreMapper = new ArtisticGenreMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getArtisticGenreSample1();
        var actual = artisticGenreMapper.toEntity(artisticGenreMapper.toDto(expected));
        assertArtisticGenreAllPropertiesEquals(expected, actual);
    }
}
