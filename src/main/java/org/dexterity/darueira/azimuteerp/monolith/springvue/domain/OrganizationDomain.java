package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrganizationDomain.
 */
@Entity
@Table(name = "tb_organization_domain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "domain_acronym", length = 255, nullable = false)
    private String domainAcronym;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @Size(max = 512)
    @Column(name = "business_handler_clazz", length = 512)
    private String businessHandlerClazz;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationDomain id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainAcronym() {
        return this.domainAcronym;
    }

    public OrganizationDomain domainAcronym(String domainAcronym) {
        this.setDomainAcronym(domainAcronym);
        return this;
    }

    public void setDomainAcronym(String domainAcronym) {
        this.domainAcronym = domainAcronym;
    }

    public String getName() {
        return this.name;
    }

    public OrganizationDomain name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public OrganizationDomain isVerified(Boolean isVerified) {
        this.setIsVerified(isVerified);
        return this;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getBusinessHandlerClazz() {
        return this.businessHandlerClazz;
    }

    public OrganizationDomain businessHandlerClazz(String businessHandlerClazz) {
        this.setBusinessHandlerClazz(businessHandlerClazz);
        return this;
    }

    public void setBusinessHandlerClazz(String businessHandlerClazz) {
        this.businessHandlerClazz = businessHandlerClazz;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public OrganizationDomain activationStatus(ActivationStatusEnum activationStatus) {
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

    public OrganizationDomain organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationDomain)) {
            return false;
        }
        return getId() != null && getId().equals(((OrganizationDomain) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationDomain{" +
            "id=" + getId() +
            ", domainAcronym='" + getDomainAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", isVerified='" + getIsVerified() + "'" +
            ", businessHandlerClazz='" + getBusinessHandlerClazz() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
