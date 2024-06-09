package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.EventStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Event.
 */
@Entity
@Table(name = "tb_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "acronym", length = 50)
    private String acronym;

    @NotNull
    @Size(max = 150)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Size(max = 2048)
    @Column(name = "full_description", length = 2048)
    private String fullDescription;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @NotNull
    @Column(name = "default_ticket_value", precision = 21, scale = 2, nullable = false)
    private BigDecimal defaultTicketValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatusEnum status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "typeOfVenue", "commonLocality", "eventsLists" }, allowSetters = true)
    private Venue mainVenue;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "eventsLists" }, allowSetters = true)
    private TypeOfEvent typeOfEvent;

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
    private Person promoteurPerson;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_event__asset_collection",
        joinColumns = @JoinColumn(name = "tb_event_id"),
        inverseJoinColumns = @JoinColumn(name = "asset_collection_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assets", "articles", "events", "activities", "scheduledActivities" }, allowSetters = true)
    private Set<AssetCollection> assetCollections = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "program", "responsiblePerson" }, allowSetters = true)
    private Set<EventProgram> eventProgramsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "invoice", "eventAttendeesLists" }, allowSetters = true)
    private Set<TicketPurchased> ticketsPurchasedLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "attendeePerson", "event", "ticketPurchased" }, allowSetters = true)
    private Set<EventAttendee> eventAttendeesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Event id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Event acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public Event name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Event description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullDescription() {
        return this.fullDescription;
    }

    public Event fullDescription(String fullDescription) {
        this.setFullDescription(fullDescription);
        return this;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Event startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public Event endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDefaultTicketValue() {
        return this.defaultTicketValue;
    }

    public Event defaultTicketValue(BigDecimal defaultTicketValue) {
        this.setDefaultTicketValue(defaultTicketValue);
        return this;
    }

    public void setDefaultTicketValue(BigDecimal defaultTicketValue) {
        this.defaultTicketValue = defaultTicketValue;
    }

    public EventStatusEnum getStatus() {
        return this.status;
    }

    public Event status(EventStatusEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(EventStatusEnum status) {
        this.status = status;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Event activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Venue getMainVenue() {
        return this.mainVenue;
    }

    public void setMainVenue(Venue venue) {
        this.mainVenue = venue;
    }

    public Event mainVenue(Venue venue) {
        this.setMainVenue(venue);
        return this;
    }

    public TypeOfEvent getTypeOfEvent() {
        return this.typeOfEvent;
    }

    public void setTypeOfEvent(TypeOfEvent typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    public Event typeOfEvent(TypeOfEvent typeOfEvent) {
        this.setTypeOfEvent(typeOfEvent);
        return this;
    }

    public Person getPromoteurPerson() {
        return this.promoteurPerson;
    }

    public void setPromoteurPerson(Person person) {
        this.promoteurPerson = person;
    }

    public Event promoteurPerson(Person person) {
        this.setPromoteurPerson(person);
        return this;
    }

    public Set<AssetCollection> getAssetCollections() {
        return this.assetCollections;
    }

    public void setAssetCollections(Set<AssetCollection> assetCollections) {
        this.assetCollections = assetCollections;
    }

    public Event assetCollections(Set<AssetCollection> assetCollections) {
        this.setAssetCollections(assetCollections);
        return this;
    }

    public Event addAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.add(assetCollection);
        return this;
    }

    public Event removeAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.remove(assetCollection);
        return this;
    }

    public Set<EventProgram> getEventProgramsLists() {
        return this.eventProgramsLists;
    }

    public void setEventProgramsLists(Set<EventProgram> eventPrograms) {
        if (this.eventProgramsLists != null) {
            this.eventProgramsLists.forEach(i -> i.setEvent(null));
        }
        if (eventPrograms != null) {
            eventPrograms.forEach(i -> i.setEvent(this));
        }
        this.eventProgramsLists = eventPrograms;
    }

    public Event eventProgramsLists(Set<EventProgram> eventPrograms) {
        this.setEventProgramsLists(eventPrograms);
        return this;
    }

    public Event addEventProgramsList(EventProgram eventProgram) {
        this.eventProgramsLists.add(eventProgram);
        eventProgram.setEvent(this);
        return this;
    }

    public Event removeEventProgramsList(EventProgram eventProgram) {
        this.eventProgramsLists.remove(eventProgram);
        eventProgram.setEvent(null);
        return this;
    }

    public Set<TicketPurchased> getTicketsPurchasedLists() {
        return this.ticketsPurchasedLists;
    }

    public void setTicketsPurchasedLists(Set<TicketPurchased> ticketPurchaseds) {
        if (this.ticketsPurchasedLists != null) {
            this.ticketsPurchasedLists.forEach(i -> i.setEvent(null));
        }
        if (ticketPurchaseds != null) {
            ticketPurchaseds.forEach(i -> i.setEvent(this));
        }
        this.ticketsPurchasedLists = ticketPurchaseds;
    }

    public Event ticketsPurchasedLists(Set<TicketPurchased> ticketPurchaseds) {
        this.setTicketsPurchasedLists(ticketPurchaseds);
        return this;
    }

    public Event addTicketsPurchasedList(TicketPurchased ticketPurchased) {
        this.ticketsPurchasedLists.add(ticketPurchased);
        ticketPurchased.setEvent(this);
        return this;
    }

    public Event removeTicketsPurchasedList(TicketPurchased ticketPurchased) {
        this.ticketsPurchasedLists.remove(ticketPurchased);
        ticketPurchased.setEvent(null);
        return this;
    }

    public Set<EventAttendee> getEventAttendeesLists() {
        return this.eventAttendeesLists;
    }

    public void setEventAttendeesLists(Set<EventAttendee> eventAttendees) {
        if (this.eventAttendeesLists != null) {
            this.eventAttendeesLists.forEach(i -> i.setEvent(null));
        }
        if (eventAttendees != null) {
            eventAttendees.forEach(i -> i.setEvent(this));
        }
        this.eventAttendeesLists = eventAttendees;
    }

    public Event eventAttendeesLists(Set<EventAttendee> eventAttendees) {
        this.setEventAttendeesLists(eventAttendees);
        return this;
    }

    public Event addEventAttendeesList(EventAttendee eventAttendee) {
        this.eventAttendeesLists.add(eventAttendee);
        eventAttendee.setEvent(this);
        return this;
    }

    public Event removeEventAttendeesList(EventAttendee eventAttendee) {
        this.eventAttendeesLists.remove(eventAttendee);
        eventAttendee.setEvent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return getId() != null && getId().equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
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
            "}";
    }
}
