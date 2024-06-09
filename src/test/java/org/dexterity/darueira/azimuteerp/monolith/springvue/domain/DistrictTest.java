package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocalityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.DistrictTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCityTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DistrictTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(District.class);
        District district1 = getDistrictSample1();
        District district2 = new District();
        assertThat(district1).isNotEqualTo(district2);

        district2.setId(district1.getId());
        assertThat(district1).isEqualTo(district2);

        district2 = getDistrictSample2();
        assertThat(district1).isNotEqualTo(district2);
    }

    @Test
    void townCityTest() {
        District district = getDistrictRandomSampleGenerator();
        TownCity townCityBack = getTownCityRandomSampleGenerator();

        district.setTownCity(townCityBack);
        assertThat(district.getTownCity()).isEqualTo(townCityBack);

        district.townCity(null);
        assertThat(district.getTownCity()).isNull();
    }

    @Test
    void commonLocalitiesListTest() {
        District district = getDistrictRandomSampleGenerator();
        CommonLocality commonLocalityBack = getCommonLocalityRandomSampleGenerator();

        district.addCommonLocalitiesList(commonLocalityBack);
        assertThat(district.getCommonLocalitiesLists()).containsOnly(commonLocalityBack);
        assertThat(commonLocalityBack.getDistrict()).isEqualTo(district);

        district.removeCommonLocalitiesList(commonLocalityBack);
        assertThat(district.getCommonLocalitiesLists()).doesNotContain(commonLocalityBack);
        assertThat(commonLocalityBack.getDistrict()).isNull();

        district.commonLocalitiesLists(new HashSet<>(Set.of(commonLocalityBack)));
        assertThat(district.getCommonLocalitiesLists()).containsOnly(commonLocalityBack);
        assertThat(commonLocalityBack.getDistrict()).isEqualTo(district);

        district.setCommonLocalitiesLists(new HashSet<>());
        assertThat(district.getCommonLocalitiesLists()).doesNotContain(commonLocalityBack);
        assertThat(commonLocalityBack.getDistrict()).isNull();
    }

    @Test
    void personsListTest() {
        District district = getDistrictRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        district.addPersonsList(personBack);
        assertThat(district.getPersonsLists()).containsOnly(personBack);
        assertThat(personBack.getDistrict()).isEqualTo(district);

        district.removePersonsList(personBack);
        assertThat(district.getPersonsLists()).doesNotContain(personBack);
        assertThat(personBack.getDistrict()).isNull();

        district.personsLists(new HashSet<>(Set.of(personBack)));
        assertThat(district.getPersonsLists()).containsOnly(personBack);
        assertThat(personBack.getDistrict()).isEqualTo(district);

        district.setPersonsLists(new HashSet<>());
        assertThat(district.getPersonsLists()).doesNotContain(personBack);
        assertThat(personBack.getDistrict()).isNull();
    }

    @Test
    void customersListTest() {
        District district = getDistrictRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        district.addCustomersList(customerBack);
        assertThat(district.getCustomersLists()).containsOnly(customerBack);
        assertThat(customerBack.getDistrict()).isEqualTo(district);

        district.removeCustomersList(customerBack);
        assertThat(district.getCustomersLists()).doesNotContain(customerBack);
        assertThat(customerBack.getDistrict()).isNull();

        district.customersLists(new HashSet<>(Set.of(customerBack)));
        assertThat(district.getCustomersLists()).containsOnly(customerBack);
        assertThat(customerBack.getDistrict()).isEqualTo(district);

        district.setCustomersLists(new HashSet<>());
        assertThat(district.getCustomersLists()).doesNotContain(customerBack);
        assertThat(customerBack.getDistrict()).isNull();
    }
}
