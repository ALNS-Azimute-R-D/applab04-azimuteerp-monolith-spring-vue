package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CustomerType.
 */
@Entity
@Table(name = "tb_type_customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "business_handler_clazz", length = 255)
    private String businessHandlerClazz;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customerType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "buyerPerson", "customerType", "district", "ordersLists" }, allowSetters = true)
    private Set<Customer> customersLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomerType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CustomerType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public CustomerType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessHandlerClazz() {
        return this.businessHandlerClazz;
    }

    public CustomerType businessHandlerClazz(String businessHandlerClazz) {
        this.setBusinessHandlerClazz(businessHandlerClazz);
        return this;
    }

    public void setBusinessHandlerClazz(String businessHandlerClazz) {
        this.businessHandlerClazz = businessHandlerClazz;
    }

    public Set<Customer> getCustomersLists() {
        return this.customersLists;
    }

    public void setCustomersLists(Set<Customer> customers) {
        if (this.customersLists != null) {
            this.customersLists.forEach(i -> i.setCustomerType(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setCustomerType(this));
        }
        this.customersLists = customers;
    }

    public CustomerType customersLists(Set<Customer> customers) {
        this.setCustomersLists(customers);
        return this;
    }

    public CustomerType addCustomersList(Customer customer) {
        this.customersLists.add(customer);
        customer.setCustomerType(this);
        return this;
    }

    public CustomerType removeCustomersList(Customer customer) {
        this.customersLists.remove(customer);
        customer.setCustomerType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerType)) {
            return false;
        }
        return getId() != null && getId().equals(((CustomerType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", businessHandlerClazz='" + getBusinessHandlerClazz() + "'" +
            "}";
    }
}
