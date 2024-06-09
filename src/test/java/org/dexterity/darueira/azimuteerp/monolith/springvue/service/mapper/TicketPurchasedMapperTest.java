package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchasedAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchasedTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketPurchasedMapperTest {

    private TicketPurchasedMapper ticketPurchasedMapper;

    @BeforeEach
    void setUp() {
        ticketPurchasedMapper = new TicketPurchasedMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTicketPurchasedSample1();
        var actual = ticketPurchasedMapper.toEntity(ticketPurchasedMapper.toDto(expected));
        assertTicketPurchasedAllPropertiesEquals(expected, actual);
    }
}
