package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.GenderEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Person.
 */
@Entity
@Table(name = "tb_person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @NotNull
    @Size(max = 50)
    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Size(max = 100)
    @Column(name = "fullname", length = 100)
    private String fullname;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderEnum gender;

    @Size(max = 20)
    @Column(name = "code_bi", length = 20)
    private String codeBI;

    @Size(max = 20)
    @Column(name = "code_nif", length = 20)
    private String codeNIF;

    @NotNull
    @Size(max = 120)
    @Column(name = "street_address", length = 120, nullable = false)
    private String streetAddress;

    @Size(max = 20)
    @Column(name = "house_number", length = 20)
    private String houseNumber;

    @Size(max = 50)
    @Column(name = "location_name", length = 50)
    private String locationName;

    @NotNull
    @Size(max = 9)
    @Column(name = "postal_code", length = 9, nullable = false)
    private String postalCode;

    @NotNull
    @Size(max = 120)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "main_email", length = 120, nullable = false, unique = true)
    private String mainEmail;

    @Size(max = 15)
    @Column(name = "land_phone_number", length = 15)
    private String landPhoneNumber;

    @Size(max = 15)
    @Column(name = "mobile_phone_number", length = 15)
    private String mobilePhoneNumber;

    @Size(max = 40)
    @Column(name = "occupation", length = 40)
    private String occupation;

    @Size(max = 5)
    @Column(name = "preferred_language", length = 5)
    private String preferredLanguage;

    @Size(max = 40)
    @Column(name = "username_in_o_auth_2", length = 40)
    private String usernameInOAuth2;

    @Size(max = 80)
    @Column(name = "user_id_in_o_auth_2", length = 80)
    private String userIdInOAuth2;

    @Size(max = 2048)
    @Column(name = "custom_attributes_details_json", length = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @Lob
    @Column(name = "avatar_img")
    private byte[] avatarImg;

    @Column(name = "avatar_img_content_type")
    private String avatarImgContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "personsLists" }, allowSetters = true)
    private TypeOfPerson typeOfPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "townCity", "commonLocalitiesLists", "personsLists", "customersLists" }, allowSetters = true)
    private District district;

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
    private Person managerPerson;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "managerPerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Person> managedPersonsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization", "person", "organizationMemberRolesLists" }, allowSetters = true)
    private Set<OrganizationMembership> organizationMembershipsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "representativePerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "representativePerson", "inventoryTransactionsLists", "toProducts" }, allowSetters = true)
    private Set<Supplier> suppliersLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyerPerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "buyerPerson", "customerType", "district", "ordersLists" }, allowSetters = true)
    private Set<Customer> customersLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdByUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfActivity", "createdByUser", "assetCollections", "scheduledActivitiesLists" },
        allowSetters = true
    )
    private Set<Activity> activitiesLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promoteurPerson")
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
    private Set<Event> promotedEventsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "responsiblePerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "event", "program", "responsiblePerson" }, allowSetters = true)
    private Set<EventProgram> eventsProgramsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "responsiblePerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "program", "activity", "responsiblePerson", "assetCollections" }, allowSetters = true)
    private Set<ScheduledActivity> scheduledActivitiesLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attendeePerson")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "attendeePerson", "event", "ticketPurchased" }, allowSetters = true)
    private Set<EventAttendee> eventAttendeesLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Person firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Person lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return this.fullname;
    }

    public Person fullname(String fullname) {
        this.setFullname(fullname);
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Person birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public GenderEnum getGender() {
        return this.gender;
    }

    public Person gender(GenderEnum gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getCodeBI() {
        return this.codeBI;
    }

    public Person codeBI(String codeBI) {
        this.setCodeBI(codeBI);
        return this;
    }

    public void setCodeBI(String codeBI) {
        this.codeBI = codeBI;
    }

    public String getCodeNIF() {
        return this.codeNIF;
    }

    public Person codeNIF(String codeNIF) {
        this.setCodeNIF(codeNIF);
        return this;
    }

    public void setCodeNIF(String codeNIF) {
        this.codeNIF = codeNIF;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public Person streetAddress(String streetAddress) {
        this.setStreetAddress(streetAddress);
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public Person houseNumber(String houseNumber) {
        this.setHouseNumber(houseNumber);
        return this;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public Person locationName(String locationName) {
        this.setLocationName(locationName);
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Person postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getMainEmail() {
        return this.mainEmail;
    }

    public Person mainEmail(String mainEmail) {
        this.setMainEmail(mainEmail);
        return this;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public String getLandPhoneNumber() {
        return this.landPhoneNumber;
    }

    public Person landPhoneNumber(String landPhoneNumber) {
        this.setLandPhoneNumber(landPhoneNumber);
        return this;
    }

    public void setLandPhoneNumber(String landPhoneNumber) {
        this.landPhoneNumber = landPhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }

    public Person mobilePhoneNumber(String mobilePhoneNumber) {
        this.setMobilePhoneNumber(mobilePhoneNumber);
        return this;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public Person occupation(String occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPreferredLanguage() {
        return this.preferredLanguage;
    }

    public Person preferredLanguage(String preferredLanguage) {
        this.setPreferredLanguage(preferredLanguage);
        return this;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getUsernameInOAuth2() {
        return this.usernameInOAuth2;
    }

    public Person usernameInOAuth2(String usernameInOAuth2) {
        this.setUsernameInOAuth2(usernameInOAuth2);
        return this;
    }

    public void setUsernameInOAuth2(String usernameInOAuth2) {
        this.usernameInOAuth2 = usernameInOAuth2;
    }

    public String getUserIdInOAuth2() {
        return this.userIdInOAuth2;
    }

    public Person userIdInOAuth2(String userIdInOAuth2) {
        this.setUserIdInOAuth2(userIdInOAuth2);
        return this;
    }

    public void setUserIdInOAuth2(String userIdInOAuth2) {
        this.userIdInOAuth2 = userIdInOAuth2;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public Person customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Person activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public byte[] getAvatarImg() {
        return this.avatarImg;
    }

    public Person avatarImg(byte[] avatarImg) {
        this.setAvatarImg(avatarImg);
        return this;
    }

    public void setAvatarImg(byte[] avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getAvatarImgContentType() {
        return this.avatarImgContentType;
    }

    public Person avatarImgContentType(String avatarImgContentType) {
        this.avatarImgContentType = avatarImgContentType;
        return this;
    }

    public void setAvatarImgContentType(String avatarImgContentType) {
        this.avatarImgContentType = avatarImgContentType;
    }

    public TypeOfPerson getTypeOfPerson() {
        return this.typeOfPerson;
    }

    public void setTypeOfPerson(TypeOfPerson typeOfPerson) {
        this.typeOfPerson = typeOfPerson;
    }

    public Person typeOfPerson(TypeOfPerson typeOfPerson) {
        this.setTypeOfPerson(typeOfPerson);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Person district(District district) {
        this.setDistrict(district);
        return this;
    }

    public Person getManagerPerson() {
        return this.managerPerson;
    }

    public void setManagerPerson(Person person) {
        this.managerPerson = person;
    }

    public Person managerPerson(Person person) {
        this.setManagerPerson(person);
        return this;
    }

    public Set<Person> getManagedPersonsLists() {
        return this.managedPersonsLists;
    }

    public void setManagedPersonsLists(Set<Person> people) {
        if (this.managedPersonsLists != null) {
            this.managedPersonsLists.forEach(i -> i.setManagerPerson(null));
        }
        if (people != null) {
            people.forEach(i -> i.setManagerPerson(this));
        }
        this.managedPersonsLists = people;
    }

    public Person managedPersonsLists(Set<Person> people) {
        this.setManagedPersonsLists(people);
        return this;
    }

    public Person addManagedPersonsList(Person person) {
        this.managedPersonsLists.add(person);
        person.setManagerPerson(this);
        return this;
    }

    public Person removeManagedPersonsList(Person person) {
        this.managedPersonsLists.remove(person);
        person.setManagerPerson(null);
        return this;
    }

    public Set<OrganizationMembership> getOrganizationMembershipsLists() {
        return this.organizationMembershipsLists;
    }

    public void setOrganizationMembershipsLists(Set<OrganizationMembership> organizationMemberships) {
        if (this.organizationMembershipsLists != null) {
            this.organizationMembershipsLists.forEach(i -> i.setPerson(null));
        }
        if (organizationMemberships != null) {
            organizationMemberships.forEach(i -> i.setPerson(this));
        }
        this.organizationMembershipsLists = organizationMemberships;
    }

    public Person organizationMembershipsLists(Set<OrganizationMembership> organizationMemberships) {
        this.setOrganizationMembershipsLists(organizationMemberships);
        return this;
    }

    public Person addOrganizationMembershipsList(OrganizationMembership organizationMembership) {
        this.organizationMembershipsLists.add(organizationMembership);
        organizationMembership.setPerson(this);
        return this;
    }

    public Person removeOrganizationMembershipsList(OrganizationMembership organizationMembership) {
        this.organizationMembershipsLists.remove(organizationMembership);
        organizationMembership.setPerson(null);
        return this;
    }

    public Set<Supplier> getSuppliersLists() {
        return this.suppliersLists;
    }

    public void setSuppliersLists(Set<Supplier> suppliers) {
        if (this.suppliersLists != null) {
            this.suppliersLists.forEach(i -> i.setRepresentativePerson(null));
        }
        if (suppliers != null) {
            suppliers.forEach(i -> i.setRepresentativePerson(this));
        }
        this.suppliersLists = suppliers;
    }

    public Person suppliersLists(Set<Supplier> suppliers) {
        this.setSuppliersLists(suppliers);
        return this;
    }

    public Person addSuppliersList(Supplier supplier) {
        this.suppliersLists.add(supplier);
        supplier.setRepresentativePerson(this);
        return this;
    }

    public Person removeSuppliersList(Supplier supplier) {
        this.suppliersLists.remove(supplier);
        supplier.setRepresentativePerson(null);
        return this;
    }

    public Set<Customer> getCustomersLists() {
        return this.customersLists;
    }

    public void setCustomersLists(Set<Customer> customers) {
        if (this.customersLists != null) {
            this.customersLists.forEach(i -> i.setBuyerPerson(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setBuyerPerson(this));
        }
        this.customersLists = customers;
    }

    public Person customersLists(Set<Customer> customers) {
        this.setCustomersLists(customers);
        return this;
    }

    public Person addCustomersList(Customer customer) {
        this.customersLists.add(customer);
        customer.setBuyerPerson(this);
        return this;
    }

    public Person removeCustomersList(Customer customer) {
        this.customersLists.remove(customer);
        customer.setBuyerPerson(null);
        return this;
    }

    public Set<Activity> getActivitiesLists() {
        return this.activitiesLists;
    }

    public void setActivitiesLists(Set<Activity> activities) {
        if (this.activitiesLists != null) {
            this.activitiesLists.forEach(i -> i.setCreatedByUser(null));
        }
        if (activities != null) {
            activities.forEach(i -> i.setCreatedByUser(this));
        }
        this.activitiesLists = activities;
    }

    public Person activitiesLists(Set<Activity> activities) {
        this.setActivitiesLists(activities);
        return this;
    }

    public Person addActivitiesList(Activity activity) {
        this.activitiesLists.add(activity);
        activity.setCreatedByUser(this);
        return this;
    }

    public Person removeActivitiesList(Activity activity) {
        this.activitiesLists.remove(activity);
        activity.setCreatedByUser(null);
        return this;
    }

    public Set<Event> getPromotedEventsLists() {
        return this.promotedEventsLists;
    }

    public void setPromotedEventsLists(Set<Event> events) {
        if (this.promotedEventsLists != null) {
            this.promotedEventsLists.forEach(i -> i.setPromoteurPerson(null));
        }
        if (events != null) {
            events.forEach(i -> i.setPromoteurPerson(this));
        }
        this.promotedEventsLists = events;
    }

    public Person promotedEventsLists(Set<Event> events) {
        this.setPromotedEventsLists(events);
        return this;
    }

    public Person addPromotedEventsList(Event event) {
        this.promotedEventsLists.add(event);
        event.setPromoteurPerson(this);
        return this;
    }

    public Person removePromotedEventsList(Event event) {
        this.promotedEventsLists.remove(event);
        event.setPromoteurPerson(null);
        return this;
    }

    public Set<EventProgram> getEventsProgramsLists() {
        return this.eventsProgramsLists;
    }

    public void setEventsProgramsLists(Set<EventProgram> eventPrograms) {
        if (this.eventsProgramsLists != null) {
            this.eventsProgramsLists.forEach(i -> i.setResponsiblePerson(null));
        }
        if (eventPrograms != null) {
            eventPrograms.forEach(i -> i.setResponsiblePerson(this));
        }
        this.eventsProgramsLists = eventPrograms;
    }

    public Person eventsProgramsLists(Set<EventProgram> eventPrograms) {
        this.setEventsProgramsLists(eventPrograms);
        return this;
    }

    public Person addEventsProgramsList(EventProgram eventProgram) {
        this.eventsProgramsLists.add(eventProgram);
        eventProgram.setResponsiblePerson(this);
        return this;
    }

    public Person removeEventsProgramsList(EventProgram eventProgram) {
        this.eventsProgramsLists.remove(eventProgram);
        eventProgram.setResponsiblePerson(null);
        return this;
    }

    public Set<ScheduledActivity> getScheduledActivitiesLists() {
        return this.scheduledActivitiesLists;
    }

    public void setScheduledActivitiesLists(Set<ScheduledActivity> scheduledActivities) {
        if (this.scheduledActivitiesLists != null) {
            this.scheduledActivitiesLists.forEach(i -> i.setResponsiblePerson(null));
        }
        if (scheduledActivities != null) {
            scheduledActivities.forEach(i -> i.setResponsiblePerson(this));
        }
        this.scheduledActivitiesLists = scheduledActivities;
    }

    public Person scheduledActivitiesLists(Set<ScheduledActivity> scheduledActivities) {
        this.setScheduledActivitiesLists(scheduledActivities);
        return this;
    }

    public Person addScheduledActivitiesList(ScheduledActivity scheduledActivity) {
        this.scheduledActivitiesLists.add(scheduledActivity);
        scheduledActivity.setResponsiblePerson(this);
        return this;
    }

    public Person removeScheduledActivitiesList(ScheduledActivity scheduledActivity) {
        this.scheduledActivitiesLists.remove(scheduledActivity);
        scheduledActivity.setResponsiblePerson(null);
        return this;
    }

    public Set<EventAttendee> getEventAttendeesLists() {
        return this.eventAttendeesLists;
    }

    public void setEventAttendeesLists(Set<EventAttendee> eventAttendees) {
        if (this.eventAttendeesLists != null) {
            this.eventAttendeesLists.forEach(i -> i.setAttendeePerson(null));
        }
        if (eventAttendees != null) {
            eventAttendees.forEach(i -> i.setAttendeePerson(this));
        }
        this.eventAttendeesLists = eventAttendees;
    }

    public Person eventAttendeesLists(Set<EventAttendee> eventAttendees) {
        this.setEventAttendeesLists(eventAttendees);
        return this;
    }

    public Person addEventAttendeesList(EventAttendee eventAttendee) {
        this.eventAttendeesLists.add(eventAttendee);
        eventAttendee.setAttendeePerson(this);
        return this;
    }

    public Person removeEventAttendeesList(EventAttendee eventAttendee) {
        this.eventAttendeesLists.remove(eventAttendee);
        eventAttendee.setAttendeePerson(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return getId() != null && getId().equals(((Person) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", codeBI='" + getCodeBI() + "'" +
            ", codeNIF='" + getCodeNIF() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", mainEmail='" + getMainEmail() + "'" +
            ", landPhoneNumber='" + getLandPhoneNumber() + "'" +
            ", mobilePhoneNumber='" + getMobilePhoneNumber() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", preferredLanguage='" + getPreferredLanguage() + "'" +
            ", usernameInOAuth2='" + getUsernameInOAuth2() + "'" +
            ", userIdInOAuth2='" + getUserIdInOAuth2() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", avatarImg='" + getAvatarImg() + "'" +
            ", avatarImgContentType='" + getAvatarImgContentType() + "'" +
            "}";
    }
}
