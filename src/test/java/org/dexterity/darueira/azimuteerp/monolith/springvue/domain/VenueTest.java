package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocalityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenueTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.VenueTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VenueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venue.class);
        Venue venue1 = getVenueSample1();
        Venue venue2 = new Venue();
        assertThat(venue1).isNotEqualTo(venue2);

        venue2.setId(venue1.getId());
        assertThat(venue1).isEqualTo(venue2);

        venue2 = getVenueSample2();
        assertThat(venue1).isNotEqualTo(venue2);
    }

    @Test
    void typeOfVenueTest() {
        Venue venue = getVenueRandomSampleGenerator();
        TypeOfVenue typeOfVenueBack = getTypeOfVenueRandomSampleGenerator();

        venue.setTypeOfVenue(typeOfVenueBack);
        assertThat(venue.getTypeOfVenue()).isEqualTo(typeOfVenueBack);

        venue.typeOfVenue(null);
        assertThat(venue.getTypeOfVenue()).isNull();
    }

    @Test
    void commonLocalityTest() {
        Venue venue = getVenueRandomSampleGenerator();
        CommonLocality commonLocalityBack = getCommonLocalityRandomSampleGenerator();

        venue.setCommonLocality(commonLocalityBack);
        assertThat(venue.getCommonLocality()).isEqualTo(commonLocalityBack);

        venue.commonLocality(null);
        assertThat(venue.getCommonLocality()).isNull();
    }

    @Test
    void eventsListTest() {
        Venue venue = getVenueRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        venue.addEventsList(eventBack);
        assertThat(venue.getEventsLists()).containsOnly(eventBack);
        assertThat(eventBack.getMainVenue()).isEqualTo(venue);

        venue.removeEventsList(eventBack);
        assertThat(venue.getEventsLists()).doesNotContain(eventBack);
        assertThat(eventBack.getMainVenue()).isNull();

        venue.eventsLists(new HashSet<>(Set.of(eventBack)));
        assertThat(venue.getEventsLists()).containsOnly(eventBack);
        assertThat(eventBack.getMainVenue()).isEqualTo(venue);

        venue.setEventsLists(new HashSet<>());
        assertThat(venue.getEventsLists()).doesNotContain(eventBack);
        assertThat(eventBack.getMainVenue()).isNull();
    }
}
