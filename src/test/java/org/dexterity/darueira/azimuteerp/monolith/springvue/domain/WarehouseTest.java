package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransactionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevelTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.WarehouseTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WarehouseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Warehouse.class);
        Warehouse warehouse1 = getWarehouseSample1();
        Warehouse warehouse2 = new Warehouse();
        assertThat(warehouse1).isNotEqualTo(warehouse2);

        warehouse2.setId(warehouse1.getId());
        assertThat(warehouse1).isEqualTo(warehouse2);

        warehouse2 = getWarehouseSample2();
        assertThat(warehouse1).isNotEqualTo(warehouse2);
    }

    @Test
    void inventoryTransactionsListTest() {
        Warehouse warehouse = getWarehouseRandomSampleGenerator();
        InventoryTransaction inventoryTransactionBack = getInventoryTransactionRandomSampleGenerator();

        warehouse.addInventoryTransactionsList(inventoryTransactionBack);
        assertThat(warehouse.getInventoryTransactionsLists()).containsOnly(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getWarehouse()).isEqualTo(warehouse);

        warehouse.removeInventoryTransactionsList(inventoryTransactionBack);
        assertThat(warehouse.getInventoryTransactionsLists()).doesNotContain(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getWarehouse()).isNull();

        warehouse.inventoryTransactionsLists(new HashSet<>(Set.of(inventoryTransactionBack)));
        assertThat(warehouse.getInventoryTransactionsLists()).containsOnly(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getWarehouse()).isEqualTo(warehouse);

        warehouse.setInventoryTransactionsLists(new HashSet<>());
        assertThat(warehouse.getInventoryTransactionsLists()).doesNotContain(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getWarehouse()).isNull();
    }

    @Test
    void stockLevelsListTest() {
        Warehouse warehouse = getWarehouseRandomSampleGenerator();
        StockLevel stockLevelBack = getStockLevelRandomSampleGenerator();

        warehouse.addStockLevelsList(stockLevelBack);
        assertThat(warehouse.getStockLevelsLists()).containsOnly(stockLevelBack);
        assertThat(stockLevelBack.getWarehouse()).isEqualTo(warehouse);

        warehouse.removeStockLevelsList(stockLevelBack);
        assertThat(warehouse.getStockLevelsLists()).doesNotContain(stockLevelBack);
        assertThat(stockLevelBack.getWarehouse()).isNull();

        warehouse.stockLevelsLists(new HashSet<>(Set.of(stockLevelBack)));
        assertThat(warehouse.getStockLevelsLists()).containsOnly(stockLevelBack);
        assertThat(stockLevelBack.getWarehouse()).isEqualTo(warehouse);

        warehouse.setStockLevelsLists(new HashSet<>());
        assertThat(warehouse.getStockLevelsLists()).doesNotContain(stockLevelBack);
        assertThat(stockLevelBack.getWarehouse()).isNull();
    }
}
