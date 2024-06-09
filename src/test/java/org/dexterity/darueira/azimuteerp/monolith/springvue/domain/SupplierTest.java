package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransactionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProductTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.SupplierTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supplier.class);
        Supplier supplier1 = getSupplierSample1();
        Supplier supplier2 = new Supplier();
        assertThat(supplier1).isNotEqualTo(supplier2);

        supplier2.setId(supplier1.getId());
        assertThat(supplier1).isEqualTo(supplier2);

        supplier2 = getSupplierSample2();
        assertThat(supplier1).isNotEqualTo(supplier2);
    }

    @Test
    void representativePersonTest() {
        Supplier supplier = getSupplierRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        supplier.setRepresentativePerson(personBack);
        assertThat(supplier.getRepresentativePerson()).isEqualTo(personBack);

        supplier.representativePerson(null);
        assertThat(supplier.getRepresentativePerson()).isNull();
    }

    @Test
    void inventoryTransactionsListTest() {
        Supplier supplier = getSupplierRandomSampleGenerator();
        InventoryTransaction inventoryTransactionBack = getInventoryTransactionRandomSampleGenerator();

        supplier.addInventoryTransactionsList(inventoryTransactionBack);
        assertThat(supplier.getInventoryTransactionsLists()).containsOnly(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getSupplier()).isEqualTo(supplier);

        supplier.removeInventoryTransactionsList(inventoryTransactionBack);
        assertThat(supplier.getInventoryTransactionsLists()).doesNotContain(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getSupplier()).isNull();

        supplier.inventoryTransactionsLists(new HashSet<>(Set.of(inventoryTransactionBack)));
        assertThat(supplier.getInventoryTransactionsLists()).containsOnly(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getSupplier()).isEqualTo(supplier);

        supplier.setInventoryTransactionsLists(new HashSet<>());
        assertThat(supplier.getInventoryTransactionsLists()).doesNotContain(inventoryTransactionBack);
        assertThat(inventoryTransactionBack.getSupplier()).isNull();
    }

    @Test
    void toProductTest() {
        Supplier supplier = getSupplierRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        supplier.addToProduct(productBack);
        assertThat(supplier.getToProducts()).containsOnly(productBack);
        assertThat(productBack.getToSuppliers()).containsOnly(supplier);

        supplier.removeToProduct(productBack);
        assertThat(supplier.getToProducts()).doesNotContain(productBack);
        assertThat(productBack.getToSuppliers()).doesNotContain(supplier);

        supplier.toProducts(new HashSet<>(Set.of(productBack)));
        assertThat(supplier.getToProducts()).containsOnly(productBack);
        assertThat(productBack.getToSuppliers()).containsOnly(supplier);

        supplier.setToProducts(new HashSet<>());
        assertThat(supplier.getToProducts()).doesNotContain(productBack);
        assertThat(productBack.getToSuppliers()).doesNotContain(supplier);
    }
}
