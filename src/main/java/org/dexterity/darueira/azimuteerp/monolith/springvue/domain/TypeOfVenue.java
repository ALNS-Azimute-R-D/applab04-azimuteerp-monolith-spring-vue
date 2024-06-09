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
 * A TypeOfVenue.
 */
@Entity
@Table(name = "tb_type_venue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfVenue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "acronym", length = 30)
    private String acronym;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "handler_clazz_name", length = 255)
    private String handlerClazzName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeOfVenue")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "typeOfVenue", "commonLocality", "eventsLists" }, allowSetters = true)
    private Set<Venue> venuesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeOfVenue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public TypeOfVenue acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public TypeOfVenue name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TypeOfVenue description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHandlerClazzName() {
        return this.handlerClazzName;
    }

    public TypeOfVenue handlerClazzName(String handlerClazzName) {
        this.setHandlerClazzName(handlerClazzName);
        return this;
    }

    public void setHandlerClazzName(String handlerClazzName) {
        this.handlerClazzName = handlerClazzName;
    }

    public Set<Venue> getVenuesLists() {
        return this.venuesLists;
    }

    public void setVenuesLists(Set<Venue> venues) {
        if (this.venuesLists != null) {
            this.venuesLists.forEach(i -> i.setTypeOfVenue(null));
        }
        if (venues != null) {
            venues.forEach(i -> i.setTypeOfVenue(this));
        }
        this.venuesLists = venues;
    }

    public TypeOfVenue venuesLists(Set<Venue> venues) {
        this.setVenuesLists(venues);
        return this;
    }

    public TypeOfVenue addVenuesList(Venue venue) {
        this.venuesLists.add(venue);
        venue.setTypeOfVenue(this);
        return this;
    }

    public TypeOfVenue removeVenuesList(Venue venue) {
        this.venuesLists.remove(venue);
        venue.setTypeOfVenue(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfVenue)) {
            return false;
        }
        return getId() != null && getId().equals(((TypeOfVenue) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfVenue{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", handlerClazzName='" + getHandlerClazzName() + "'" +
            "}";
    }
}
