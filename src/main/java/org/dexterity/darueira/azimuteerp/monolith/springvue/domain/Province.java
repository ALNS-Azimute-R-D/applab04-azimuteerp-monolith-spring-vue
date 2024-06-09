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
 * A Province.
 */
@Entity
@Table(name = "tb_province")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "acronym", length = 3, nullable = false)
    private String acronym;

    @NotNull
    @Size(max = 40)
    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Lob
    @Column(name = "geo_polygon_area")
    private byte[] geoPolygonArea;

    @Column(name = "geo_polygon_area_content_type")
    private String geoPolygonAreaContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "provincesLists" }, allowSetters = true)
    private Country country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "province", "districtsLists" }, allowSetters = true)
    private Set<TownCity> townCitiesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Province id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Province acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public Province name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getGeoPolygonArea() {
        return this.geoPolygonArea;
    }

    public Province geoPolygonArea(byte[] geoPolygonArea) {
        this.setGeoPolygonArea(geoPolygonArea);
        return this;
    }

    public void setGeoPolygonArea(byte[] geoPolygonArea) {
        this.geoPolygonArea = geoPolygonArea;
    }

    public String getGeoPolygonAreaContentType() {
        return this.geoPolygonAreaContentType;
    }

    public Province geoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
        return this;
    }

    public void setGeoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Province country(Country country) {
        this.setCountry(country);
        return this;
    }

    public Set<TownCity> getTownCitiesLists() {
        return this.townCitiesLists;
    }

    public void setTownCitiesLists(Set<TownCity> townCities) {
        if (this.townCitiesLists != null) {
            this.townCitiesLists.forEach(i -> i.setProvince(null));
        }
        if (townCities != null) {
            townCities.forEach(i -> i.setProvince(this));
        }
        this.townCitiesLists = townCities;
    }

    public Province townCitiesLists(Set<TownCity> townCities) {
        this.setTownCitiesLists(townCities);
        return this;
    }

    public Province addTownCitiesList(TownCity townCity) {
        this.townCitiesLists.add(townCity);
        townCity.setProvince(this);
        return this;
    }

    public Province removeTownCitiesList(TownCity townCity) {
        this.townCitiesLists.remove(townCity);
        townCity.setProvince(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Province)) {
            return false;
        }
        return getId() != null && getId().equals(((Province) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Province{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", geoPolygonArea='" + getGeoPolygonArea() + "'" +
            ", geoPolygonAreaContentType='" + getGeoPolygonAreaContentType() + "'" +
            "}";
    }
}
