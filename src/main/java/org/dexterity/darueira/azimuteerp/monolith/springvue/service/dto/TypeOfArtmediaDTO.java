package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia} entity.
 */
@Schema(description = "- TypeOfArtmedia\n- TypeOfArtist\n- ArtisticGenre\n- Artist\n- Artwork\n- ArtworkCast")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfArtmediaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String acronym;

    @NotNull
    @Size(min = 2, max = 120)
    private String name;

    @NotNull
    @Size(max = 512)
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
        if (!(o instanceof TypeOfArtmediaDTO)) {
            return false;
        }

        TypeOfArtmediaDTO typeOfArtmediaDTO = (TypeOfArtmediaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeOfArtmediaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfArtmediaDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
