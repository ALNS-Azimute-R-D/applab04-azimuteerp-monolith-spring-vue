package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 8)
    private String acronym;

    @NotNull
    @Size(max = 40)
    private String name;

    @Lob
    private byte[] geoPolygonArea;

    private String geoPolygonAreaContentType;

    @NotNull
    private TownCityDTO townCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getGeoPolygonArea() {
        return geoPolygonArea;
    }

    public void setGeoPolygonArea(byte[] geoPolygonArea) {
        this.geoPolygonArea = geoPolygonArea;
    }

    public String getGeoPolygonAreaContentType() {
        return geoPolygonAreaContentType;
    }

    public void setGeoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
    }

    public TownCityDTO getTownCity() {
        return townCity;
    }

    public void setTownCity(TownCityDTO townCity) {
        this.townCity = townCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictDTO)) {
            return false;
        }

        DistrictDTO districtDTO = (DistrictDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, districtDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", geoPolygonArea='" + getGeoPolygonArea() + "'" +
            ", townCity=" + getTownCity() +
            "}";
    }
}
