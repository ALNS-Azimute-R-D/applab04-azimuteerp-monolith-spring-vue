package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfOrganizationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String acronym;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 128)
    private String description;

    @Size(max = 512)
    private String businessHandlerClazz;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessHandlerClazz() {
        return businessHandlerClazz;
    }

    public void setBusinessHandlerClazz(String businessHandlerClazz) {
        this.businessHandlerClazz = businessHandlerClazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfOrganizationDTO)) {
            return false;
        }

        TypeOfOrganizationDTO typeOfOrganizationDTO = (TypeOfOrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeOfOrganizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfOrganizationDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", businessHandlerClazz='" + getBusinessHandlerClazz() + "'" +
            "}";
    }
}
