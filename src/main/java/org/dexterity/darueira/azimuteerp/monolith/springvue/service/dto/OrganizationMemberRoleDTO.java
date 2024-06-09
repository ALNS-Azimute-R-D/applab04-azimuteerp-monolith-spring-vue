package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationMemberRoleDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate joinedAt;

    @NotNull
    private OrganizationMembershipDTO organizationMembership;

    @NotNull
    private OrganizationRoleDTO organizationRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDate joinedAt) {
        this.joinedAt = joinedAt;
    }

    public OrganizationMembershipDTO getOrganizationMembership() {
        return organizationMembership;
    }

    public void setOrganizationMembership(OrganizationMembershipDTO organizationMembership) {
        this.organizationMembership = organizationMembership;
    }

    public OrganizationRoleDTO getOrganizationRole() {
        return organizationRole;
    }

    public void setOrganizationRole(OrganizationRoleDTO organizationRole) {
        this.organizationRole = organizationRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationMemberRoleDTO)) {
            return false;
        }

        OrganizationMemberRoleDTO organizationMemberRoleDTO = (OrganizationMemberRoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationMemberRoleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationMemberRoleDTO{" +
            "id=" + getId() +
            ", joinedAt='" + getJoinedAt() + "'" +
            ", organizationMembership=" + getOrganizationMembership() +
            ", organizationRole=" + getOrganizationRole() +
            "}";
    }
}
