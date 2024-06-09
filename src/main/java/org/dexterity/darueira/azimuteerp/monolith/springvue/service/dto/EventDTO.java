package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.EventStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String acronym;

    @NotNull
    @Size(max = 150)
    private String name;

    @NotNull
    @Size(max = 255)
    private String description;

    @Size(max = 2048)
    private String fullDescription;

    @NotNull
    private Instant startTime;

    private Instant endTime;

    @NotNull
    private BigDecimal defaultTicketValue;

    @NotNull
    private EventStatusEnum status;

    @NotNull
    private ActivationStatusEnum activationStatus;

    private VenueDTO mainVenue;

    @NotNull
    private TypeOfEventDTO typeOfEvent;

    private PersonDTO promoteurPerson;

    private Set<AssetCollectionDTO> assetCollections = new HashSet<>();

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

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDefaultTicketValue() {
        return defaultTicketValue;
    }

    public void setDefaultTicketValue(BigDecimal defaultTicketValue) {
        this.defaultTicketValue = defaultTicketValue;
    }

    public EventStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EventStatusEnum status) {
        this.status = status;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public VenueDTO getMainVenue() {
        return mainVenue;
    }

    public void setMainVenue(VenueDTO mainVenue) {
        this.mainVenue = mainVenue;
    }

    public TypeOfEventDTO getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(TypeOfEventDTO typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    public PersonDTO getPromoteurPerson() {
        return promoteurPerson;
    }

    public void setPromoteurPerson(PersonDTO promoteurPerson) {
        this.promoteurPerson = promoteurPerson;
    }

    public Set<AssetCollectionDTO> getAssetCollections() {
        return assetCollections;
    }

    public void setAssetCollections(Set<AssetCollectionDTO> assetCollections) {
        this.assetCollections = assetCollections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fullDescription='" + getFullDescription() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", defaultTicketValue=" + getDefaultTicketValue() +
            ", status='" + getStatus() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", mainVenue=" + getMainVenue() +
            ", typeOfEvent=" + getTypeOfEvent() +
            ", promoteurPerson=" + getPromoteurPerson() +
            ", assetCollections=" + getAssetCollections() +
            "}";
    }
}
