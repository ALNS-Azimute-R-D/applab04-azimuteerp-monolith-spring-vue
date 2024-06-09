package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventMapperTest {

    private EventMapper eventMapper;

    @BeforeEach
    void setUp() {
        eventMapper = new EventMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEventSample1();
        var actual = eventMapper.toEntity(eventMapper.toDto(expected));
        assertEventAllPropertiesEquals(expected, actual);
    }
}
