package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.PaymentStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.PaymentTypeEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Payment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer installmentNumber;

    @NotNull
    private Instant paymentDueDate;

    @NotNull
    private Instant paymentPaidDate;

    @NotNull
    private BigDecimal paymentAmount;

    @NotNull
    private PaymentTypeEnum typeOfPayment;

    @NotNull
    private PaymentStatusEnum statusPayment;

    @Size(max = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private PaymentGatewayDTO paymentGateway;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Instant getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(Instant paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Instant getPaymentPaidDate() {
        return paymentPaidDate;
    }

    public void setPaymentPaidDate(Instant paymentPaidDate) {
        this.paymentPaidDate = paymentPaidDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentTypeEnum getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(PaymentTypeEnum typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public PaymentStatusEnum getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(PaymentStatusEnum statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getCustomAttributesDetailsJSON() {
        return customAttributesDetailsJSON;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public PaymentGatewayDTO getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(PaymentGatewayDTO paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        PaymentDTO paymentDTO = (PaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", installmentNumber=" + getInstallmentNumber() +
            ", paymentDueDate='" + getPaymentDueDate() + "'" +
            ", paymentPaidDate='" + getPaymentPaidDate() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", typeOfPayment='" + getTypeOfPayment() + "'" +
            ", statusPayment='" + getStatusPayment() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", paymentGateway=" + getPaymentGateway() +
            "}";
    }
}
