package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScheduledActivityDTO implements Serializable {

    private Long id;

    @Size(max = 150)
    private String customizedName;

    @NotNull
    private Instant startTime;

    private Instant endTime;

    private Integer numberOfAttendees;

    private Integer averageEvaluationInStars;

    @Size(max = 4096)
    private String customAttributtesDetailsJSON;

    @NotNull
    private ActivationStatusEnum activationStatus;

    private ProgramDTO program;

    private ActivityDTO activity;

    private PersonDTO responsiblePerson;

    private Set<AssetCollectionDTO> assetCollections = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomizedName() {
        return customizedName;
    }

    public void setCustomizedName(String customizedName) {
        this.customizedName = customizedName;
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

    public Integer getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public void setNumberOfAttendees(Integer numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }

    public Integer getAverageEvaluationInStars() {
        return averageEvaluationInStars;
    }

    public void setAverageEvaluationInStars(Integer averageEvaluationInStars) {
        this.averageEvaluationInStars = averageEvaluationInStars;
    }

    public String getCustomAttributtesDetailsJSON() {
        return customAttributtesDetailsJSON;
    }

    public void setCustomAttributtesDetailsJSON(String customAttributtesDetailsJSON) {
        this.customAttributtesDetailsJSON = customAttributtesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }

    public ActivityDTO getActivity() {
        return activity;
    }

    public void setActivity(ActivityDTO activity) {
        this.activity = activity;
    }

    public PersonDTO getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(PersonDTO responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
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
        if (!(o instanceof ScheduledActivityDTO)) {
            return false;
        }

        ScheduledActivityDTO scheduledActivityDTO = (ScheduledActivityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, scheduledActivityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduledActivityDTO{" +
            "id=" + getId() +
            ", customizedName='" + getCustomizedName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", numberOfAttendees=" + getNumberOfAttendees() +
            ", averageEvaluationInStars=" + getAverageEvaluationInStars() +
            ", customAttributtesDetailsJSON='" + getCustomAttributtesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", program=" + getProgram() +
            ", activity=" + getActivity() +
            ", responsiblePerson=" + getResponsiblePerson() +
            ", assetCollections=" + getAssetCollections() +
            "}";
    }
}
