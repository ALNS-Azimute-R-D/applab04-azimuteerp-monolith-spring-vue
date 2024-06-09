package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransactionAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransactionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTransactionMapperTest {

    private InventoryTransactionMapper inventoryTransactionMapper;

    @BeforeEach
    void setUp() {
        inventoryTransactionMapper = new InventoryTransactionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInventoryTransactionSample1();
        var actual = inventoryTransactionMapper.toEntity(inventoryTransactionMapper.toDto(expected));
        assertInventoryTransactionAllPropertiesEquals(expected, actual);
    }
}
