package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationMembershipDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate joinedAt;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private OrganizationDTO organization;

    @NotNull
    private PersonDTO person;

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

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationMembershipDTO)) {
            return false;
        }

        OrganizationMembershipDTO organizationMembershipDTO = (OrganizationMembershipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationMembershipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationMembershipDTO{" +
            "id=" + getId() +
            ", joinedAt='" + getJoinedAt() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", organization=" + getOrganization() +
            ", person=" + getPerson() +
            "}";
    }
}
