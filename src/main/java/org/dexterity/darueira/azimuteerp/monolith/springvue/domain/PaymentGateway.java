package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentGateway.
 */
@Entity
@Table(name = "tb_payment_gateway")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentGateway implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "alias_code", length = 20, nullable = false)
    private String aliasCode;

    @NotNull
    @Size(max = 120)
    @Column(name = "description", length = 120, nullable = false)
    private String description;

    @Size(max = 512)
    @Column(name = "business_handler_clazz", length = 512)
    private String businessHandlerClazz;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "preferrablePaymentGateway")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "preferrablePaymentGateway", "ordersLists", "ticketsPurchasedLists" }, allowSetters = true)
    private Set<Invoice> invoicesAsPreferrableLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentGateway")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentGateway" }, allowSetters = true)
    private Set<Payment> paymentsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentGateway id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliasCode() {
        return this.aliasCode;
    }

    public PaymentGateway aliasCode(String aliasCode) {
        this.setAliasCode(aliasCode);
        return this;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    public String getDescription() {
        return this.description;
    }

    public PaymentGateway description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessHandlerClazz() {
        return this.businessHandlerClazz;
    }

    public PaymentGateway businessHandlerClazz(String businessHandlerClazz) {
        this.setBusinessHandlerClazz(businessHandlerClazz);
        return this;
    }

    public void setBusinessHandlerClazz(String businessHandlerClazz) {
        this.businessHandlerClazz = businessHandlerClazz;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public PaymentGateway activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Set<Invoice> getInvoicesAsPreferrableLists() {
        return this.invoicesAsPreferrableLists;
    }

    public void setInvoicesAsPreferrableLists(Set<Invoice> invoices) {
        if (this.invoicesAsPreferrableLists != null) {
            this.invoicesAsPreferrableLists.forEach(i -> i.setPreferrablePaymentGateway(null));
        }
        if (invoices != null) {
            invoices.forEach(i -> i.setPreferrablePaymentGateway(this));
        }
        this.invoicesAsPreferrableLists = invoices;
    }

    public PaymentGateway invoicesAsPreferrableLists(Set<Invoice> invoices) {
        this.setInvoicesAsPreferrableLists(invoices);
        return this;
    }

    public PaymentGateway addInvoicesAsPreferrableList(Invoice invoice) {
        this.invoicesAsPreferrableLists.add(invoice);
        invoice.setPreferrablePaymentGateway(this);
        return this;
    }

    public PaymentGateway removeInvoicesAsPreferrableList(Invoice invoice) {
        this.invoicesAsPreferrableLists.remove(invoice);
        invoice.setPreferrablePaymentGateway(null);
        return this;
    }

    public Set<Payment> getPaymentsLists() {
        return this.paymentsLists;
    }

    public void setPaymentsLists(Set<Payment> payments) {
        if (this.paymentsLists != null) {
            this.paymentsLists.forEach(i -> i.setPaymentGateway(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setPaymentGateway(this));
        }
        this.paymentsLists = payments;
    }

    public PaymentGateway paymentsLists(Set<Payment> payments) {
        this.setPaymentsLists(payments);
        return this;
    }

    public PaymentGateway addPaymentsList(Payment payment) {
        this.paymentsLists.add(payment);
        payment.setPaymentGateway(this);
        return this;
    }

    public PaymentGateway removePaymentsList(Payment payment) {
        this.paymentsLists.remove(payment);
        payment.setPaymentGateway(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentGateway)) {
            return false;
        }
        return getId() != null && getId().equals(((PaymentGateway) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentGateway{" +
            "id=" + getId() +
            ", aliasCode='" + getAliasCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", businessHandlerClazz='" + getBusinessHandlerClazz() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
