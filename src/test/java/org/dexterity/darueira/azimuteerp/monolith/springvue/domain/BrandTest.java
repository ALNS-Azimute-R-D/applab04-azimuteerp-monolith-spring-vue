package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BrandTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProductTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BrandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brand.class);
        Brand brand1 = getBrandSample1();
        Brand brand2 = new Brand();
        assertThat(brand1).isNotEqualTo(brand2);

        brand2.setId(brand1.getId());
        assertThat(brand1).isEqualTo(brand2);

        brand2 = getBrandSample2();
        assertThat(brand1).isNotEqualTo(brand2);
    }

    @Test
    void productsListTest() {
        Brand brand = getBrandRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        brand.addProductsList(productBack);
        assertThat(brand.getProductsLists()).containsOnly(productBack);
        assertThat(productBack.getBrand()).isEqualTo(brand);

        brand.removeProductsList(productBack);
        assertThat(brand.getProductsLists()).doesNotContain(productBack);
        assertThat(productBack.getBrand()).isNull();

        brand.productsLists(new HashSet<>(Set.of(productBack)));
        assertThat(brand.getProductsLists()).containsOnly(productBack);
        assertThat(productBack.getBrand()).isEqualTo(brand);

        brand.setProductsLists(new HashSet<>());
        assertThat(brand.getProductsLists()).doesNotContain(productBack);
        assertThat(productBack.getBrand()).isNull();
    }
}
