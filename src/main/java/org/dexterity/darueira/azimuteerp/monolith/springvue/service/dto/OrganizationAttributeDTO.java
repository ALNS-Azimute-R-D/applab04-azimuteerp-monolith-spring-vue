package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttribute} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationAttributeDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String attributeName;

    @Size(max = 4096)
    private String attributeValue;

    @NotNull
    private OrganizationDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
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
        if (!(o instanceof OrganizationAttributeDTO)) {
            return false;
        }

        OrganizationAttributeDTO organizationAttributeDTO = (OrganizationAttributeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationAttributeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationAttributeDTO{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            ", organization=" + getOrganization() +
            "}";
    }
}
