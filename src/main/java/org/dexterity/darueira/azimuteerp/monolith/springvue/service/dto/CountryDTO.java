package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ContinentEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Country} entity.
 */
@Schema(description = "- Country\n- Province\n- TownCity\n- District\n- Locality")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3)
    private String acronym;

    @NotNull
    @Size(max = 40)
    private String name;

    @NotNull
    private ContinentEnum continent;

    @Lob
    private byte[] geoPolygonArea;

    private String geoPolygonAreaContentType;

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

    public ContinentEnum getContinent() {
        return continent;
    }

    public void setContinent(ContinentEnum continent) {
        this.continent = continent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", continent='" + getContinent() + "'" +
            ", geoPolygonArea='" + getGeoPolygonArea() + "'" +
            "}";
    }
}
