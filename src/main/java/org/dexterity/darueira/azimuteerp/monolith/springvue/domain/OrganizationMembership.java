package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrganizationMembership.
 */
@Entity
@Table(name = "tb_organization_membership")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationMembership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "joined_at", nullable = false)
    private LocalDate joinedAt;

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
    private Person person;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizationMembership")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organizationMembership", "organizationRole" }, allowSetters = true)
    private Set<OrganizationMemberRole> organizationMemberRolesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationMembership id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getJoinedAt() {
        return this.joinedAt;
    }

    public OrganizationMembership joinedAt(LocalDate joinedAt) {
        this.setJoinedAt(joinedAt);
        return this;
    }

    public void setJoinedAt(LocalDate joinedAt) {
        this.joinedAt = joinedAt;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public OrganizationMembership activationStatus(ActivationStatusEnum activationStatus) {
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

    public OrganizationMembership organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public OrganizationMembership person(Person person) {
        this.setPerson(person);
        return this;
    }

    public Set<OrganizationMemberRole> getOrganizationMemberRolesLists() {
        return this.organizationMemberRolesLists;
    }

    public void setOrganizationMemberRolesLists(Set<OrganizationMemberRole> organizationMemberRoles) {
        if (this.organizationMemberRolesLists != null) {
            this.organizationMemberRolesLists.forEach(i -> i.setOrganizationMembership(null));
        }
        if (organizationMemberRoles != null) {
            organizationMemberRoles.forEach(i -> i.setOrganizationMembership(this));
        }
        this.organizationMemberRolesLists = organizationMemberRoles;
    }

    public OrganizationMembership organizationMemberRolesLists(Set<OrganizationMemberRole> organizationMemberRoles) {
        this.setOrganizationMemberRolesLists(organizationMemberRoles);
        return this;
    }

    public OrganizationMembership addOrganizationMemberRolesList(OrganizationMemberRole organizationMemberRole) {
        this.organizationMemberRolesLists.add(organizationMemberRole);
        organizationMemberRole.setOrganizationMembership(this);
        return this;
    }

    public OrganizationMembership removeOrganizationMemberRolesList(OrganizationMemberRole organizationMemberRole) {
        this.organizationMemberRolesLists.remove(organizationMemberRole);
        organizationMemberRole.setOrganizationMembership(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationMembership)) {
            return false;
        }
        return getId() != null && getId().equals(((OrganizationMembership) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationMembership{" +
            "id=" + getId() +
            ", joinedAt='" + getJoinedAt() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
