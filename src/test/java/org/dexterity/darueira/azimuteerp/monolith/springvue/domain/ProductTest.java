package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BrandTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransactionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProductTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevelTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.SupplierTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void brandTest() {
        Product product = getProductRandomSampleGenerator();
        Brand brandBack = getBrandRandomSampleGenerator();

        product.setBrand(brandBack);
        assertThat(product.getBrand()).isEqualTo(brandBack);

        product.brand(null);
        assertThat(product.getBrand()).isNull();
    }

    @Test
    void toSupplierTest() {
        Product product = getProductRandomSampleGenerator();
        Supplier supplierBack = getSupplierRandomSampleGenerator();

        product.addToSupplier(supplierBack);
        assertThat(product.getToSuppliers()).containsOnly(supplierBack);

        product.removeToSupplier(supplierBack);
        assertThat(product.getToSuppliers()).doesNotContain(supplierBack);

        product.toSuppliers(new HashSet<>(Set.of(supplierBack)));
        assertThat(product.getToSuppliers()).containsOnly(supplierBack);

        product.setToSuppliers(new HashSet<>());
        assertThat(product.getToSuppliers()).doesNotContain(supplierBack);
    }

    @Test
    void productsListTest() {
        Product product = getProductRandomSampleGenerator();
        InventoryTransaction inventoryTransactionBack = getInventoryTransactionRandomSampleGenerator();

        product.addProductsList(inventoryTransactionBack);
        assertThat(product.getProductsLists()).containsOnly(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getProduct()).isEqualTo(product);

        product.removeProductsList(inventoryTransactionBack);
        assertThat(product.getProductsLists()).doesNotContain(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getProduct()).isNull();

        product.productsLists(new HashSet<>(Set.of(inventoryTransactionBack)));
        assertThat(product.getProductsLists()).containsOnly(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getProduct()).isEqualTo(product);

        product.setProductsLists(new HashSet<>());
        assertThat(product.getProductsLists()).doesNotContain(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getProduct()).isNull();
    }

    @Test
    void stockLevelsListTest() {
        Product product = getProductRandomSampleGenerator();
        StockLevel stockLevelBack = getStockLevelRandomSampleGenerator();

        product.addStockLevelsList(stockLevelBack);
        assertThat(product.getStockLevelsLists()).containsOnly(stockLevelBack);
        assertThat(stockLevelBack.getProduct()).isEqualTo(product);

        product.removeStockLevelsList(stockLevelBack);
        assertThat(product.getStockLevelsLists()).doesNotContain(stockLevelBack);
        assertThat(stockLevelBack.getProduct()).isNull();

        product.stockLevelsLists(new HashSet<>(Set.of(stockLevelBack)));
        assertThat(product.getStockLevelsLists()).containsOnly(stockLevelBack);
        assertThat(stockLevelBack.getProduct()).isEqualTo(product);

        product.setStockLevelsLists(new HashSet<>());
        assertThat(product.getStockLevelsLists()).doesNotContain(stockLevelBack);
        assertThat(stockLevelBack.getProduct()).isNull();
    }
}
