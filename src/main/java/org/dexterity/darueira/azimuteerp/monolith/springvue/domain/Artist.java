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
 * A Artist.
 */
@Entity
@Table(name = "tb_artist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Artist implements Serializable {

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
    @Column(name = "public_name", length = 120, nullable = false)
    private String publicName;

    @Size(min = 2, max = 120)
    @Column(name = "real_name", length = 120)
    private String realName;

    @Size(max = 2048)
    @Column(name = "biography_details_json", length = 2048)
    private String biographyDetailsJSON;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "artistsLists", "artworksLists" }, allowSetters = true)
    private TypeOfArtmedia typeOfArtmedia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "artistsLists" }, allowSetters = true)
    private TypeOfArtist typeOfArtist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "typeOfArtist", "artistAggregator", "artists", "artworkCastsLists", "linkedArtistsLists" },
        allowSetters = true
    )
    private Artist artistAggregator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_artist__artist",
        joinColumns = @JoinColumn(name = "tb_artist_id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "artisticGenres" }, allowSetters = true)
    private Set<ArtisticGenre> artists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "artwork", "artist" }, allowSetters = true)
    private Set<ArtworkCast> artworkCastsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artistAggregator")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "typeOfArtist", "artistAggregator", "artists", "artworkCastsLists", "linkedArtistsLists" },
        allowSetters = true
    )
    private Set<Artist> linkedArtistsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Artist id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Artist acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getPublicName() {
        return this.publicName;
    }

    public Artist publicName(String publicName) {
        this.setPublicName(publicName);
        return this;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getRealName() {
        return this.realName;
    }

    public Artist realName(String realName) {
        this.setRealName(realName);
        return this;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBiographyDetailsJSON() {
        return this.biographyDetailsJSON;
    }

    public Artist biographyDetailsJSON(String biographyDetailsJSON) {
        this.setBiographyDetailsJSON(biographyDetailsJSON);
        return this;
    }

    public void setBiographyDetailsJSON(String biographyDetailsJSON) {
        this.biographyDetailsJSON = biographyDetailsJSON;
    }

    public TypeOfArtmedia getTypeOfArtmedia() {
        return this.typeOfArtmedia;
    }

    public void setTypeOfArtmedia(TypeOfArtmedia typeOfArtmedia) {
        this.typeOfArtmedia = typeOfArtmedia;
    }

    public Artist typeOfArtmedia(TypeOfArtmedia typeOfArtmedia) {
        this.setTypeOfArtmedia(typeOfArtmedia);
        return this;
    }

    public TypeOfArtist getTypeOfArtist() {
        return this.typeOfArtist;
    }

    public void setTypeOfArtist(TypeOfArtist typeOfArtist) {
        this.typeOfArtist = typeOfArtist;
    }

    public Artist typeOfArtist(TypeOfArtist typeOfArtist) {
        this.setTypeOfArtist(typeOfArtist);
        return this;
    }

    public Artist getArtistAggregator() {
        return this.artistAggregator;
    }

    public void setArtistAggregator(Artist artist) {
        this.artistAggregator = artist;
    }

    public Artist artistAggregator(Artist artist) {
        this.setArtistAggregator(artist);
        return this;
    }

    public Set<ArtisticGenre> getArtists() {
        return this.artists;
    }

    public void setArtists(Set<ArtisticGenre> artisticGenres) {
        this.artists = artisticGenres;
    }

    public Artist artists(Set<ArtisticGenre> artisticGenres) {
        this.setArtists(artisticGenres);
        return this;
    }

    public Artist addArtist(ArtisticGenre artisticGenre) {
        this.artists.add(artisticGenre);
        return this;
    }

    public Artist removeArtist(ArtisticGenre artisticGenre) {
        this.artists.remove(artisticGenre);
        return this;
    }

    public Set<ArtworkCast> getArtworkCastsLists() {
        return this.artworkCastsLists;
    }

    public void setArtworkCastsLists(Set<ArtworkCast> artworkCasts) {
        if (this.artworkCastsLists != null) {
            this.artworkCastsLists.forEach(i -> i.setArtist(null));
        }
        if (artworkCasts != null) {
            artworkCasts.forEach(i -> i.setArtist(this));
        }
        this.artworkCastsLists = artworkCasts;
    }

    public Artist artworkCastsLists(Set<ArtworkCast> artworkCasts) {
        this.setArtworkCastsLists(artworkCasts);
        return this;
    }

    public Artist addArtworkCastsList(ArtworkCast artworkCast) {
        this.artworkCastsLists.add(artworkCast);
        artworkCast.setArtist(this);
        return this;
    }

    public Artist removeArtworkCastsList(ArtworkCast artworkCast) {
        this.artworkCastsLists.remove(artworkCast);
        artworkCast.setArtist(null);
        return this;
    }

    public Set<Artist> getLinkedArtistsLists() {
        return this.linkedArtistsLists;
    }

    public void setLinkedArtistsLists(Set<Artist> artists) {
        if (this.linkedArtistsLists != null) {
            this.linkedArtistsLists.forEach(i -> i.setArtistAggregator(null));
        }
        if (artists != null) {
            artists.forEach(i -> i.setArtistAggregator(this));
        }
        this.linkedArtistsLists = artists;
    }

    public Artist linkedArtistsLists(Set<Artist> artists) {
        this.setLinkedArtistsLists(artists);
        return this;
    }

    public Artist addLinkedArtistsList(Artist artist) {
        this.linkedArtistsLists.add(artist);
        artist.setArtistAggregator(this);
        return this;
    }

    public Artist removeLinkedArtistsList(Artist artist) {
        this.linkedArtistsLists.remove(artist);
        artist.setArtistAggregator(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artist)) {
            return false;
        }
        return getId() != null && getId().equals(((Artist) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artist{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", publicName='" + getPublicName() + "'" +
            ", realName='" + getRealName() + "'" +
            ", biographyDetailsJSON='" + getBiographyDetailsJSON() + "'" +
            "}";
    }
}
