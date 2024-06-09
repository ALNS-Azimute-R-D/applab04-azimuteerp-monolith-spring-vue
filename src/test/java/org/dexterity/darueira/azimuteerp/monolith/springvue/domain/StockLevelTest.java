package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProductTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevelTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.WarehouseTestSamples.*;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StockLevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockLevel.class);
        StockLevel stockLevel1 = getStockLevelSample1();
        StockLevel stockLevel2 = new StockLevel();
        assertThat(stockLevel1).isNotEqualTo(stockLevel2);

        stockLevel2.setId(stockLevel1.getId());
        assertThat(stockLevel1).isEqualTo(stockLevel2);

        stockLevel2 = getStockLevelSample2();
        assertThat(stockLevel1).isNotEqualTo(stockLevel2);
    }

    @Test
    void warehouseTest() {
        StockLevel stockLevel = getStockLevelRandomSampleGenerator();
        Warehouse warehouseBack = getWarehouseRandomSampleGenerator();

        stockLevel.setWarehouse(warehouseBack);
        assertThat(stockLevel.getWarehouse()).isEqualTo(warehouseBack);

        stockLevel.warehouse(null);
        assertThat(stockLevel.getWarehouse()).isNull();
    }

    @Test
    void productTest() {
        StockLevel stockLevel = getStockLevelRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        stockLevel.setProduct(productBack);
        assertThat(stockLevel.getProduct()).isEqualTo(productBack);

        stockLevel.product(null);
        assertThat(stockLevel.getProduct()).isNull();
    }
}
