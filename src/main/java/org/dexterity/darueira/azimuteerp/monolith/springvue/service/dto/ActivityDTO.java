package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 120)
    private String name;

    @Size(max = 512)
    private String shortDescription;

    @Size(max = 2048)
    private String fullDescription;

    @Size(max = 1024)
    private String mainGoals;

    private Duration estimatedDurationTime;

    private LocalDate lastPerformedDate;

    private Instant createdAt;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private TypeOfActivityDTO typeOfActivity;

    private PersonDTO createdByUser;

    private Set<AssetCollectionDTO> assetCollections = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getMainGoals() {
        return mainGoals;
    }

    public void setMainGoals(String mainGoals) {
        this.mainGoals = mainGoals;
    }

    public Duration getEstimatedDurationTime() {
        return estimatedDurationTime;
    }

    public void setEstimatedDurationTime(Duration estimatedDurationTime) {
        this.estimatedDurationTime = estimatedDurationTime;
    }

    public LocalDate getLastPerformedDate() {
        return lastPerformedDate;
    }

    public void setLastPerformedDate(LocalDate lastPerformedDate) {
        this.lastPerformedDate = lastPerformedDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public TypeOfActivityDTO getTypeOfActivity() {
        return typeOfActivity;
    }

    public void setTypeOfActivity(TypeOfActivityDTO typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
    }

    public PersonDTO getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(PersonDTO createdByUser) {
        this.createdByUser = createdByUser;
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
        if (!(o instanceof ActivityDTO)) {
            return false;
        }

        ActivityDTO activityDTO = (ActivityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, activityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", fullDescription='" + getFullDescription() + "'" +
            ", mainGoals='" + getMainGoals() + "'" +
            ", estimatedDurationTime='" + getEstimatedDurationTime() + "'" +
            ", lastPerformedDate='" + getLastPerformedDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", typeOfActivity=" + getTypeOfActivity() +
            ", createdByUser=" + getCreatedByUser() +
            ", assetCollections=" + getAssetCollections() +
            "}";
    }
}
