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
 * - TypeOfArtmedia
 * - TypeOfArtist
 * - ArtisticGenre
 * - Artist
 * - Artwork
 * - ArtworkCast
 */
@Entity
@Table(name = "tb_artmedia_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfArtmedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "acronym", length = 50, nullable = false)
    private String acronym;

    @NotNull
    @Size(min = 2, max = 120)
    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @NotNull
    @Size(max = 512)
    @Column(name = "description", length = 512, nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeOfArtmedia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "typeOfArtist", "artistAggregator", "artists", "artworkCastsLists", "linkedArtistsLists" },
        allowSetters = true
    )
    private Set<Artist> artistsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeOfArtmedia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "artworkAggregator", "artworkCastsLists", "linkedArtworksLists" },
        allowSetters = true
    )
    private Set<Artwork> artworksLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeOfArtmedia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public TypeOfArtmedia acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public TypeOfArtmedia name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TypeOfArtmedia description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Artist> getArtistsLists() {
        return this.artistsLists;
    }

    public void setArtistsLists(Set<Artist> artists) {
        if (this.artistsLists != null) {
            this.artistsLists.forEach(i -> i.setTypeOfArtmedia(null));
        }
        if (artists != null) {
            artists.forEach(i -> i.setTypeOfArtmedia(this));
        }
        this.artistsLists = artists;
    }

    public TypeOfArtmedia artistsLists(Set<Artist> artists) {
        this.setArtistsLists(artists);
        return this;
    }

    public TypeOfArtmedia addArtistsList(Artist artist) {
        this.artistsLists.add(artist);
        artist.setTypeOfArtmedia(this);
        return this;
    }

    public TypeOfArtmedia removeArtistsList(Artist artist) {
        this.artistsLists.remove(artist);
        artist.setTypeOfArtmedia(null);
        return this;
    }

    public Set<Artwork> getArtworksLists() {
        return this.artworksLists;
    }

    public void setArtworksLists(Set<Artwork> artworks) {
        if (this.artworksLists != null) {
            this.artworksLists.forEach(i -> i.setTypeOfArtmedia(null));
        }
        if (artworks != null) {
            artworks.forEach(i -> i.setTypeOfArtmedia(this));
        }
        this.artworksLists = artworks;
    }

    public TypeOfArtmedia artworksLists(Set<Artwork> artworks) {
        this.setArtworksLists(artworks);
        return this;
    }

    public TypeOfArtmedia addArtworksList(Artwork artwork) {
        this.artworksLists.add(artwork);
        artwork.setTypeOfArtmedia(this);
        return this;
    }

    public TypeOfArtmedia removeArtworksList(Artwork artwork) {
        this.artworksLists.remove(artwork);
        artwork.setTypeOfArtmedia(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfArtmedia)) {
            return false;
        }
        return getId() != null && getId().equals(((TypeOfArtmedia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfArtmedia{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
