package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventAttendeeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String attendedAsRole;

    private Boolean wasPresentInEvent;

    private Boolean shouldBuyTicket;

    @Size(max = 20)
    private String ticketNumber;

    @Size(max = 10)
    private String seatNumber;

    private PersonDTO attendeePerson;

    private EventDTO event;

    private TicketPurchasedDTO ticketPurchased;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttendedAsRole() {
        return attendedAsRole;
    }

    public void setAttendedAsRole(String attendedAsRole) {
        this.attendedAsRole = attendedAsRole;
    }

    public Boolean getWasPresentInEvent() {
        return wasPresentInEvent;
    }

    public void setWasPresentInEvent(Boolean wasPresentInEvent) {
        this.wasPresentInEvent = wasPresentInEvent;
    }

    public Boolean getShouldBuyTicket() {
        return shouldBuyTicket;
    }

    public void setShouldBuyTicket(Boolean shouldBuyTicket) {
        this.shouldBuyTicket = shouldBuyTicket;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public PersonDTO getAttendeePerson() {
        return attendeePerson;
    }

    public void setAttendeePerson(PersonDTO attendeePerson) {
        this.attendeePerson = attendeePerson;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public TicketPurchasedDTO getTicketPurchased() {
        return ticketPurchased;
    }

    public void setTicketPurchased(TicketPurchasedDTO ticketPurchased) {
        this.ticketPurchased = ticketPurchased;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventAttendeeDTO)) {
            return false;
        }

        EventAttendeeDTO eventAttendeeDTO = (EventAttendeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventAttendeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventAttendeeDTO{" +
            "id=" + getId() +
            ", attendedAsRole='" + getAttendedAsRole() + "'" +
            ", wasPresentInEvent='" + getWasPresentInEvent() + "'" +
            ", shouldBuyTicket='" + getShouldBuyTicket() + "'" +
            ", ticketNumber='" + getTicketNumber() + "'" +
            ", seatNumber='" + getSeatNumber() + "'" +
            ", attendeePerson=" + getAttendeePerson() +
            ", event=" + getEvent() +
            ", ticketPurchased=" + getTicketPurchased() +
            "}";
    }
}
