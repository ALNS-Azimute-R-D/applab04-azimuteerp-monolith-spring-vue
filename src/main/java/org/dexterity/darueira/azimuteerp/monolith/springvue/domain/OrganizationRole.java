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
 * A OrganizationRole.
 */
@Entity
@Table(name = "tb_organization_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "role_name", length = 255, nullable = false)
    private String roleName;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizationRole")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organizationMembership", "organizationRole" }, allowSetters = true)
    private Set<OrganizationMemberRole> organizationMemberRolesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationRole id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public OrganizationRole roleName(String roleName) {
        this.setRoleName(roleName);
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public OrganizationRole activationStatus(ActivationStatusEnum activationStatus) {
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

    public OrganizationRole organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public Set<OrganizationMemberRole> getOrganizationMemberRolesLists() {
        return this.organizationMemberRolesLists;
    }

    public void setOrganizationMemberRolesLists(Set<OrganizationMemberRole> organizationMemberRoles) {
        if (this.organizationMemberRolesLists != null) {
            this.organizationMemberRolesLists.forEach(i -> i.setOrganizationRole(null));
        }
        if (organizationMemberRoles != null) {
            organizationMemberRoles.forEach(i -> i.setOrganizationRole(this));
        }
        this.organizationMemberRolesLists = organizationMemberRoles;
    }

    public OrganizationRole organizationMemberRolesLists(Set<OrganizationMemberRole> organizationMemberRoles) {
        this.setOrganizationMemberRolesLists(organizationMemberRoles);
        return this;
    }

    public OrganizationRole addOrganizationMemberRolesList(OrganizationMemberRole organizationMemberRole) {
        this.organizationMemberRolesLists.add(organizationMemberRole);
        organizationMemberRole.setOrganizationRole(this);
        return this;
    }

    public OrganizationRole removeOrganizationMemberRolesList(OrganizationMemberRole organizationMemberRole) {
        this.organizationMemberRolesLists.remove(organizationMemberRole);
        organizationMemberRole.setOrganizationRole(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationRole)) {
            return false;
        }
        return getId() != null && getId().equals(((OrganizationRole) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationRole{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
