package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendeeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchasedTestSamples.*;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventAttendeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventAttendee.class);
        EventAttendee eventAttendee1 = getEventAttendeeSample1();
        EventAttendee eventAttendee2 = new EventAttendee();
        assertThat(eventAttendee1).isNotEqualTo(eventAttendee2);

        eventAttendee2.setId(eventAttendee1.getId());
        assertThat(eventAttendee1).isEqualTo(eventAttendee2);

        eventAttendee2 = getEventAttendeeSample2();
        assertThat(eventAttendee1).isNotEqualTo(eventAttendee2);
    }

    @Test
    void attendeePersonTest() {
        EventAttendee eventAttendee = getEventAttendeeRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        eventAttendee.setAttendeePerson(personBack);
        assertThat(eventAttendee.getAttendeePerson()).isEqualTo(personBack);

        eventAttendee.attendeePerson(null);
        assertThat(eventAttendee.getAttendeePerson()).isNull();
    }

    @Test
    void eventTest() {
        EventAttendee eventAttendee = getEventAttendeeRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        eventAttendee.setEvent(eventBack);
        assertThat(eventAttendee.getEvent()).isEqualTo(eventBack);

        eventAttendee.event(null);
        assertThat(eventAttendee.getEvent()).isNull();
    }

    @Test
    void ticketPurchasedTest() {
        EventAttendee eventAttendee = getEventAttendeeRandomSampleGenerator();
        TicketPurchased ticketPurchasedBack = getTicketPurchasedRandomSampleGenerator();

        eventAttendee.setTicketPurchased(ticketPurchasedBack);
        assertThat(eventAttendee.getTicketPurchased()).isEqualTo(ticketPurchasedBack);

        eventAttendee.ticketPurchased(null);
        assertThat(eventAttendee.getTicketPurchased()).isNull();
    }
}
