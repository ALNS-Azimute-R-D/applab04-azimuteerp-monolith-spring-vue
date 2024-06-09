package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrganizationMemberRole.
 */
@Entity
@Table(name = "tb_organization_member_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationMemberRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "joined_at", nullable = false)
    private LocalDate joinedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "organization", "person", "organizationMemberRolesLists" }, allowSetters = true)
    private OrganizationMembership organizationMembership;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "organization", "organizationMemberRolesLists" }, allowSetters = true)
    private OrganizationRole organizationRole;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationMemberRole id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getJoinedAt() {
        return this.joinedAt;
    }

    public OrganizationMemberRole joinedAt(LocalDate joinedAt) {
        this.setJoinedAt(joinedAt);
        return this;
    }

    public void setJoinedAt(LocalDate joinedAt) {
        this.joinedAt = joinedAt;
    }

    public OrganizationMembership getOrganizationMembership() {
        return this.organizationMembership;
    }

    public void setOrganizationMembership(OrganizationMembership organizationMembership) {
        this.organizationMembership = organizationMembership;
    }

    public OrganizationMemberRole organizationMembership(OrganizationMembership organizationMembership) {
        this.setOrganizationMembership(organizationMembership);
        return this;
    }

    public OrganizationRole getOrganizationRole() {
        return this.organizationRole;
    }

    public void setOrganizationRole(OrganizationRole organizationRole) {
        this.organizationRole = organizationRole;
    }

    public OrganizationMemberRole organizationRole(OrganizationRole organizationRole) {
        this.setOrganizationRole(organizationRole);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationMemberRole)) {
            return false;
        }
        return getId() != null && getId().equals(((OrganizationMemberRole) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationMemberRole{" +
            "id=" + getId() +
            ", joinedAt='" + getJoinedAt() + "'" +
            "}";
    }
}
