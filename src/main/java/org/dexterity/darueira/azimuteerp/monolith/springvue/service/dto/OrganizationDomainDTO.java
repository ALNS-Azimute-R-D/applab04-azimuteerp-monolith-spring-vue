package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationDomainDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String domainAcronym;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    private Boolean isVerified;

    @Size(max = 512)
    private String businessHandlerClazz;

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

    public String getDomainAcronym() {
        return domainAcronym;
    }

    public void setDomainAcronym(String domainAcronym) {
        this.domainAcronym = domainAcronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getBusinessHandlerClazz() {
        return businessHandlerClazz;
    }

    public void setBusinessHandlerClazz(String businessHandlerClazz) {
        this.businessHandlerClazz = businessHandlerClazz;
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
        if (!(o instanceof OrganizationDomainDTO)) {
            return false;
        }

        OrganizationDomainDTO organizationDomainDTO = (OrganizationDomainDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationDomainDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationDomainDTO{" +
            "id=" + getId() +
            ", domainAcronym='" + getDomainAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", isVerified='" + getIsVerified() + "'" +
            ", businessHandlerClazz='" + getBusinessHandlerClazz() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", organization=" + getOrganization() +
            "}";
    }
}
