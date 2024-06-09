package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EventAttendee.
 */
@Entity
@Table(name = "tb_event_attendee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventAttendee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "attended_as_role", length = 30, nullable = false)
    private String attendedAsRole;

    @Column(name = "was_present_in_event")
    private Boolean wasPresentInEvent;

    @Column(name = "should_buy_ticket")
    private Boolean shouldBuyTicket;

    @Size(max = 20)
    @Column(name = "ticket_number", length = 20)
    private String ticketNumber;

    @Size(max = 10)
    @Column(name = "seat_number", length = 10)
    private String seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "typeOfPerson",
            "district",
            "managerPerson",
            "managedPersonsLists",
            "organizationMembershipsLists",
            "suppliersLists",
            "customersLists",
            "activitiesLists",
            "promotedEventsLists",
            "eventsProgramsLists",
            "scheduledActivitiesLists",
            "eventAttendeesLists",
        },
        allowSetters = true
    )
    private Person attendeePerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "mainVenue",
            "typeOfEvent",
            "promoteurPerson",
            "assetCollections",
            "eventProgramsLists",
            "ticketsPurchasedLists",
            "eventAttendeesLists",
        },
        allowSetters = true
    )
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "event", "invoice", "eventAttendeesLists" }, allowSetters = true)
    private TicketPurchased ticketPurchased;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventAttendee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttendedAsRole() {
        return this.attendedAsRole;
    }

    public EventAttendee attendedAsRole(String attendedAsRole) {
        this.setAttendedAsRole(attendedAsRole);
        return this;
    }

    public void setAttendedAsRole(String attendedAsRole) {
        this.attendedAsRole = attendedAsRole;
    }

    public Boolean getWasPresentInEvent() {
        return this.wasPresentInEvent;
    }

    public EventAttendee wasPresentInEvent(Boolean wasPresentInEvent) {
        this.setWasPresentInEvent(wasPresentInEvent);
        return this;
    }

    public void setWasPresentInEvent(Boolean wasPresentInEvent) {
        this.wasPresentInEvent = wasPresentInEvent;
    }

    public Boolean getShouldBuyTicket() {
        return this.shouldBuyTicket;
    }

    public EventAttendee shouldBuyTicket(Boolean shouldBuyTicket) {
        this.setShouldBuyTicket(shouldBuyTicket);
        return this;
    }

    public void setShouldBuyTicket(Boolean shouldBuyTicket) {
        this.shouldBuyTicket = shouldBuyTicket;
    }

    public String getTicketNumber() {
        return this.ticketNumber;
    }

    public EventAttendee ticketNumber(String ticketNumber) {
        this.setTicketNumber(ticketNumber);
        return this;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getSeatNumber() {
        return this.seatNumber;
    }

    public EventAttendee seatNumber(String seatNumber) {
        this.setSeatNumber(seatNumber);
        return this;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Person getAttendeePerson() {
        return this.attendeePerson;
    }

    public void setAttendeePerson(Person person) {
        this.attendeePerson = person;
    }

    public EventAttendee attendeePerson(Person person) {
        this.setAttendeePerson(person);
        return this;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventAttendee event(Event event) {
        this.setEvent(event);
        return this;
    }

    public TicketPurchased getTicketPurchased() {
        return this.ticketPurchased;
    }

    public void setTicketPurchased(TicketPurchased ticketPurchased) {
        this.ticketPurchased = ticketPurchased;
    }

    public EventAttendee ticketPurchased(TicketPurchased ticketPurchased) {
        this.setTicketPurchased(ticketPurchased);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventAttendee)) {
            return false;
        }
        return getId() != null && getId().equals(((EventAttendee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventAttendee{" +
            "id=" + getId() +
            ", attendedAsRole='" + getAttendedAsRole() + "'" +
            ", wasPresentInEvent='" + getWasPresentInEvent() + "'" +
            ", shouldBuyTicket='" + getShouldBuyTicket() + "'" +
            ", ticketNumber='" + getTicketNumber() + "'" +
            ", seatNumber='" + getSeatNumber() + "'" +
            "}";
    }
}
