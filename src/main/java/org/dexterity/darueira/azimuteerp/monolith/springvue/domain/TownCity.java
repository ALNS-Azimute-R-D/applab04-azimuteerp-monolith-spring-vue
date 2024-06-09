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
 * A TownCity.
 */
@Entity
@Table(name = "tb_town_city")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TownCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 8)
    @Column(name = "acronym", length = 8, nullable = false)
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
    @JsonIgnoreProperties(value = { "country", "townCitiesLists" }, allowSetters = true)
    private Province province;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "townCity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "townCity", "commonLocalitiesLists", "personsLists", "customersLists" }, allowSetters = true)
    private Set<District> districtsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TownCity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public TownCity acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public TownCity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getGeoPolygonArea() {
        return this.geoPolygonArea;
    }

    public TownCity geoPolygonArea(byte[] geoPolygonArea) {
        this.setGeoPolygonArea(geoPolygonArea);
        return this;
    }

    public void setGeoPolygonArea(byte[] geoPolygonArea) {
        this.geoPolygonArea = geoPolygonArea;
    }

    public String getGeoPolygonAreaContentType() {
        return this.geoPolygonAreaContentType;
    }

    public TownCity geoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
        return this;
    }

    public void setGeoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
    }

    public Province getProvince() {
        return this.province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public TownCity province(Province province) {
        this.setProvince(province);
        return this;
    }

    public Set<District> getDistrictsLists() {
        return this.districtsLists;
    }

    public void setDistrictsLists(Set<District> districts) {
        if (this.districtsLists != null) {
            this.districtsLists.forEach(i -> i.setTownCity(null));
        }
        if (districts != null) {
            districts.forEach(i -> i.setTownCity(this));
        }
        this.districtsLists = districts;
    }

    public TownCity districtsLists(Set<District> districts) {
        this.setDistrictsLists(districts);
        return this;
    }

    public TownCity addDistrictsList(District district) {
        this.districtsLists.add(district);
        district.setTownCity(this);
        return this;
    }

    public TownCity removeDistrictsList(District district) {
        this.districtsLists.remove(district);
        district.setTownCity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TownCity)) {
            return false;
        }
        return getId() != null && getId().equals(((TownCity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TownCity{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", geoPolygonArea='" + getGeoPolygonArea() + "'" +
            ", geoPolygonAreaContentType='" + getGeoPolygonAreaContentType() + "'" +
            "}";
    }
}
