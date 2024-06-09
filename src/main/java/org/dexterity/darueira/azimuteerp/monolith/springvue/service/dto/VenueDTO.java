package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Venue} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VenueDTO implements Serializable {

    private Long id;

    @Size(max = 30)
    private String acronym;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @Lob
    private byte[] geoPointLocation;

    private String geoPointLocationContentType;

    @NotNull
    private TypeOfVenueDTO typeOfVenue;

    private CommonLocalityDTO commonLocality;

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

    public byte[] getGeoPointLocation() {
        return geoPointLocation;
    }

    public void setGeoPointLocation(byte[] geoPointLocation) {
        this.geoPointLocation = geoPointLocation;
    }

    public String getGeoPointLocationContentType() {
        return geoPointLocationContentType;
    }

    public void setGeoPointLocationContentType(String geoPointLocationContentType) {
        this.geoPointLocationContentType = geoPointLocationContentType;
    }

    public TypeOfVenueDTO getTypeOfVenue() {
        return typeOfVenue;
    }

    public void setTypeOfVenue(TypeOfVenueDTO typeOfVenue) {
        this.typeOfVenue = typeOfVenue;
    }

    public CommonLocalityDTO getCommonLocality() {
        return commonLocality;
    }

    public void setCommonLocality(CommonLocalityDTO commonLocality) {
        this.commonLocality = commonLocality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VenueDTO)) {
            return false;
        }

        VenueDTO venueDTO = (VenueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, venueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VenueDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", geoPointLocation='" + getGeoPointLocation() + "'" +
            ", typeOfVenue=" + getTypeOfVenue() +
            ", commonLocality=" + getCommonLocality() +
            "}";
    }
}
