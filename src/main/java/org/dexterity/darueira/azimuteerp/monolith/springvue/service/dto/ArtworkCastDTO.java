package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkCastDTO implements Serializable {

    private Long id;

    private Integer orderOfAppearance;

    @Size(min = 2, max = 255)
    private String characterName;

    @Size(max = 255)
    private String mainAssetUUID;

    @Size(max = 2048)
    private String characterDetailsJSON;

    @NotNull
    private ArtworkDTO artwork;

    @NotNull
    private ArtistDTO artist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderOfAppearance() {
        return orderOfAppearance;
    }

    public void setOrderOfAppearance(Integer orderOfAppearance) {
        this.orderOfAppearance = orderOfAppearance;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getMainAssetUUID() {
        return mainAssetUUID;
    }

    public void setMainAssetUUID(String mainAssetUUID) {
        this.mainAssetUUID = mainAssetUUID;
    }

    public String getCharacterDetailsJSON() {
        return characterDetailsJSON;
    }

    public void setCharacterDetailsJSON(String characterDetailsJSON) {
        this.characterDetailsJSON = characterDetailsJSON;
    }

    public ArtworkDTO getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDTO artwork) {
        this.artwork = artwork;
    }

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkCastDTO)) {
            return false;
        }

        ArtworkCastDTO artworkCastDTO = (ArtworkCastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkCastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkCastDTO{" +
            "id=" + getId() +
            ", orderOfAppearance=" + getOrderOfAppearance() +
            ", characterName='" + getCharacterName() + "'" +
            ", mainAssetUUID='" + getMainAssetUUID() + "'" +
            ", characterDetailsJSON='" + getCharacterDetailsJSON() + "'" +
            ", artwork=" + getArtwork() +
            ", artist=" + getArtist() +
            "}";
    }
}
