package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    private String artworkTitle;

    private Integer productionYear;

    private Integer seasonNumber;

    private Integer episodeOrSequenceNumber;

    @Size(max = 50)
    private String registerIdInIMDB;

    @Size(max = 255)
    private String assetsCollectionUUID;

    @Size(max = 4096)
    private String contentDetailsJSON;

    @NotNull
    private TypeOfArtmediaDTO typeOfArtmedia;

    private ArtworkDTO artworkAggregator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtworkTitle() {
        return artworkTitle;
    }

    public void setArtworkTitle(String artworkTitle) {
        this.artworkTitle = artworkTitle;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeOrSequenceNumber() {
        return episodeOrSequenceNumber;
    }

    public void setEpisodeOrSequenceNumber(Integer episodeOrSequenceNumber) {
        this.episodeOrSequenceNumber = episodeOrSequenceNumber;
    }

    public String getRegisterIdInIMDB() {
        return registerIdInIMDB;
    }

    public void setRegisterIdInIMDB(String registerIdInIMDB) {
        this.registerIdInIMDB = registerIdInIMDB;
    }

    public String getAssetsCollectionUUID() {
        return assetsCollectionUUID;
    }

    public void setAssetsCollectionUUID(String assetsCollectionUUID) {
        this.assetsCollectionUUID = assetsCollectionUUID;
    }

    public String getContentDetailsJSON() {
        return contentDetailsJSON;
    }

    public void setContentDetailsJSON(String contentDetailsJSON) {
        this.contentDetailsJSON = contentDetailsJSON;
    }

    public TypeOfArtmediaDTO getTypeOfArtmedia() {
        return typeOfArtmedia;
    }

    public void setTypeOfArtmedia(TypeOfArtmediaDTO typeOfArtmedia) {
        this.typeOfArtmedia = typeOfArtmedia;
    }

    public ArtworkDTO getArtworkAggregator() {
        return artworkAggregator;
    }

    public void setArtworkAggregator(ArtworkDTO artworkAggregator) {
        this.artworkAggregator = artworkAggregator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkDTO)) {
            return false;
        }

        ArtworkDTO artworkDTO = (ArtworkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkDTO{" +
            "id=" + getId() +
            ", artworkTitle='" + getArtworkTitle() + "'" +
            ", productionYear=" + getProductionYear() +
            ", seasonNumber=" + getSeasonNumber() +
            ", episodeOrSequenceNumber=" + getEpisodeOrSequenceNumber() +
            ", registerIdInIMDB='" + getRegisterIdInIMDB() + "'" +
            ", assetsCollectionUUID='" + getAssetsCollectionUUID() + "'" +
            ", contentDetailsJSON='" + getContentDetailsJSON() + "'" +
            ", typeOfArtmedia=" + getTypeOfArtmedia() +
            ", artworkAggregator=" + getArtworkAggregator() +
            "}";
    }
}
