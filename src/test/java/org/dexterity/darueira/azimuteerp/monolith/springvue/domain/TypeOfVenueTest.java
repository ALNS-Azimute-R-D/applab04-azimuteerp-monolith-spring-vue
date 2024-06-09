package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenueTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.VenueTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfVenueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfVenue.class);
        TypeOfVenue typeOfVenue1 = getTypeOfVenueSample1();
        TypeOfVenue typeOfVenue2 = new TypeOfVenue();
        assertThat(typeOfVenue1).isNotEqualTo(typeOfVenue2);

        typeOfVenue2.setId(typeOfVenue1.getId());
        assertThat(typeOfVenue1).isEqualTo(typeOfVenue2);

        typeOfVenue2 = getTypeOfVenueSample2();
        assertThat(typeOfVenue1).isNotEqualTo(typeOfVenue2);
    }

    @Test
    void venuesListTest() {
        TypeOfVenue typeOfVenue = getTypeOfVenueRandomSampleGenerator();
        Venue venueBack = getVenueRandomSampleGenerator();

        typeOfVenue.addVenuesList(venueBack);
        assertThat(typeOfVenue.getVenuesLists()).containsOnly(venueBack);
        assertThat(venueBack.getTypeOfVenue()).isEqualTo(typeOfVenue);

        typeOfVenue.removeVenuesList(venueBack);
        assertThat(typeOfVenue.getVenuesLists()).doesNotContain(venueBack);
        assertThat(venueBack.getTypeOfVenue()).isNull();

        typeOfVenue.venuesLists(new HashSet<>(Set.of(venueBack)));
        assertThat(typeOfVenue.getVenuesLists()).containsOnly(venueBack);
        assertThat(venueBack.getTypeOfVenue()).isEqualTo(typeOfVenue);

        typeOfVenue.setVenuesLists(new HashSet<>());
        assertThat(typeOfVenue.getVenuesLists()).doesNotContain(venueBack);
        assertThat(venueBack.getTypeOfVenue()).isNull();
    }
}
