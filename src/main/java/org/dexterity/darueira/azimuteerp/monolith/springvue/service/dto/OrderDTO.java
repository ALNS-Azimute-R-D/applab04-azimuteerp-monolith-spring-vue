package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.OrderStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Order} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String businessCode;

    @NotNull
    private Instant placedDate;

    private BigDecimal totalTaxValue;

    private BigDecimal totalDueValue;

    @NotNull
    private OrderStatusEnum status;

    private Instant estimatedDeliveryDate;

    @NotNull
    private ActivationStatusEnum activationStatus;

    private InvoiceDTO invoice;

    @NotNull
    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Instant getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Instant placedDate) {
        this.placedDate = placedDate;
    }

    public BigDecimal getTotalTaxValue() {
        return totalTaxValue;
    }

    public void setTotalTaxValue(BigDecimal totalTaxValue) {
        this.totalTaxValue = totalTaxValue;
    }

    public BigDecimal getTotalDueValue() {
        return totalDueValue;
    }

    public void setTotalDueValue(BigDecimal totalDueValue) {
        this.totalDueValue = totalDueValue;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Instant getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Instant estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", businessCode='" + getBusinessCode() + "'" +
            ", placedDate='" + getPlacedDate() + "'" +
            ", totalTaxValue=" + getTotalTaxValue() +
            ", totalDueValue=" + getTotalDueValue() +
            ", status='" + getStatus() + "'" +
            ", estimatedDeliveryDate='" + getEstimatedDeliveryDate() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", invoice=" + getInvoice() +
            ", customer=" + getCustomer() +
            "}";
    }
}
