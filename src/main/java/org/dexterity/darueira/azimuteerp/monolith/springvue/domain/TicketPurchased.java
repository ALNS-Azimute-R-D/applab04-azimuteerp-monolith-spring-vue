package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TicketPurchased.
 */
@Entity
@Table(name = "tb_ticket_purchased")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketPurchased implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "buying_code", length = 30)
    private String buyingCode;

    @Column(name = "due_payment_date")
    private Instant duePaymentDate;

    @Column(name = "amount_of_tickets")
    private Integer amountOfTickets;

    @Column(name = "tax_value", precision = 21, scale = 2)
    private BigDecimal taxValue;

    @Column(name = "ticket_value", precision = 21, scale = 2)
    private BigDecimal ticketValue;

    @Size(max = 50)
    @Column(name = "acquired_seats_numbers", length = 50)
    private String acquiredSeatsNumbers;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

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
    @JsonIgnoreProperties(value = { "preferrablePaymentGateway", "ordersLists", "ticketsPurchasedLists" }, allowSetters = true)
    private Invoice invoice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticketPurchased")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "attendeePerson", "event", "ticketPurchased" }, allowSetters = true)
    private Set<EventAttendee> eventAttendeesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TicketPurchased id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyingCode() {
        return this.buyingCode;
    }

    public TicketPurchased buyingCode(String buyingCode) {
        this.setBuyingCode(buyingCode);
        return this;
    }

    public void setBuyingCode(String buyingCode) {
        this.buyingCode = buyingCode;
    }

    public Instant getDuePaymentDate() {
        return this.duePaymentDate;
    }

    public TicketPurchased duePaymentDate(Instant duePaymentDate) {
        this.setDuePaymentDate(duePaymentDate);
        return this;
    }

    public void setDuePaymentDate(Instant duePaymentDate) {
        this.duePaymentDate = duePaymentDate;
    }

    public Integer getAmountOfTickets() {
        return this.amountOfTickets;
    }

    public TicketPurchased amountOfTickets(Integer amountOfTickets) {
        this.setAmountOfTickets(amountOfTickets);
        return this;
    }

    public void setAmountOfTickets(Integer amountOfTickets) {
        this.amountOfTickets = amountOfTickets;
    }

    public BigDecimal getTaxValue() {
        return this.taxValue;
    }

    public TicketPurchased taxValue(BigDecimal taxValue) {
        this.setTaxValue(taxValue);
        return this;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public BigDecimal getTicketValue() {
        return this.ticketValue;
    }

    public TicketPurchased ticketValue(BigDecimal ticketValue) {
        this.setTicketValue(ticketValue);
        return this;
    }

    public void setTicketValue(BigDecimal ticketValue) {
        this.ticketValue = ticketValue;
    }

    public String getAcquiredSeatsNumbers() {
        return this.acquiredSeatsNumbers;
    }

    public TicketPurchased acquiredSeatsNumbers(String acquiredSeatsNumbers) {
        this.setAcquiredSeatsNumbers(acquiredSeatsNumbers);
        return this;
    }

    public void setAcquiredSeatsNumbers(String acquiredSeatsNumbers) {
        this.acquiredSeatsNumbers = acquiredSeatsNumbers;
    }

    public String getDescription() {
        return this.description;
    }

    public TicketPurchased description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public TicketPurchased event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public TicketPurchased invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    public Set<EventAttendee> getEventAttendeesLists() {
        return this.eventAttendeesLists;
    }

    public void setEventAttendeesLists(Set<EventAttendee> eventAttendees) {
        if (this.eventAttendeesLists != null) {
            this.eventAttendeesLists.forEach(i -> i.setTicketPurchased(null));
        }
        if (eventAttendees != null) {
            eventAttendees.forEach(i -> i.setTicketPurchased(this));
        }
        this.eventAttendeesLists = eventAttendees;
    }

    public TicketPurchased eventAttendeesLists(Set<EventAttendee> eventAttendees) {
        this.setEventAttendeesLists(eventAttendees);
        return this;
    }

    public TicketPurchased addEventAttendeesList(EventAttendee eventAttendee) {
        this.eventAttendeesLists.add(eventAttendee);
        eventAttendee.setTicketPurchased(this);
        return this;
    }

    public TicketPurchased removeEventAttendeesList(EventAttendee eventAttendee) {
        this.eventAttendeesLists.remove(eventAttendee);
        eventAttendee.setTicketPurchased(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketPurchased)) {
            return false;
        }
        return getId() != null && getId().equals(((TicketPurchased) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketPurchased{" +
            "id=" + getId() +
            ", buyingCode='" + getBuyingCode() + "'" +
            ", duePaymentDate='" + getDuePaymentDate() + "'" +
            ", amountOfTickets=" + getAmountOfTickets() +
            ", taxValue=" + getTaxValue() +
            ", ticketValue=" + getTicketValue() +
            ", acquiredSeatsNumbers='" + getAcquiredSeatsNumbers() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
