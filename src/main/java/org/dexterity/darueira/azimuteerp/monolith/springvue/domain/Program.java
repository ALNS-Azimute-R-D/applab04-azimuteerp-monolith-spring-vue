package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Program.
 */
@Entity
@Table(name = "tb_program")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "acronym", length = 30)
    private String acronym;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 2048)
    @Column(name = "full_description", length = 2048)
    private String fullDescription;

    @Column(name = "target_public")
    private String targetPublic;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "program")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "program", "responsiblePerson" }, allowSetters = true)
    private Set<EventProgram> programsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "program")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "program", "activity", "responsiblePerson", "assetCollections" }, allowSetters = true)
    private Set<ScheduledActivity> scheduledActivitiesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Program id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Program acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public Program name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Program description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullDescription() {
        return this.fullDescription;
    }

    public Program fullDescription(String fullDescription) {
        this.setFullDescription(fullDescription);
        return this;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getTargetPublic() {
        return this.targetPublic;
    }

    public Program targetPublic(String targetPublic) {
        this.setTargetPublic(targetPublic);
        return this;
    }

    public void setTargetPublic(String targetPublic) {
        this.targetPublic = targetPublic;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Program activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Set<EventProgram> getProgramsLists() {
        return this.programsLists;
    }

    public void setProgramsLists(Set<EventProgram> eventPrograms) {
        if (this.programsLists != null) {
            this.programsLists.forEach(i -> i.setProgram(null));
        }
        if (eventPrograms != null) {
            eventPrograms.forEach(i -> i.setProgram(this));
        }
        this.programsLists = eventPrograms;
    }

    public Program programsLists(Set<EventProgram> eventPrograms) {
        this.setProgramsLists(eventPrograms);
        return this;
    }

    public Program addProgramsList(EventProgram eventProgram) {
        this.programsLists.add(eventProgram);
        eventProgram.setProgram(this);
        return this;
    }

    public Program removeProgramsList(EventProgram eventProgram) {
        this.programsLists.remove(eventProgram);
        eventProgram.setProgram(null);
        return this;
    }

    public Set<ScheduledActivity> getScheduledActivitiesLists() {
        return this.scheduledActivitiesLists;
    }

    public void setScheduledActivitiesLists(Set<ScheduledActivity> scheduledActivities) {
        if (this.scheduledActivitiesLists != null) {
            this.scheduledActivitiesLists.forEach(i -> i.setProgram(null));
        }
        if (scheduledActivities != null) {
            scheduledActivities.forEach(i -> i.setProgram(this));
        }
        this.scheduledActivitiesLists = scheduledActivities;
    }

    public Program scheduledActivitiesLists(Set<ScheduledActivity> scheduledActivities) {
        this.setScheduledActivitiesLists(scheduledActivities);
        return this;
    }

    public Program addScheduledActivitiesList(ScheduledActivity scheduledActivity) {
        this.scheduledActivitiesLists.add(scheduledActivity);
        scheduledActivity.setProgram(this);
        return this;
    }

    public Program removeScheduledActivitiesList(ScheduledActivity scheduledActivity) {
        this.scheduledActivitiesLists.remove(scheduledActivity);
        scheduledActivity.setProgram(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Program)) {
            return false;
        }
        return getId() != null && getId().equals(((Program) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Program{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", fullDescription='" + getFullDescription() + "'" +
            ", targetPublic='" + getTargetPublic() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
