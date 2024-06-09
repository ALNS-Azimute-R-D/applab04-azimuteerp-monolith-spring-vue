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
 * A BusinessUnit.
 */
@Entity
@Table(name = "tb_business_unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "acronym", length = 20, nullable = false)
    private String acronym;

    @Size(max = 30)
    @Column(name = "hierarchical_level", length = 30)
    private String hierarchicalLevel;

    @NotNull
    @Size(min = 2, max = 120)
    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "tenant",
            "typeOfOrganization",
            "organizationParent",
            "organizationDomainsLists",
            "organizationAttributesLists",
            "businessUnitsLists",
            "childrenOrganizationsLists",
            "organizationRolesLists",
            "organizationMembershipsLists",
        },
        allowSetters = true
    )
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "organization", "businessUnitParent", "childrenBusinessUnitsLists" }, allowSetters = true)
    private BusinessUnit businessUnitParent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessUnitParent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization", "businessUnitParent", "childrenBusinessUnitsLists" }, allowSetters = true)
    private Set<BusinessUnit> childrenBusinessUnitsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BusinessUnit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public BusinessUnit acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getHierarchicalLevel() {
        return this.hierarchicalLevel;
    }

    public BusinessUnit hierarchicalLevel(String hierarchicalLevel) {
        this.setHierarchicalLevel(hierarchicalLevel);
        return this;
    }

    public void setHierarchicalLevel(String hierarchicalLevel) {
        this.hierarchicalLevel = hierarchicalLevel;
    }

    public String getName() {
        return this.name;
    }

    public BusinessUnit name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public BusinessUnit activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public BusinessUnit organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public BusinessUnit getBusinessUnitParent() {
        return this.businessUnitParent;
    }

    public void setBusinessUnitParent(BusinessUnit businessUnit) {
        this.businessUnitParent = businessUnit;
    }

    public BusinessUnit businessUnitParent(BusinessUnit businessUnit) {
        this.setBusinessUnitParent(businessUnit);
        return this;
    }

    public Set<BusinessUnit> getChildrenBusinessUnitsLists() {
        return this.childrenBusinessUnitsLists;
    }

    public void setChildrenBusinessUnitsLists(Set<BusinessUnit> businessUnits) {
        if (this.childrenBusinessUnitsLists != null) {
            this.childrenBusinessUnitsLists.forEach(i -> i.setBusinessUnitParent(null));
        }
        if (businessUnits != null) {
            businessUnits.forEach(i -> i.setBusinessUnitParent(this));
        }
        this.childrenBusinessUnitsLists = businessUnits;
    }

    public BusinessUnit childrenBusinessUnitsLists(Set<BusinessUnit> businessUnits) {
        this.setChildrenBusinessUnitsLists(businessUnits);
        return this;
    }

    public BusinessUnit addChildrenBusinessUnitsList(BusinessUnit businessUnit) {
        this.childrenBusinessUnitsLists.add(businessUnit);
        businessUnit.setBusinessUnitParent(this);
        return this;
    }

    public BusinessUnit removeChildrenBusinessUnitsList(BusinessUnit businessUnit) {
        this.childrenBusinessUnitsLists.remove(businessUnit);
        businessUnit.setBusinessUnitParent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessUnit)) {
            return false;
        }
        return getId() != null && getId().equals(((BusinessUnit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessUnit{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", hierarchicalLevel='" + getHierarchicalLevel() + "'" +
            ", name='" + getName() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
