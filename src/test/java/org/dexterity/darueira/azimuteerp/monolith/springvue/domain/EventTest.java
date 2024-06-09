package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollectionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendeeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgramTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchasedTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.VenueTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = getEventSample1();
        Event event2 = new Event();
        assertThat(event1).isNotEqualTo(event2);

        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);

        event2 = getEventSample2();
        assertThat(event1).isNotEqualTo(event2);
    }

    @Test
    void mainVenueTest() {
        Event event = getEventRandomSampleGenerator();
        Venue venueBack = getVenueRandomSampleGenerator();

        event.setMainVenue(venueBack);
        assertThat(event.getMainVenue()).isEqualTo(venueBack);

        event.mainVenue(null);
        assertThat(event.getMainVenue()).isNull();
    }

    @Test
    void typeOfEventTest() {
        Event event = getEventRandomSampleGenerator();
        TypeOfEvent typeOfEventBack = getTypeOfEventRandomSampleGenerator();

        event.setTypeOfEvent(typeOfEventBack);
        assertThat(event.getTypeOfEvent()).isEqualTo(typeOfEventBack);

        event.typeOfEvent(null);
        assertThat(event.getTypeOfEvent()).isNull();
    }

    @Test
    void promoteurPersonTest() {
        Event event = getEventRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        event.setPromoteurPerson(personBack);
        assertThat(event.getPromoteurPerson()).isEqualTo(personBack);

        event.promoteurPerson(null);
        assertThat(event.getPromoteurPerson()).isNull();
    }

    @Test
    void assetCollectionTest() {
        Event event = getEventRandomSampleGenerator();
        AssetCollection assetCollectionBack = getAssetCollectionRandomSampleGenerator();

        event.addAssetCollection(assetCollectionBack);
        assertThat(event.getAssetCollections()).containsOnly(assetCollectionBack);

        event.removeAssetCollection(assetCollectionBack);
        assertThat(event.getAssetCollections()).doesNotContain(assetCollectionBack);

        event.assetCollections(new HashSet<>(Set.of(assetCollectionBack)));
        assertThat(event.getAssetCollections()).containsOnly(assetCollectionBack);

        event.setAssetCollections(new HashSet<>());
        assertThat(event.getAssetCollections()).doesNotContain(assetCollectionBack);
    }

    @Test
    void eventProgramsListTest() {
        Event event = getEventRandomSampleGenerator();
        EventProgram eventProgramBack = getEventProgramRandomSampleGenerator();

        event.addEventProgramsList(eventProgramBack);
        assertThat(event.getEventProgramsLists()).containsOnly(eventProgramBack);
        assertThat(eventProgramBack.getEvent()).isEqualTo(event);

        event.removeEventProgramsList(eventProgramBack);
        assertThat(event.getEventProgramsLists()).doesNotContain(eventProgramBack);
        assertThat(eventProgramBack.getEvent()).isNull();

        event.eventProgramsLists(new HashSet<>(Set.of(eventProgramBack)));
        assertThat(event.getEventProgramsLists()).containsOnly(eventProgramBack);
        assertThat(eventProgramBack.getEvent()).isEqualTo(event);

        event.setEventProgramsLists(new HashSet<>());
        assertThat(event.getEventProgramsLists()).doesNotContain(eventProgramBack);
        assertThat(eventProgramBack.getEvent()).isNull();
    }

    @Test
    void ticketsPurchasedListTest() {
        Event event = getEventRandomSampleGenerator();
        TicketPurchased ticketPurchasedBack = getTicketPurchasedRandomSampleGenerator();

        event.addTicketsPurchasedList(ticketPurchasedBack);
        assertThat(event.getTicketsPurchasedLists()).containsOnly(ticketPurchasedBack);
        assertThat(ticketPurchasedBack.getEvent()).isEqualTo(event);

        event.removeTicketsPurchasedList(ticketPurchasedBack);
        assertThat(event.getTicketsPurchasedLists()).doesNotContain(ticketPurchasedBack);
        assertThat(ticketPurchasedBack.getEvent()).isNull();

        event.ticketsPurchasedLists(new HashSet<>(Set.of(ticketPurchasedBack)));
        assertThat(event.getTicketsPurchasedLists()).containsOnly(ticketPurchasedBack);
        assertThat(ticketPurchasedBack.getEvent()).isEqualTo(event);

        event.setTicketsPurchasedLists(new HashSet<>());
        assertThat(event.getTicketsPurchasedLists()).doesNotContain(ticketPurchasedBack);
        assertThat(ticketPurchasedBack.getEvent()).isNull();
    }

    @Test
    void eventAttendeesListTest() {
        Event event = getEventRandomSampleGenerator();
        EventAttendee eventAttendeeBack = getEventAttendeeRandomSampleGenerator();

        event.addEventAttendeesList(eventAttendeeBack);
        assertThat(event.getEventAttendeesLists()).containsOnly(eventAttendeeBack);
        assertThat(eventAttendeeBack.getEvent()).isEqualTo(event);

        event.removeEventAttendeesList(eventAttendeeBack);
        assertThat(event.getEventAttendeesLists()).doesNotContain(eventAttendeeBack);
        assertThat(eventAttendeeBack.getEvent()).isNull();

        event.eventAttendeesLists(new HashSet<>(Set.of(eventAttendeeBack)));
        assertThat(event.getEventAttendeesLists()).containsOnly(eventAttendeeBack);
        assertThat(eventAttendeeBack.getEvent()).isEqualTo(event);

        event.setEventAttendeesLists(new HashSet<>());
        assertThat(event.getEventAttendeesLists()).doesNotContain(eventAttendeeBack);
        assertThat(eventAttendeeBack.getEvent()).isNull();
    }
}
