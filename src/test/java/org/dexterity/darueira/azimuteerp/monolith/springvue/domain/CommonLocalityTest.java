package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocalityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.DistrictTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.VenueTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommonLocalityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonLocality.class);
        CommonLocality commonLocality1 = getCommonLocalitySample1();
        CommonLocality commonLocality2 = new CommonLocality();
        assertThat(commonLocality1).isNotEqualTo(commonLocality2);

        commonLocality2.setId(commonLocality1.getId());
        assertThat(commonLocality1).isEqualTo(commonLocality2);

        commonLocality2 = getCommonLocalitySample2();
        assertThat(commonLocality1).isNotEqualTo(commonLocality2);
    }

    @Test
    void districtTest() {
        CommonLocality commonLocality = getCommonLocalityRandomSampleGenerator();
        District districtBack = getDistrictRandomSampleGenerator();

        commonLocality.setDistrict(districtBack);
        assertThat(commonLocality.getDistrict()).isEqualTo(districtBack);

        commonLocality.district(null);
        assertThat(commonLocality.getDistrict()).isNull();
    }

    @Test
    void venuesListTest() {
        CommonLocality commonLocality = getCommonLocalityRandomSampleGenerator();
        Venue venueBack = getVenueRandomSampleGenerator();

        commonLocality.addVenuesList(venueBack);
        assertThat(commonLocality.getVenuesLists()).containsOnly(venueBack);
        assertThat(venueBack.getCommonLocality()).isEqualTo(commonLocality);

        commonLocality.removeVenuesList(venueBack);
        assertThat(commonLocality.getVenuesLists()).doesNotContain(venueBack);
        assertThat(venueBack.getCommonLocality()).isNull();

        commonLocality.venuesLists(new HashSet<>(Set.of(venueBack)));
        assertThat(commonLocality.getVenuesLists()).containsOnly(venueBack);
        assertThat(venueBack.getCommonLocality()).isEqualTo(commonLocality);

        commonLocality.setVenuesLists(new HashSet<>());
        assertThat(commonLocality.getVenuesLists()).doesNotContain(venueBack);
        assertThat(venueBack.getCommonLocality()).isNull();
    }
}
