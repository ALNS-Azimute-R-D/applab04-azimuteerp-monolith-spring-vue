package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationRoleDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String roleName;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private OrganizationDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationRoleDTO)) {
            return false;
        }

        OrganizationRoleDTO organizationRoleDTO = (OrganizationRoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationRoleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationRoleDTO{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", organization=" + getOrganization() +
            "}";
    }
}
