package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CountryTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProvinceTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = getCountrySample1();
        Country country2 = new Country();
        assertThat(country1).isNotEqualTo(country2);

        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);

        country2 = getCountrySample2();
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    void provincesListTest() {
        Country country = getCountryRandomSampleGenerator();
        Province provinceBack = getProvinceRandomSampleGenerator();

        country.addProvincesList(provinceBack);
        assertThat(country.getProvincesLists()).containsOnly(provinceBack);
        assertThat(provinceBack.getCountry()).isEqualTo(country);

        country.removeProvincesList(provinceBack);
        assertThat(country.getProvincesLists()).doesNotContain(provinceBack);
        assertThat(provinceBack.getCountry()).isNull();

        country.provincesLists(new HashSet<>(Set.of(provinceBack)));
        assertThat(country.getProvincesLists()).containsOnly(provinceBack);
        assertThat(provinceBack.getCountry()).isEqualTo(country);

        country.setProvincesLists(new HashSet<>());
        assertThat(country.getProvincesLists()).doesNotContain(provinceBack);
        assertThat(provinceBack.getCountry()).isNull();
    }
}
