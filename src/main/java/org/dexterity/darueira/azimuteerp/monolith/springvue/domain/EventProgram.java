package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EventProgram.
 */
@Entity
@Table(name = "tb_event_program")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "percentage_execution")
    private Double percentageExecution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "mainVenue",
            "typeOfEvent",
            "promoteurPerson",
            "assetCollections",
            "eventProgramsLists",
            "ticketsPurchasedLists",
            "eventAttendeesLists",
        },
        allowSetters = true
    )
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "programsLists", "scheduledActivitiesLists" }, allowSetters = true)
    private Program program;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventProgram id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentageExecution() {
        return this.percentageExecution;
    }

    public EventProgram percentageExecution(Double percentageExecution) {
        this.setPercentageExecution(percentageExecution);
        return this;
    }

    public void setPercentageExecution(Double percentageExecution) {
        this.percentageExecution = percentageExecution;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventProgram event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Program getProgram() {
        return this.program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public EventProgram program(Program program) {
        this.setProgram(program);
        return this;
    }

    public Person getResponsiblePerson() {
        return this.responsiblePerson;
    }

    public void setResponsiblePerson(Person person) {
        this.responsiblePerson = person;
    }

    public EventProgram responsiblePerson(Person person) {
        this.setResponsiblePerson(person);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventProgram)) {
            return false;
        }
        return getId() != null && getId().equals(((EventProgram) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventProgram{" +
            "id=" + getId() +
            ", percentageExecution=" + getPercentageExecution() +
            "}";
    }
}
