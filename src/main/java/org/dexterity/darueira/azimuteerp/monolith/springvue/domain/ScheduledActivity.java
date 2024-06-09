package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ScheduledActivity.
 */
@Entity
@Table(name = "tb_scheduled_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScheduledActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 150)
    @Column(name = "customized_name", length = 150)
    private String customizedName;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "number_of_attendees")
    private Integer numberOfAttendees;

    @Column(name = "average_evaluation_in_stars")
    private Integer averageEvaluationInStars;

    @Size(max = 4096)
    @Column(name = "custom_attributtes_details_json", length = 4096)
    private String customAttributtesDetailsJSON;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "programsLists", "scheduledActivitiesLists" }, allowSetters = true)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "typeOfActivity", "createdByUser", "assetCollections", "scheduledActivitiesLists" },
        allowSetters = true
    )
    private Activity activity;

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
    private Person responsiblePerson;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_scheduled_activity__asset_collection",
        joinColumns = @JoinColumn(name = "tb_scheduled_activity_id"),
        inverseJoinColumns = @JoinColumn(name = "asset_collection_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assets", "articles", "events", "activities", "scheduledActivities" }, allowSetters = true)
    private Set<AssetCollection> assetCollections = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ScheduledActivity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomizedName() {
        return this.customizedName;
    }

    public ScheduledActivity customizedName(String customizedName) {
        this.setCustomizedName(customizedName);
        return this;
    }

    public void setCustomizedName(String customizedName) {
        this.customizedName = customizedName;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public ScheduledActivity startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public ScheduledActivity endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getNumberOfAttendees() {
        return this.numberOfAttendees;
    }

    public ScheduledActivity numberOfAttendees(Integer numberOfAttendees) {
        this.setNumberOfAttendees(numberOfAttendees);
        return this;
    }

    public void setNumberOfAttendees(Integer numberOfAttendees) {
        this.numberOfAttendees = numberOfAttendees;
    }

    public Integer getAverageEvaluationInStars() {
        return this.averageEvaluationInStars;
    }

    public ScheduledActivity averageEvaluationInStars(Integer averageEvaluationInStars) {
        this.setAverageEvaluationInStars(averageEvaluationInStars);
        return this;
    }

    public void setAverageEvaluationInStars(Integer averageEvaluationInStars) {
        this.averageEvaluationInStars = averageEvaluationInStars;
    }

    public String getCustomAttributtesDetailsJSON() {
        return this.customAttributtesDetailsJSON;
    }

    public ScheduledActivity customAttributtesDetailsJSON(String customAttributtesDetailsJSON) {
        this.setCustomAttributtesDetailsJSON(customAttributtesDetailsJSON);
        return this;
    }

    public void setCustomAttributtesDetailsJSON(String customAttributtesDetailsJSON) {
        this.customAttributtesDetailsJSON = customAttributtesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public ScheduledActivity activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Program getProgram() {
        return this.program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public ScheduledActivity program(Program program) {
        this.setProgram(program);
        return this;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ScheduledActivity activity(Activity activity) {
        this.setActivity(activity);
        return this;
    }

    public Person getResponsiblePerson() {
        return this.responsiblePerson;
    }

    public void setResponsiblePerson(Person person) {
        this.responsiblePerson = person;
    }

    public ScheduledActivity responsiblePerson(Person person) {
        this.setResponsiblePerson(person);
        return this;
    }

    public Set<AssetCollection> getAssetCollections() {
        return this.assetCollections;
    }

    public void setAssetCollections(Set<AssetCollection> assetCollections) {
        this.assetCollections = assetCollections;
    }

    public ScheduledActivity assetCollections(Set<AssetCollection> assetCollections) {
        this.setAssetCollections(assetCollections);
        return this;
    }

    public ScheduledActivity addAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.add(assetCollection);
        return this;
    }

    public ScheduledActivity removeAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.remove(assetCollection);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduledActivity)) {
            return false;
        }
        return getId() != null && getId().equals(((ScheduledActivity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduledActivity{" +
            "id=" + getId() +
            ", customizedName='" + getCustomizedName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", numberOfAttendees=" + getNumberOfAttendees() +
            ", averageEvaluationInStars=" + getAverageEvaluationInStars() +
            ", customAttributtesDetailsJSON='" + getCustomAttributtesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
