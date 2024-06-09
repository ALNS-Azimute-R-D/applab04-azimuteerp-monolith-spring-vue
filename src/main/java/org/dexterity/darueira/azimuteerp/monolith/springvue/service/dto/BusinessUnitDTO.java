package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessUnitDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String acronym;

    @Size(max = 30)
    private String hierarchicalLevel;

    @NotNull
    @Size(min = 2, max = 120)
    private String name;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private OrganizationDTO organization;

    private BusinessUnitDTO businessUnitParent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getHierarchicalLevel() {
        return hierarchicalLevel;
    }

    public void setHierarchicalLevel(String hierarchicalLevel) {
        this.hierarchicalLevel = hierarchicalLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BusinessUnitDTO getBusinessUnitParent() {
        return businessUnitParent;
    }

    public void setBusinessUnitParent(BusinessUnitDTO businessUnitParent) {
        this.businessUnitParent = businessUnitParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessUnitDTO)) {
            return false;
        }

        BusinessUnitDTO businessUnitDTO = (BusinessUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, businessUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessUnitDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", hierarchicalLevel='" + getHierarchicalLevel() + "'" +
            ", name='" + getName() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", organization=" + getOrganization() +
            ", businessUnitParent=" + getBusinessUnitParent() +
            "}";
    }
}
