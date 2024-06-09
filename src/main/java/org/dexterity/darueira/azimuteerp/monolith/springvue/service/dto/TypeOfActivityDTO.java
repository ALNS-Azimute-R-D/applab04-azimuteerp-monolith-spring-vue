package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfActivityDTO implements Serializable {

    private Long id;

    @Size(max = 30)
    private String acronym;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String handlerClazzName;

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

    public String getHandlerClazzName() {
        return handlerClazzName;
    }

    public void setHandlerClazzName(String handlerClazzName) {
        this.handlerClazzName = handlerClazzName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfActivityDTO)) {
            return false;
        }

        TypeOfActivityDTO typeOfActivityDTO = (TypeOfActivityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeOfActivityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfActivityDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", handlerClazzName='" + getHandlerClazzName() + "'" +
            "}";
    }
}
