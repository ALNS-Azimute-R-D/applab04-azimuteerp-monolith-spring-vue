package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.InvoiceStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * - Invoice
 * - PaymentMethod
 * - Payment
 */
@Entity
@Table(name = "tb_invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "business_code", length = 15, nullable = false)
    private String businessCode;

    @Column(name = "invoice_date")
    private Instant invoiceDate;

    @Column(name = "due_date")
    private Instant dueDate;

    @NotNull
    @Size(max = 80)
    @Column(name = "description", length = 80, nullable = false)
    private String description;

    @Column(name = "tax_value", precision = 21, scale = 2)
    private BigDecimal taxValue;

    @Column(name = "shipping_value", precision = 21, scale = 2)
    private BigDecimal shippingValue;

    @Column(name = "amount_due_value", precision = 21, scale = 2)
    private BigDecimal amountDueValue;

    @NotNull
    @Column(name = "number_of_installments_original", nullable = false)
    private Integer numberOfInstallmentsOriginal;

    @Column(name = "number_of_installments_paid")
    private Integer numberOfInstallmentsPaid;

    @Column(name = "amount_paid_value", precision = 21, scale = 2)
    private BigDecimal amountPaidValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatusEnum status;

    @Size(max = 4096)
    @Column(name = "custom_attributes_details_json", length = 4096)
    private String customAttributesDetailsJSON;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "invoicesAsPreferrableLists", "paymentsLists" }, allowSetters = true)
    private PaymentGateway preferrablePaymentGateway;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orderItemsLists", "invoice", "customer" }, allowSetters = true)
    private Set<Order> ordersLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "invoice", "eventAttendeesLists" }, allowSetters = true)
    private Set<TicketPurchased> ticketsPurchasedLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Invoice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return this.businessCode;
    }

    public Invoice businessCode(String businessCode) {
        this.setBusinessCode(businessCode);
        return this;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Instant getInvoiceDate() {
        return this.invoiceDate;
    }

    public Invoice invoiceDate(Instant invoiceDate) {
        this.setInvoiceDate(invoiceDate);
        return this;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public Invoice dueDate(Instant dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return this.description;
    }

    public Invoice description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTaxValue() {
        return this.taxValue;
    }

    public Invoice taxValue(BigDecimal taxValue) {
        this.setTaxValue(taxValue);
        return this;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public BigDecimal getShippingValue() {
        return this.shippingValue;
    }

    public Invoice shippingValue(BigDecimal shippingValue) {
        this.setShippingValue(shippingValue);
        return this;
    }

    public void setShippingValue(BigDecimal shippingValue) {
        this.shippingValue = shippingValue;
    }

    public BigDecimal getAmountDueValue() {
        return this.amountDueValue;
    }

    public Invoice amountDueValue(BigDecimal amountDueValue) {
        this.setAmountDueValue(amountDueValue);
        return this;
    }

    public void setAmountDueValue(BigDecimal amountDueValue) {
        this.amountDueValue = amountDueValue;
    }

    public Integer getNumberOfInstallmentsOriginal() {
        return this.numberOfInstallmentsOriginal;
    }

    public Invoice numberOfInstallmentsOriginal(Integer numberOfInstallmentsOriginal) {
        this.setNumberOfInstallmentsOriginal(numberOfInstallmentsOriginal);
        return this;
    }

    public void setNumberOfInstallmentsOriginal(Integer numberOfInstallmentsOriginal) {
        this.numberOfInstallmentsOriginal = numberOfInstallmentsOriginal;
    }

    public Integer getNumberOfInstallmentsPaid() {
        return this.numberOfInstallmentsPaid;
    }

    public Invoice numberOfInstallmentsPaid(Integer numberOfInstallmentsPaid) {
        this.setNumberOfInstallmentsPaid(numberOfInstallmentsPaid);
        return this;
    }

    public void setNumberOfInstallmentsPaid(Integer numberOfInstallmentsPaid) {
        this.numberOfInstallmentsPaid = numberOfInstallmentsPaid;
    }

    public BigDecimal getAmountPaidValue() {
        return this.amountPaidValue;
    }

    public Invoice amountPaidValue(BigDecimal amountPaidValue) {
        this.setAmountPaidValue(amountPaidValue);
        return this;
    }

    public void setAmountPaidValue(BigDecimal amountPaidValue) {
        this.amountPaidValue = amountPaidValue;
    }

    public InvoiceStatusEnum getStatus() {
        return this.status;
    }

    public Invoice status(InvoiceStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(InvoiceStatusEnum status) {
        this.status = status;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public Invoice customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Invoice activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public PaymentGateway getPreferrablePaymentGateway() {
        return this.preferrablePaymentGateway;
    }

    public void setPreferrablePaymentGateway(PaymentGateway paymentGateway) {
        this.preferrablePaymentGateway = paymentGateway;
    }

    public Invoice preferrablePaymentGateway(PaymentGateway paymentGateway) {
        this.setPreferrablePaymentGateway(paymentGateway);
        return this;
    }

    public Set<Order> getOrdersLists() {
        return this.ordersLists;
    }

    public void setOrdersLists(Set<Order> orders) {
        if (this.ordersLists != null) {
            this.ordersLists.forEach(i -> i.setInvoice(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setInvoice(this));
        }
        this.ordersLists = orders;
    }

    public Invoice ordersLists(Set<Order> orders) {
        this.setOrdersLists(orders);
        return this;
    }

    public Invoice addOrdersList(Order order) {
        this.ordersLists.add(order);
        order.setInvoice(this);
        return this;
    }

    public Invoice removeOrdersList(Order order) {
        this.ordersLists.remove(order);
        order.setInvoice(null);
        return this;
    }

    public Set<TicketPurchased> getTicketsPurchasedLists() {
        return this.ticketsPurchasedLists;
    }

    public void setTicketsPurchasedLists(Set<TicketPurchased> ticketPurchaseds) {
        if (this.ticketsPurchasedLists != null) {
            this.ticketsPurchasedLists.forEach(i -> i.setInvoice(null));
        }
        if (ticketPurchaseds != null) {
            ticketPurchaseds.forEach(i -> i.setInvoice(this));
        }
        this.ticketsPurchasedLists = ticketPurchaseds;
    }

    public Invoice ticketsPurchasedLists(Set<TicketPurchased> ticketPurchaseds) {
        this.setTicketsPurchasedLists(ticketPurchaseds);
        return this;
    }

    public Invoice addTicketsPurchasedList(TicketPurchased ticketPurchased) {
        this.ticketsPurchasedLists.add(ticketPurchased);
        ticketPurchased.setInvoice(this);
        return this;
    }

    public Invoice removeTicketsPurchasedList(TicketPurchased ticketPurchased) {
        this.ticketsPurchasedLists.remove(ticketPurchased);
        ticketPurchased.setInvoice(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return getId() != null && getId().equals(((Invoice) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", businessCode='" + getBusinessCode() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", taxValue=" + getTaxValue() +
            ", shippingValue=" + getShippingValue() +
            ", amountDueValue=" + getAmountDueValue() +
            ", numberOfInstallmentsOriginal=" + getNumberOfInstallmentsOriginal() +
            ", numberOfInstallmentsPaid=" + getNumberOfInstallmentsPaid() +
            ", amountPaidValue=" + getAmountPaidValue() +
            ", status='" + getStatus() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
