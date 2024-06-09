package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfArtistDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String acronym;

    @NotNull
    @Size(min = 2, max = 120)
    private String name;

    @NotNull
    @Size(max = 120)
    private String description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfArtistDTO)) {
            return false;
        }

        TypeOfArtistDTO typeOfArtistDTO = (TypeOfArtistDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeOfArtistDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfArtistDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
