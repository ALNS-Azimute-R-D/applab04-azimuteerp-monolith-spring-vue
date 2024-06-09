package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Activity.
 */
@Entity
@Table(name = "tb_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 120)
    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Size(max = 512)
    @Column(name = "short_description", length = 512)
    private String shortDescription;

    @Size(max = 2048)
    @Column(name = "full_description", length = 2048)
    private String fullDescription;

    @Size(max = 1024)
    @Column(name = "main_goals", length = 1024)
    private String mainGoals;

    @Column(name = "estimated_duration_time")
    private Duration estimatedDurationTime;

    @Column(name = "last_performed_date")
    private LocalDate lastPerformedDate;

    @Column(name = "created_at")
    private Instant createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "activitiesLists" }, allowSetters = true)
    private TypeOfActivity typeOfActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "typeOfPerson",
            "district",
            "managerPerson",
            "managedPersonsLists",
            "organizationMembershipsLists",
            "suppliersLists",
            "customersLists",
            "activitiesLists",
            "promotedEventsLists",
            "eventsProgramsLists",
            "scheduledActivitiesLists",
            "eventAttendeesLists",
        },
        allowSetters = true
    )
    private Person createdByUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_activity__asset_collection",
        joinColumns = @JoinColumn(name = "tb_activity_id"),
        inverseJoinColumns = @JoinColumn(name = "asset_collection_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assets", "articles", "events", "activities", "scheduledActivities" }, allowSetters = true)
    private Set<AssetCollection> assetCollections = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "activity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "program", "activity", "responsiblePerson", "assetCollections" }, allowSetters = true)
    private Set<ScheduledActivity> scheduledActivitiesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Activity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Activity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public Activity shortDescription(String shortDescription) {
        this.setShortDescription(shortDescription);
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return this.fullDescription;
    }

    public Activity fullDescription(String fullDescription) {
        this.setFullDescription(fullDescription);
        return this;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getMainGoals() {
        return this.mainGoals;
    }

    public Activity mainGoals(String mainGoals) {
        this.setMainGoals(mainGoals);
        return this;
    }

    public void setMainGoals(String mainGoals) {
        this.mainGoals = mainGoals;
    }

    public Duration getEstimatedDurationTime() {
        return this.estimatedDurationTime;
    }

    public Activity estimatedDurationTime(Duration estimatedDurationTime) {
        this.setEstimatedDurationTime(estimatedDurationTime);
        return this;
    }

    public void setEstimatedDurationTime(Duration estimatedDurationTime) {
        this.estimatedDurationTime = estimatedDurationTime;
    }

    public LocalDate getLastPerformedDate() {
        return this.lastPerformedDate;
    }

    public Activity lastPerformedDate(LocalDate lastPerformedDate) {
        this.setLastPerformedDate(lastPerformedDate);
        return this;
    }

    public void setLastPerformedDate(LocalDate lastPerformedDate) {
        this.lastPerformedDate = lastPerformedDate;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Activity createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Activity activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public TypeOfActivity getTypeOfActivity() {
        return this.typeOfActivity;
    }

    public void setTypeOfActivity(TypeOfActivity typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
    }

    public Activity typeOfActivity(TypeOfActivity typeOfActivity) {
        this.setTypeOfActivity(typeOfActivity);
        return this;
    }

    public Person getCreatedByUser() {
        return this.createdByUser;
    }

    public void setCreatedByUser(Person person) {
        this.createdByUser = person;
    }

    public Activity createdByUser(Person person) {
        this.setCreatedByUser(person);
        return this;
    }

    public Set<AssetCollection> getAssetCollections() {
        return this.assetCollections;
    }

    public void setAssetCollections(Set<AssetCollection> assetCollections) {
        this.assetCollections = assetCollections;
    }

    public Activity assetCollections(Set<AssetCollection> assetCollections) {
        this.setAssetCollections(assetCollections);
        return this;
    }

    public Activity addAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.add(assetCollection);
        return this;
    }

    public Activity removeAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.remove(assetCollection);
        return this;
    }

    public Set<ScheduledActivity> getScheduledActivitiesLists() {
        return this.scheduledActivitiesLists;
    }

    public void setScheduledActivitiesLists(Set<ScheduledActivity> scheduledActivities) {
        if (this.scheduledActivitiesLists != null) {
            this.scheduledActivitiesLists.forEach(i -> i.setActivity(null));
        }
        if (scheduledActivities != null) {
            scheduledActivities.forEach(i -> i.setActivity(this));
        }
        this.scheduledActivitiesLists = scheduledActivities;
    }

    public Activity scheduledActivitiesLists(Set<ScheduledActivity> scheduledActivities) {
        this.setScheduledActivitiesLists(scheduledActivities);
        return this;
    }

    public Activity addScheduledActivitiesList(ScheduledActivity scheduledActivity) {
        this.scheduledActivitiesLists.add(scheduledActivity);
        scheduledActivity.setActivity(this);
        return this;
    }

    public Activity removeScheduledActivitiesList(ScheduledActivity scheduledActivity) {
        this.scheduledActivitiesLists.remove(scheduledActivity);
        scheduledActivity.setActivity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return getId() != null && getId().equals(((Activity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", fullDescription='" + getFullDescription() + "'" +
            ", mainGoals='" + getMainGoals() + "'" +
            ", estimatedDurationTime='" + getEstimatedDurationTime() + "'" +
            ", lastPerformedDate='" + getLastPerformedDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
