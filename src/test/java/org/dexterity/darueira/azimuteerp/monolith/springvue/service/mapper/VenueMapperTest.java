package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.VenueAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.VenueTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenueMapperTest {

    private VenueMapper venueMapper;

    @BeforeEach
    void setUp() {
        venueMapper = new VenueMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVenueSample1();
        var actual = venueMapper.toEntity(venueMapper.toDto(expected));
        assertVenueAllPropertiesEquals(expected, actual);
    }
}
