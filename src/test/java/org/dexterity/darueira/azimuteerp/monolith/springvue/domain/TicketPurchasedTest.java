package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendeeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InvoiceTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchasedTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketPurchasedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketPurchased.class);
        TicketPurchased ticketPurchased1 = getTicketPurchasedSample1();
        TicketPurchased ticketPurchased2 = new TicketPurchased();
        assertThat(ticketPurchased1).isNotEqualTo(ticketPurchased2);

        ticketPurchased2.setId(ticketPurchased1.getId());
        assertThat(ticketPurchased1).isEqualTo(ticketPurchased2);

        ticketPurchased2 = getTicketPurchasedSample2();
        assertThat(ticketPurchased1).isNotEqualTo(ticketPurchased2);
    }

    @Test
    void eventTest() {
        TicketPurchased ticketPurchased = getTicketPurchasedRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        ticketPurchased.setEvent(eventBack);
        assertThat(ticketPurchased.getEvent()).isEqualTo(eventBack);

        ticketPurchased.event(null);
        assertThat(ticketPurchased.getEvent()).isNull();
    }

    @Test
    void invoiceTest() {
        TicketPurchased ticketPurchased = getTicketPurchasedRandomSampleGenerator();
        Invoice invoiceBack = getInvoiceRandomSampleGenerator();

        ticketPurchased.setInvoice(invoiceBack);
        assertThat(ticketPurchased.getInvoice()).isEqualTo(invoiceBack);

        ticketPurchased.invoice(null);
        assertThat(ticketPurchased.getInvoice()).isNull();
    }

    @Test
    void eventAttendeesListTest() {
        TicketPurchased ticketPurchased = getTicketPurchasedRandomSampleGenerator();
        EventAttendee eventAttendeeBack = getEventAttendeeRandomSampleGenerator();

        ticketPurchased.addEventAttendeesList(eventAttendeeBack);
        assertThat(ticketPurchased.getEventAttendeesLists()).containsOnly(eventAttendeeBack);
        assertThat(eventAttendeeBack.getTicketPurchased()).isEqualTo(ticketPurchased);

        ticketPurchased.removeEventAttendeesList(eventAttendeeBack);
        assertThat(ticketPurchased.getEventAttendeesLists()).doesNotContain(eventAttendeeBack);
        assertThat(eventAttendeeBack.getTicketPurchased()).isNull();

        ticketPurchased.eventAttendeesLists(new HashSet<>(Set.of(eventAttendeeBack)));
        assertThat(ticketPurchased.getEventAttendeesLists()).containsOnly(eventAttendeeBack);
        assertThat(eventAttendeeBack.getTicketPurchased()).isEqualTo(ticketPurchased);

        ticketPurchased.setEventAttendeesLists(new HashSet<>());
        assertThat(ticketPurchased.getEventAttendeesLists()).doesNotContain(eventAttendeeBack);
        assertThat(eventAttendeeBack.getTicketPurchased()).isNull();
    }
}
