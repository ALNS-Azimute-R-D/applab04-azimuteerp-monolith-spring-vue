package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.PaymentStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.PaymentTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "tb_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;

    @NotNull
    @Column(name = "payment_due_date", nullable = false)
    private Instant paymentDueDate;

    @NotNull
    @Column(name = "payment_paid_date", nullable = false)
    private Instant paymentPaidDate;

    @NotNull
    @Column(name = "payment_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal paymentAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_payment", nullable = false)
    private PaymentTypeEnum typeOfPayment;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_payment", nullable = false)
    private PaymentStatusEnum statusPayment;

    @Size(max = 2048)
    @Column(name = "custom_attributes_details_json", length = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "invoicesAsPreferrableLists", "paymentsLists" }, allowSetters = true)
    private PaymentGateway paymentGateway;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInstallmentNumber() {
        return this.installmentNumber;
    }

    public Payment installmentNumber(Integer installmentNumber) {
        this.setInstallmentNumber(installmentNumber);
        return this;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Instant getPaymentDueDate() {
        return this.paymentDueDate;
    }

    public Payment paymentDueDate(Instant paymentDueDate) {
        this.setPaymentDueDate(paymentDueDate);
        return this;
    }

    public void setPaymentDueDate(Instant paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Instant getPaymentPaidDate() {
        return this.paymentPaidDate;
    }

    public Payment paymentPaidDate(Instant paymentPaidDate) {
        this.setPaymentPaidDate(paymentPaidDate);
        return this;
    }

    public void setPaymentPaidDate(Instant paymentPaidDate) {
        this.paymentPaidDate = paymentPaidDate;
    }

    public BigDecimal getPaymentAmount() {
        return this.paymentAmount;
    }

    public Payment paymentAmount(BigDecimal paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentTypeEnum getTypeOfPayment() {
        return this.typeOfPayment;
    }

    public Payment typeOfPayment(PaymentTypeEnum typeOfPayment) {
        this.setTypeOfPayment(typeOfPayment);
        return this;
    }

    public void setTypeOfPayment(PaymentTypeEnum typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public PaymentStatusEnum getStatusPayment() {
        return this.statusPayment;
    }

    public Payment statusPayment(PaymentStatusEnum statusPayment) {
        this.setStatusPayment(statusPayment);
        return this;
    }

    public void setStatusPayment(PaymentStatusEnum statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public Payment customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Payment activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public PaymentGateway getPaymentGateway() {
        return this.paymentGateway;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public Payment paymentGateway(PaymentGateway paymentGateway) {
        this.setPaymentGateway(paymentGateway);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return getId() != null && getId().equals(((Payment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", installmentNumber=" + getInstallmentNumber() +
            ", paymentDueDate='" + getPaymentDueDate() + "'" +
            ", paymentPaidDate='" + getPaymentPaidDate() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", typeOfPayment='" + getTypeOfPayment() + "'" +
            ", statusPayment='" + getStatusPayment() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
