package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtistDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String acronym;

    @NotNull
    @Size(min = 2, max = 120)
    private String publicName;

    @Size(min = 2, max = 120)
    private String realName;

    @Size(max = 2048)
    private String biographyDetailsJSON;

    @NotNull
    private TypeOfArtmediaDTO typeOfArtmedia;

    @NotNull
    private TypeOfArtistDTO typeOfArtist;

    private ArtistDTO artistAggregator;

    private Set<ArtisticGenreDTO> artists = new HashSet<>();

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

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBiographyDetailsJSON() {
        return biographyDetailsJSON;
    }

    public void setBiographyDetailsJSON(String biographyDetailsJSON) {
        this.biographyDetailsJSON = biographyDetailsJSON;
    }

    public TypeOfArtmediaDTO getTypeOfArtmedia() {
        return typeOfArtmedia;
    }

    public void setTypeOfArtmedia(TypeOfArtmediaDTO typeOfArtmedia) {
        this.typeOfArtmedia = typeOfArtmedia;
    }

    public TypeOfArtistDTO getTypeOfArtist() {
        return typeOfArtist;
    }

    public void setTypeOfArtist(TypeOfArtistDTO typeOfArtist) {
        this.typeOfArtist = typeOfArtist;
    }

    public ArtistDTO getArtistAggregator() {
        return artistAggregator;
    }

    public void setArtistAggregator(ArtistDTO artistAggregator) {
        this.artistAggregator = artistAggregator;
    }

    public Set<ArtisticGenreDTO> getArtists() {
        return artists;
    }

    public void setArtists(Set<ArtisticGenreDTO> artists) {
        this.artists = artists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtistDTO)) {
            return false;
        }

        ArtistDTO artistDTO = (ArtistDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artistDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", publicName='" + getPublicName() + "'" +
            ", realName='" + getRealName() + "'" +
            ", biographyDetailsJSON='" + getBiographyDetailsJSON() + "'" +
            ", typeOfArtmedia=" + getTypeOfArtmedia() +
            ", typeOfArtist=" + getTypeOfArtist() +
            ", artistAggregator=" + getArtistAggregator() +
            ", artists=" + getArtists() +
            "}";
    }
}
