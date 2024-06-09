package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Artwork.
 */
@Entity
@Table(name = "tb_artwork")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Artwork implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "artwork_title", length = 255, nullable = false)
    private String artworkTitle;

    @Column(name = "production_year")
    private Integer productionYear;

    @Column(name = "season_number")
    private Integer seasonNumber;

    @Column(name = "episode_or_sequence_number")
    private Integer episodeOrSequenceNumber;

    @Size(max = 50)
    @Column(name = "register_id_in_imdb", length = 50)
    private String registerIdInIMDB;

    @Size(max = 255)
    @Column(name = "assets_collection_uuid", length = 255)
    private String assetsCollectionUUID;

    @Size(max = 4096)
    @Column(name = "content_details_json", length = 4096)
    private String contentDetailsJSON;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "artistsLists", "artworksLists" }, allowSetters = true)
    private TypeOfArtmedia typeOfArtmedia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "artworkAggregator", "artworkCastsLists", "linkedArtworksLists" },
        allowSetters = true
    )
    private Artwork artworkAggregator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artwork")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "artwork", "artist" }, allowSetters = true)
    private Set<ArtworkCast> artworkCastsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artworkAggregator")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "artworkAggregator", "artworkCastsLists", "linkedArtworksLists" },
        allowSetters = true
    )
    private Set<Artwork> linkedArtworksLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Artwork id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtworkTitle() {
        return this.artworkTitle;
    }

    public Artwork artworkTitle(String artworkTitle) {
        this.setArtworkTitle(artworkTitle);
        return this;
    }

    public void setArtworkTitle(String artworkTitle) {
        this.artworkTitle = artworkTitle;
    }

    public Integer getProductionYear() {
        return this.productionYear;
    }

    public Artwork productionYear(Integer productionYear) {
        this.setProductionYear(productionYear);
        return this;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Integer getSeasonNumber() {
        return this.seasonNumber;
    }

    public Artwork seasonNumber(Integer seasonNumber) {
        this.setSeasonNumber(seasonNumber);
        return this;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeOrSequenceNumber() {
        return this.episodeOrSequenceNumber;
    }

    public Artwork episodeOrSequenceNumber(Integer episodeOrSequenceNumber) {
        this.setEpisodeOrSequenceNumber(episodeOrSequenceNumber);
        return this;
    }

    public void setEpisodeOrSequenceNumber(Integer episodeOrSequenceNumber) {
        this.episodeOrSequenceNumber = episodeOrSequenceNumber;
    }

    public String getRegisterIdInIMDB() {
        return this.registerIdInIMDB;
    }

    public Artwork registerIdInIMDB(String registerIdInIMDB) {
        this.setRegisterIdInIMDB(registerIdInIMDB);
        return this;
    }

    public void setRegisterIdInIMDB(String registerIdInIMDB) {
        this.registerIdInIMDB = registerIdInIMDB;
    }

    public String getAssetsCollectionUUID() {
        return this.assetsCollectionUUID;
    }

    public Artwork assetsCollectionUUID(String assetsCollectionUUID) {
        this.setAssetsCollectionUUID(assetsCollectionUUID);
        return this;
    }

    public void setAssetsCollectionUUID(String assetsCollectionUUID) {
        this.assetsCollectionUUID = assetsCollectionUUID;
    }

    public String getContentDetailsJSON() {
        return this.contentDetailsJSON;
    }

    public Artwork contentDetailsJSON(String contentDetailsJSON) {
        this.setContentDetailsJSON(contentDetailsJSON);
        return this;
    }

    public void setContentDetailsJSON(String contentDetailsJSON) {
        this.contentDetailsJSON = contentDetailsJSON;
    }

    public TypeOfArtmedia getTypeOfArtmedia() {
        return this.typeOfArtmedia;
    }

    public void setTypeOfArtmedia(TypeOfArtmedia typeOfArtmedia) {
        this.typeOfArtmedia = typeOfArtmedia;
    }

    public Artwork typeOfArtmedia(TypeOfArtmedia typeOfArtmedia) {
        this.setTypeOfArtmedia(typeOfArtmedia);
        return this;
    }

    public Artwork getArtworkAggregator() {
        return this.artworkAggregator;
    }

    public void setArtworkAggregator(Artwork artwork) {
        this.artworkAggregator = artwork;
    }

    public Artwork artworkAggregator(Artwork artwork) {
        this.setArtworkAggregator(artwork);
        return this;
    }

    public Set<ArtworkCast> getArtworkCastsLists() {
        return this.artworkCastsLists;
    }

    public void setArtworkCastsLists(Set<ArtworkCast> artworkCasts) {
        if (this.artworkCastsLists != null) {
            this.artworkCastsLists.forEach(i -> i.setArtwork(null));
        }
        if (artworkCasts != null) {
            artworkCasts.forEach(i -> i.setArtwork(this));
        }
        this.artworkCastsLists = artworkCasts;
    }

    public Artwork artworkCastsLists(Set<ArtworkCast> artworkCasts) {
        this.setArtworkCastsLists(artworkCasts);
        return this;
    }

    public Artwork addArtworkCastsList(ArtworkCast artworkCast) {
        this.artworkCastsLists.add(artworkCast);
        artworkCast.setArtwork(this);
        return this;
    }

    public Artwork removeArtworkCastsList(ArtworkCast artworkCast) {
        this.artworkCastsLists.remove(artworkCast);
        artworkCast.setArtwork(null);
        return this;
    }

    public Set<Artwork> getLinkedArtworksLists() {
        return this.linkedArtworksLists;
    }

    public void setLinkedArtworksLists(Set<Artwork> artworks) {
        if (this.linkedArtworksLists != null) {
            this.linkedArtworksLists.forEach(i -> i.setArtworkAggregator(null));
        }
        if (artworks != null) {
            artworks.forEach(i -> i.setArtworkAggregator(this));
        }
        this.linkedArtworksLists = artworks;
    }

    public Artwork linkedArtworksLists(Set<Artwork> artworks) {
        this.setLinkedArtworksLists(artworks);
        return this;
    }

    public Artwork addLinkedArtworksList(Artwork artwork) {
        this.linkedArtworksLists.add(artwork);
        artwork.setArtworkAggregator(this);
        return this;
    }

    public Artwork removeLinkedArtworksList(Artwork artwork) {
        this.linkedArtworksLists.remove(artwork);
        artwork.setArtworkAggregator(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artwork)) {
            return false;
        }
        return getId() != null && getId().equals(((Artwork) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artwork{" +
            "id=" + getId() +
            ", artworkTitle='" + getArtworkTitle() + "'" +
            ", productionYear=" + getProductionYear() +
            ", seasonNumber=" + getSeasonNumber() +
            ", episodeOrSequenceNumber=" + getEpisodeOrSequenceNumber() +
            ", registerIdInIMDB='" + getRegisterIdInIMDB() + "'" +
            ", assetsCollectionUUID='" + getAssetsCollectionUUID() + "'" +
            ", contentDetailsJSON='" + getContentDetailsJSON() + "'" +
            "}";
    }
}
