package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.CustomerStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * - Category
 * - Article
 * - Order
 * - OrderItem
 */
@Entity
@Table(name = "tb_customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "customer_business_code", length = 15, nullable = false)
    private String customerBusinessCode;

    @NotNull
    @Size(min = 2, max = 80)
    @Column(name = "fullname", length = 80, nullable = false)
    private String fullname;

    @Size(max = 2048)
    @Column(name = "custom_attributes_details_json", length = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_status", nullable = false)
    private CustomerStatusEnum customerStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(optional = false)
    @NotNull
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
    private Person buyerPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "customersLists" }, allowSetters = true)
    private CustomerType customerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "townCity", "commonLocalitiesLists", "personsLists", "customersLists" }, allowSetters = true)
    private District district;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orderItemsLists", "invoice", "customer" }, allowSetters = true)
    private Set<Order> ordersLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerBusinessCode() {
        return this.customerBusinessCode;
    }

    public Customer customerBusinessCode(String customerBusinessCode) {
        this.setCustomerBusinessCode(customerBusinessCode);
        return this;
    }

    public void setCustomerBusinessCode(String customerBusinessCode) {
        this.customerBusinessCode = customerBusinessCode;
    }

    public String getFullname() {
        return this.fullname;
    }

    public Customer fullname(String fullname) {
        this.setFullname(fullname);
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public Customer customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public CustomerStatusEnum getCustomerStatus() {
        return this.customerStatus;
    }

    public Customer customerStatus(CustomerStatusEnum customerStatus) {
        this.setCustomerStatus(customerStatus);
        return this;
    }

    public void setCustomerStatus(CustomerStatusEnum customerStatus) {
        this.customerStatus = customerStatus;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Customer activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Person getBuyerPerson() {
        return this.buyerPerson;
    }

    public void setBuyerPerson(Person person) {
        this.buyerPerson = person;
    }

    public Customer buyerPerson(Person person) {
        this.setBuyerPerson(person);
        return this;
    }

    public CustomerType getCustomerType() {
        return this.customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Customer customerType(CustomerType customerType) {
        this.setCustomerType(customerType);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Customer district(District district) {
        this.setDistrict(district);
        return this;
    }

    public Set<Order> getOrdersLists() {
        return this.ordersLists;
    }

    public void setOrdersLists(Set<Order> orders) {
        if (this.ordersLists != null) {
            this.ordersLists.forEach(i -> i.setCustomer(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setCustomer(this));
        }
        this.ordersLists = orders;
    }

    public Customer ordersLists(Set<Order> orders) {
        this.setOrdersLists(orders);
        return this;
    }

    public Customer addOrdersList(Order order) {
        this.ordersLists.add(order);
        order.setCustomer(this);
        return this;
    }

    public Customer removeOrdersList(Order order) {
        this.ordersLists.remove(order);
        order.setCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return getId() != null && getId().equals(((Customer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerBusinessCode='" + getCustomerBusinessCode() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", customerStatus='" + getCustomerStatus() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
