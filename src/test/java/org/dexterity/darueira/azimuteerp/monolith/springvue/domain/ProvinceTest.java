package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CountryTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProvinceTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCityTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProvinceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Province.class);
        Province province1 = getProvinceSample1();
        Province province2 = new Province();
        assertThat(province1).isNotEqualTo(province2);

        province2.setId(province1.getId());
        assertThat(province1).isEqualTo(province2);

        province2 = getProvinceSample2();
        assertThat(province1).isNotEqualTo(province2);
    }

    @Test
    void countryTest() {
        Province province = getProvinceRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        province.setCountry(countryBack);
        assertThat(province.getCountry()).isEqualTo(countryBack);

        province.country(null);
        assertThat(province.getCountry()).isNull();
    }

    @Test
    void townCitiesListTest() {
        Province province = getProvinceRandomSampleGenerator();
        TownCity townCityBack = getTownCityRandomSampleGenerator();

        province.addTownCitiesList(townCityBack);
        assertThat(province.getTownCitiesLists()).containsOnly(townCityBack);
        assertThat(townCityBack.getProvince()).isEqualTo(province);

        province.removeTownCitiesList(townCityBack);
        assertThat(province.getTownCitiesLists()).doesNotContain(townCityBack);
        assertThat(townCityBack.getProvince()).isNull();

        province.townCitiesLists(new HashSet<>(Set.of(townCityBack)));
        assertThat(province.getTownCitiesLists()).containsOnly(townCityBack);
        assertThat(townCityBack.getProvince()).isEqualTo(province);

        province.setTownCitiesLists(new HashSet<>());
        assertThat(province.getTownCitiesLists()).doesNotContain(townCityBack);
        assertThat(townCityBack.getProvince()).isNull();
    }
}
