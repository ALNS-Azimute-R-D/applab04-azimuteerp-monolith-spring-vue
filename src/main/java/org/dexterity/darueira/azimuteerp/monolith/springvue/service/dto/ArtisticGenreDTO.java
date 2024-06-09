package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtisticGenreDTO implements Serializable {

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

    private Set<ArtistDTO> artisticGenres = new HashSet<>();

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

    public Set<ArtistDTO> getArtisticGenres() {
        return artisticGenres;
    }

    public void setArtisticGenres(Set<ArtistDTO> artisticGenres) {
        this.artisticGenres = artisticGenres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtisticGenreDTO)) {
            return false;
        }

        ArtisticGenreDTO artisticGenreDTO = (ArtisticGenreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artisticGenreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtisticGenreDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", artisticGenres=" + getArtisticGenres() +
            "}";
    }
}
