package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ContinentEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * - Country
 * - Province
 * - TownCity
 * - District
 * - Locality
 */
@Entity
@Table(name = "tb_country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Country implements Serializable {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "continent", nullable = false)
    private ContinentEnum continent;

    @Lob
    @Column(name = "geo_polygon_area")
    private byte[] geoPolygonArea;

    @Column(name = "geo_polygon_area_content_type")
    private String geoPolygonAreaContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "country", "townCitiesLists" }, allowSetters = true)
    private Set<Province> provincesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Country id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Country acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public Country name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContinentEnum getContinent() {
        return this.continent;
    }

    public Country continent(ContinentEnum continent) {
        this.setContinent(continent);
        return this;
    }

    public void setContinent(ContinentEnum continent) {
        this.continent = continent;
    }

    public byte[] getGeoPolygonArea() {
        return this.geoPolygonArea;
    }

    public Country geoPolygonArea(byte[] geoPolygonArea) {
        this.setGeoPolygonArea(geoPolygonArea);
        return this;
    }

    public void setGeoPolygonArea(byte[] geoPolygonArea) {
        this.geoPolygonArea = geoPolygonArea;
    }

    public String getGeoPolygonAreaContentType() {
        return this.geoPolygonAreaContentType;
    }

    public Country geoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
        return this;
    }

    public void setGeoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
    }

    public Set<Province> getProvincesLists() {
        return this.provincesLists;
    }

    public void setProvincesLists(Set<Province> provinces) {
        if (this.provincesLists != null) {
            this.provincesLists.forEach(i -> i.setCountry(null));
        }
        if (provinces != null) {
            provinces.forEach(i -> i.setCountry(this));
        }
        this.provincesLists = provinces;
    }

    public Country provincesLists(Set<Province> provinces) {
        this.setProvincesLists(provinces);
        return this;
    }

    public Country addProvincesList(Province province) {
        this.provincesLists.add(province);
        province.setCountry(this);
        return this;
    }

    public Country removeProvincesList(Province province) {
        this.provincesLists.remove(province);
        province.setCountry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return getId() != null && getId().equals(((Country) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", continent='" + getContinent() + "'" +
            ", geoPolygonArea='" + getGeoPolygonArea() + "'" +
            ", geoPolygonAreaContentType='" + getGeoPolygonAreaContentType() + "'" +
            "}";
    }
}
