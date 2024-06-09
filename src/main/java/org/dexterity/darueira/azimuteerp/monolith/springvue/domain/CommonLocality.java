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
 * A CommonLocality.
 */
@Entity
@Table(name = "tb_common_locality")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommonLocality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "acronym", length = 20, nullable = false)
    private String acronym;

    @NotNull
    @Size(max = 840)
    @Column(name = "name", length = 840, nullable = false)
    private String name;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

    @NotNull
    @Size(max = 120)
    @Column(name = "street_address", length = 120, nullable = false)
    private String streetAddress;

    @Size(max = 20)
    @Column(name = "house_number", length = 20)
    private String houseNumber;

    @Size(max = 50)
    @Column(name = "location_name", length = 50)
    private String locationName;

    @NotNull
    @Size(max = 9)
    @Column(name = "postal_code", length = 9, nullable = false)
    private String postalCode;

    @Lob
    @Column(name = "geo_polygon_area")
    private byte[] geoPolygonArea;

    @Column(name = "geo_polygon_area_content_type")
    private String geoPolygonAreaContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "townCity", "commonLocalitiesLists", "personsLists", "customersLists" }, allowSetters = true)
    private District district;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commonLocality")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "typeOfVenue", "commonLocality", "eventsLists" }, allowSetters = true)
    private Set<Venue> venuesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CommonLocality id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public CommonLocality acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public CommonLocality name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public CommonLocality description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public CommonLocality streetAddress(String streetAddress) {
        this.setStreetAddress(streetAddress);
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public CommonLocality houseNumber(String houseNumber) {
        this.setHouseNumber(houseNumber);
        return this;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public CommonLocality locationName(String locationName) {
        this.setLocationName(locationName);
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public CommonLocality postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public byte[] getGeoPolygonArea() {
        return this.geoPolygonArea;
    }

    public CommonLocality geoPolygonArea(byte[] geoPolygonArea) {
        this.setGeoPolygonArea(geoPolygonArea);
        return this;
    }

    public void setGeoPolygonArea(byte[] geoPolygonArea) {
        this.geoPolygonArea = geoPolygonArea;
    }

    public String getGeoPolygonAreaContentType() {
        return this.geoPolygonAreaContentType;
    }

    public CommonLocality geoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
        return this;
    }

    public void setGeoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public CommonLocality district(District district) {
        this.setDistrict(district);
        return this;
    }

    public Set<Venue> getVenuesLists() {
        return this.venuesLists;
    }

    public void setVenuesLists(Set<Venue> venues) {
        if (this.venuesLists != null) {
            this.venuesLists.forEach(i -> i.setCommonLocality(null));
        }
        if (venues != null) {
            venues.forEach(i -> i.setCommonLocality(this));
        }
        this.venuesLists = venues;
    }

    public CommonLocality venuesLists(Set<Venue> venues) {
        this.setVenuesLists(venues);
        return this;
    }

    public CommonLocality addVenuesList(Venue venue) {
        this.venuesLists.add(venue);
        venue.setCommonLocality(this);
        return this;
    }

    public CommonLocality removeVenuesList(Venue venue) {
        this.venuesLists.remove(venue);
        venue.setCommonLocality(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonLocality)) {
            return false;
        }
        return getId() != null && getId().equals(((CommonLocality) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommonLocality{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", geoPolygonArea='" + getGeoPolygonArea() + "'" +
            ", geoPolygonAreaContentType='" + getGeoPolygonAreaContentType() + "'" +
            "}";
    }
}
