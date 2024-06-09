package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ArtworkCast.
 */
@Entity
@Table(name = "tb_artwork_cast")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkCast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "order_of_appearance")
    private Integer orderOfAppearance;

    @Size(min = 2, max = 255)
    @Column(name = "character_name", length = 255)
    private String characterName;

    @Size(max = 255)
    @Column(name = "main_asset_uuid", length = 255)
    private String mainAssetUUID;

    @Size(max = 2048)
    @Column(name = "character_details_json", length = 2048)
    private String characterDetailsJSON;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "artworkAggregator", "artworkCastsLists", "linkedArtworksLists" },
        allowSetters = true
    )
    private Artwork artwork;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "typeOfArtist", "artistAggregator", "artists", "artworkCastsLists", "linkedArtistsLists" },
        allowSetters = true
    )
    private Artist artist;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArtworkCast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderOfAppearance() {
        return this.orderOfAppearance;
    }

    public ArtworkCast orderOfAppearance(Integer orderOfAppearance) {
        this.setOrderOfAppearance(orderOfAppearance);
        return this;
    }

    public void setOrderOfAppearance(Integer orderOfAppearance) {
        this.orderOfAppearance = orderOfAppearance;
    }

    public String getCharacterName() {
        return this.characterName;
    }

    public ArtworkCast characterName(String characterName) {
        this.setCharacterName(characterName);
        return this;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getMainAssetUUID() {
        return this.mainAssetUUID;
    }

    public ArtworkCast mainAssetUUID(String mainAssetUUID) {
        this.setMainAssetUUID(mainAssetUUID);
        return this;
    }

    public void setMainAssetUUID(String mainAssetUUID) {
        this.mainAssetUUID = mainAssetUUID;
    }

    public String getCharacterDetailsJSON() {
        return this.characterDetailsJSON;
    }

    public ArtworkCast characterDetailsJSON(String characterDetailsJSON) {
        this.setCharacterDetailsJSON(characterDetailsJSON);
        return this;
    }

    public void setCharacterDetailsJSON(String characterDetailsJSON) {
        this.characterDetailsJSON = characterDetailsJSON;
    }

    public Artwork getArtwork() {
        return this.artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public ArtworkCast artwork(Artwork artwork) {
        this.setArtwork(artwork);
        return this;
    }

    public Artist getArtist() {
        return this.artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public ArtworkCast artist(Artist artist) {
        this.setArtist(artist);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkCast)) {
            return false;
        }
        return getId() != null && getId().equals(((ArtworkCast) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkCast{" +
            "id=" + getId() +
            ", orderOfAppearance=" + getOrderOfAppearance() +
            ", characterName='" + getCharacterName() + "'" +
            ", mainAssetUUID='" + getMainAssetUUID() + "'" +
            ", characterDetailsJSON='" + getCharacterDetailsJSON() + "'" +
            "}";
    }
}
