package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommonLocalityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String acronym;

    @NotNull
    @Size(max = 840)
    private String name;

    @Size(max = 512)
    private String description;

    @NotNull
    @Size(max = 120)
    private String streetAddress;

    @Size(max = 20)
    private String houseNumber;

    @Size(max = 50)
    private String locationName;

    @NotNull
    @Size(max = 9)
    private String postalCode;

    @Lob
    private byte[] geoPolygonArea;

    private String geoPolygonAreaContentType;

    @NotNull
    private DistrictDTO district;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonLocalityDTO)) {
            return false;
        }

        CommonLocalityDTO commonLocalityDTO = (CommonLocalityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commonLocalityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommonLocalityDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", geoPolygonArea='" + getGeoPolygonArea() + "'" +
            ", district=" + getDistrict() +
            "}";
    }
}
