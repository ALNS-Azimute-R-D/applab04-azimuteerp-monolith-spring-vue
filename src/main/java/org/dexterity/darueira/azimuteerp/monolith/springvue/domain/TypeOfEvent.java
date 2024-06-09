package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * - TypeOfEvent
 * - TypeOfVenue
 * - TypeOfActivity
 * - Venue
 * - Activity
 * - Event
 * - Program
 * - EventProgram
 * - ScheduledActivity
 * - EventAttendee
 * - TicketPurchased
 */
@Entity
@Table(name = "tb_type_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfEvent implements Serializable {

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

    @Size(max = 255)
    @Column(name = "handler_clazz_name", length = 255)
    private String handlerClazzName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeOfEvent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Event> eventsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeOfEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public TypeOfEvent acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public TypeOfEvent name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TypeOfEvent description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHandlerClazzName() {
        return this.handlerClazzName;
    }

    public TypeOfEvent handlerClazzName(String handlerClazzName) {
        this.setHandlerClazzName(handlerClazzName);
        return this;
    }

    public void setHandlerClazzName(String handlerClazzName) {
        this.handlerClazzName = handlerClazzName;
    }

    public Set<Event> getEventsLists() {
        return this.eventsLists;
    }

    public void setEventsLists(Set<Event> events) {
        if (this.eventsLists != null) {
            this.eventsLists.forEach(i -> i.setTypeOfEvent(null));
        }
        if (events != null) {
            events.forEach(i -> i.setTypeOfEvent(this));
        }
        this.eventsLists = events;
    }

    public TypeOfEvent eventsLists(Set<Event> events) {
        this.setEventsLists(events);
        return this;
    }

    public TypeOfEvent addEventsList(Event event) {
        this.eventsLists.add(event);
        event.setTypeOfEvent(this);
        return this;
    }

    public TypeOfEvent removeEventsList(Event event) {
        this.eventsLists.remove(event);
        event.setTypeOfEvent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfEvent)) {
            return false;
        }
        return getId() != null && getId().equals(((TypeOfEvent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfEvent{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", handlerClazzName='" + getHandlerClazzName() + "'" +
            "}";
    }
}
