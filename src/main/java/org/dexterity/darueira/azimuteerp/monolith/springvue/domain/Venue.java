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
 * A Venue.
 */
@Entity
@Table(name = "tb_venue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Venue implements Serializable {

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

    @Lob
    @Column(name = "geo_point_location")
    private byte[] geoPointLocation;

    @Column(name = "geo_point_location_content_type")
    private String geoPointLocationContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "venuesLists" }, allowSetters = true)
    private TypeOfVenue typeOfVenue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "district", "venuesLists" }, allowSetters = true)
    private CommonLocality commonLocality;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainVenue")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "mainVenue",
            "typeOfEvent",
            "promoteurPerson",
            "assetCollections",
            "eventProgramsLists",
            "ticketsPurchasedLists",
            "eventAttendeesLists",
        },
        allowSetters = true
    )
    private Set<Event> eventsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Venue acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public Venue name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Venue description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getGeoPointLocation() {
        return this.geoPointLocation;
    }

    public Venue geoPointLocation(byte[] geoPointLocation) {
        this.setGeoPointLocation(geoPointLocation);
        return this;
    }

    public void setGeoPointLocation(byte[] geoPointLocation) {
        this.geoPointLocation = geoPointLocation;
    }

    public String getGeoPointLocationContentType() {
        return this.geoPointLocationContentType;
    }

    public Venue geoPointLocationContentType(String geoPointLocationContentType) {
        this.geoPointLocationContentType = geoPointLocationContentType;
        return this;
    }

    public void setGeoPointLocationContentType(String geoPointLocationContentType) {
        this.geoPointLocationContentType = geoPointLocationContentType;
    }

    public TypeOfVenue getTypeOfVenue() {
        return this.typeOfVenue;
    }

    public void setTypeOfVenue(TypeOfVenue typeOfVenue) {
        this.typeOfVenue = typeOfVenue;
    }

    public Venue typeOfVenue(TypeOfVenue typeOfVenue) {
        this.setTypeOfVenue(typeOfVenue);
        return this;
    }

    public CommonLocality getCommonLocality() {
        return this.commonLocality;
    }

    public void setCommonLocality(CommonLocality commonLocality) {
        this.commonLocality = commonLocality;
    }

    public Venue commonLocality(CommonLocality commonLocality) {
        this.setCommonLocality(commonLocality);
        return this;
    }

    public Set<Event> getEventsLists() {
        return this.eventsLists;
    }

    public void setEventsLists(Set<Event> events) {
        if (this.eventsLists != null) {
            this.eventsLists.forEach(i -> i.setMainVenue(null));
        }
        if (events != null) {
            events.forEach(i -> i.setMainVenue(this));
        }
        this.eventsLists = events;
    }

    public Venue eventsLists(Set<Event> events) {
        this.setEventsLists(events);
        return this;
    }

    public Venue addEventsList(Event event) {
        this.eventsLists.add(event);
        event.setMainVenue(this);
        return this;
    }

    public Venue removeEventsList(Event event) {
        this.eventsLists.remove(event);
        event.setMainVenue(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venue)) {
            return false;
        }
        return getId() != null && getId().equals(((Venue) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venue{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", geoPointLocation='" + getGeoPointLocation() + "'" +
            ", geoPointLocationContentType='" + getGeoPointLocationContentType() + "'" +
            "}";
    }
}
