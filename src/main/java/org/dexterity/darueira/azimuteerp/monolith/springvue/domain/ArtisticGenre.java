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
 * A ArtisticGenre.
 */
@Entity
@Table(name = "tb_artistic_genre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtisticGenre implements Serializable {

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
    @Size(max = 120)
    @Column(name = "description", length = 120, nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "artists")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "typeOfArtist", "artistAggregator", "artists", "artworkCastsLists", "linkedArtistsLists" },
        allowSetters = true
    )
    private Set<Artist> artisticGenres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArtisticGenre id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public ArtisticGenre acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public ArtisticGenre name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ArtisticGenre description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Artist> getArtisticGenres() {
        return this.artisticGenres;
    }

    public void setArtisticGenres(Set<Artist> artists) {
        if (this.artisticGenres != null) {
            this.artisticGenres.forEach(i -> i.removeArtist(this));
        }
        if (artists != null) {
            artists.forEach(i -> i.addArtist(this));
        }
        this.artisticGenres = artists;
    }

    public ArtisticGenre artisticGenres(Set<Artist> artists) {
        this.setArtisticGenres(artists);
        return this;
    }

    public ArtisticGenre addArtisticGenre(Artist artist) {
        this.artisticGenres.add(artist);
        artist.getArtists().add(this);
        return this;
    }

    public ArtisticGenre removeArtisticGenre(Artist artist) {
        this.artisticGenres.remove(artist);
        artist.getArtists().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtisticGenre)) {
            return false;
        }
        return getId() != null && getId().equals(((ArtisticGenre) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtisticGenre{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
