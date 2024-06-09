package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketPurchasedDTO implements Serializable {

    private Long id;

    @Size(max = 30)
    private String buyingCode;

    private Instant duePaymentDate;

    private Integer amountOfTickets;

    private BigDecimal taxValue;

    private BigDecimal ticketValue;

    @Size(max = 50)
    private String acquiredSeatsNumbers;

    @Size(max = 255)
    private String description;

    private EventDTO event;

    private InvoiceDTO invoice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyingCode() {
        return buyingCode;
    }

    public void setBuyingCode(String buyingCode) {
        this.buyingCode = buyingCode;
    }

    public Instant getDuePaymentDate() {
        return duePaymentDate;
    }

    public void setDuePaymentDate(Instant duePaymentDate) {
        this.duePaymentDate = duePaymentDate;
    }

    public Integer getAmountOfTickets() {
        return amountOfTickets;
    }

    public void setAmountOfTickets(Integer amountOfTickets) {
        this.amountOfTickets = amountOfTickets;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public BigDecimal getTicketValue() {
        return ticketValue;
    }

    public void setTicketValue(BigDecimal ticketValue) {
        this.ticketValue = ticketValue;
    }

    public String getAcquiredSeatsNumbers() {
        return acquiredSeatsNumbers;
    }

    public void setAcquiredSeatsNumbers(String acquiredSeatsNumbers) {
        this.acquiredSeatsNumbers = acquiredSeatsNumbers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketPurchasedDTO)) {
            return false;
        }

        TicketPurchasedDTO ticketPurchasedDTO = (TicketPurchasedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketPurchasedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketPurchasedDTO{" +
            "id=" + getId() +
            ", buyingCode='" + getBuyingCode() + "'" +
            ", duePaymentDate='" + getDuePaymentDate() + "'" +
            ", amountOfTickets=" + getAmountOfTickets() +
            ", taxValue=" + getTaxValue() +
            ", ticketValue=" + getTicketValue() +
            ", acquiredSeatsNumbers='" + getAcquiredSeatsNumbers() + "'" +
            ", description='" + getDescription() + "'" +
            ", event=" + getEvent() +
            ", invoice=" + getInvoice() +
            "}";
    }
}
