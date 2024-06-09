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
 * A TypeOfArtist.
 */
@Entity
@Table(name = "tb_artmedia_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfArtist implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeOfArtist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfArtmedia", "typeOfArtist", "artistAggregator", "artists", "artworkCastsLists", "linkedArtistsLists" },
        allowSetters = true
    )
    private Set<Artist> artistsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeOfArtist id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public TypeOfArtist acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public TypeOfArtist name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TypeOfArtist description(String description) {
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
            this.artistsLists.forEach(i -> i.setTypeOfArtist(null));
        }
        if (artists != null) {
            artists.forEach(i -> i.setTypeOfArtist(this));
        }
        this.artistsLists = artists;
    }

    public TypeOfArtist artistsLists(Set<Artist> artists) {
        this.setArtistsLists(artists);
        return this;
    }

    public TypeOfArtist addArtistsList(Artist artist) {
        this.artistsLists.add(artist);
        artist.setTypeOfArtist(this);
        return this;
    }

    public TypeOfArtist removeArtistsList(Artist artist) {
        this.artistsLists.remove(artist);
        artist.setTypeOfArtist(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfArtist)) {
            return false;
        }
        return getId() != null && getId().equals(((TypeOfArtist) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfArtist{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
