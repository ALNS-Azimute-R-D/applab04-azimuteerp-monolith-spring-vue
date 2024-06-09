package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ActivityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.DistrictTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendeeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgramTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembershipTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.SupplierTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPersonTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = getPersonSample1();
        Person person2 = new Person();
        assertThat(person1).isNotEqualTo(person2);

        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);

        person2 = getPersonSample2();
        assertThat(person1).isNotEqualTo(person2);
    }

    @Test
    void typeOfPersonTest() {
        Person person = getPersonRandomSampleGenerator();
        TypeOfPerson typeOfPersonBack = getTypeOfPersonRandomSampleGenerator();

        person.setTypeOfPerson(typeOfPersonBack);
        assertThat(person.getTypeOfPerson()).isEqualTo(typeOfPersonBack);

        person.typeOfPerson(null);
        assertThat(person.getTypeOfPerson()).isNull();
    }

    @Test
    void districtTest() {
        Person person = getPersonRandomSampleGenerator();
        District districtBack = getDistrictRandomSampleGenerator();

        person.setDistrict(districtBack);
        assertThat(person.getDistrict()).isEqualTo(districtBack);

        person.district(null);
        assertThat(person.getDistrict()).isNull();
    }

    @Test
    void managerPersonTest() {
        Person person = getPersonRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        person.setManagerPerson(personBack);
        assertThat(person.getManagerPerson()).isEqualTo(personBack);

        person.managerPerson(null);
        assertThat(person.getManagerPerson()).isNull();
    }

    @Test
    void managedPersonsListTest() {
        Person person = getPersonRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        person.addManagedPersonsList(personBack);
        assertThat(person.getManagedPersonsLists()).containsOnly(personBack);
        assertThat(personBack.getManagerPerson()).isEqualTo(person);

        person.removeManagedPersonsList(personBack);
        assertThat(person.getManagedPersonsLists()).doesNotContain(personBack);
        assertThat(personBack.getManagerPerson()).isNull();

        person.managedPersonsLists(new HashSet<>(Set.of(personBack)));
        assertThat(person.getManagedPersonsLists()).containsOnly(personBack);
        assertThat(personBack.getManagerPerson()).isEqualTo(person);

        person.setManagedPersonsLists(new HashSet<>());
        assertThat(person.getManagedPersonsLists()).doesNotContain(personBack);
        assertThat(personBack.getManagerPerson()).isNull();
    }

    @Test
    void organizationMembershipsListTest() {
        Person person = getPersonRandomSampleGenerator();
        OrganizationMembership organizationMembershipBack = getOrganizationMembershipRandomSampleGenerator();

        person.addOrganizationMembershipsList(organizationMembershipBack);
        assertThat(person.getOrganizationMembershipsLists()).containsOnly(organizationMembershipBack);
        assertThat(organizationMembershipBack.getPerson()).isEqualTo(person);

        person.removeOrganizationMembershipsList(organizationMembershipBack);
        assertThat(person.getOrganizationMembershipsLists()).doesNotContain(organizationMembershipBack);
        assertThat(organizationMembershipBack.getPerson()).isNull();

        person.organizationMembershipsLists(new HashSet<>(Set.of(organizationMembershipBack)));
        assertThat(person.getOrganizationMembershipsLists()).containsOnly(organizationMembershipBack);
        assertThat(organizationMembershipBack.getPerson()).isEqualTo(person);

        person.setOrganizationMembershipsLists(new HashSet<>());
        assertThat(person.getOrganizationMembershipsLists()).doesNotContain(organizationMembershipBack);
        assertThat(organizationMembershipBack.getPerson()).isNull();
    }

    @Test
    void suppliersListTest() {
        Person person = getPersonRandomSampleGenerator();
        Supplier supplierBack = getSupplierRandomSampleGenerator();

        person.addSuppliersList(supplierBack);
        assertThat(person.getSuppliersLists()).containsOnly(supplierBack);
        assertThat(supplierBack.getRepresentativePerson()).isEqualTo(person);

        person.removeSuppliersList(supplierBack);
        assertThat(person.getSuppliersLists()).doesNotContain(supplierBack);
        assertThat(supplierBack.getRepresentativePerson()).isNull();

        person.suppliersLists(new HashSet<>(Set.of(supplierBack)));
        assertThat(person.getSuppliersLists()).containsOnly(supplierBack);
        assertThat(supplierBack.getRepresentativePerson()).isEqualTo(person);

        person.setSuppliersLists(new HashSet<>());
        assertThat(person.getSuppliersLists()).doesNotContain(supplierBack);
        assertThat(supplierBack.getRepresentativePerson()).isNull();
    }

    @Test
    void customersListTest() {
        Person person = getPersonRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        person.addCustomersList(customerBack);
        assertThat(person.getCustomersLists()).containsOnly(customerBack);
        assertThat(customerBack.getBuyerPerson()).isEqualTo(person);

        person.removeCustomersList(customerBack);
        assertThat(person.getCustomersLists()).doesNotContain(customerBack);
        assertThat(customerBack.getBuyerPerson()).isNull();

        person.customersLists(new HashSet<>(Set.of(customerBack)));
        assertThat(person.getCustomersLists()).containsOnly(customerBack);
        assertThat(customerBack.getBuyerPerson()).isEqualTo(person);

        person.setCustomersLists(new HashSet<>());
        assertThat(person.getCustomersLists()).doesNotContain(customerBack);
        assertThat(customerBack.getBuyerPerson()).isNull();
    }

    @Test
    void activitiesListTest() {
        Person person = getPersonRandomSampleGenerator();
        Activity activityBack = getActivityRandomSampleGenerator();

        person.addActivitiesList(activityBack);
        assertThat(person.getActivitiesLists()).containsOnly(activityBack);
        assertThat(activityBack.getCreatedByUser()).isEqualTo(person);

        person.removeActivitiesList(activityBack);
        assertThat(person.getActivitiesLists()).doesNotContain(activityBack);
        assertThat(activityBack.getCreatedByUser()).isNull();

        person.activitiesLists(new HashSet<>(Set.of(activityBack)));
        assertThat(person.getActivitiesLists()).containsOnly(activityBack);
        assertThat(activityBack.getCreatedByUser()).isEqualTo(person);

        person.setActivitiesLists(new HashSet<>());
        assertThat(person.getActivitiesLists()).doesNotContain(activityBack);
        assertThat(activityBack.getCreatedByUser()).isNull();
    }

    @Test
    void promotedEventsListTest() {
        Person person = getPersonRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        person.addPromotedEventsList(eventBack);
        assertThat(person.getPromotedEventsLists()).containsOnly(eventBack);
        assertThat(eventBack.getPromoteurPerson()).isEqualTo(person);

        person.removePromotedEventsList(eventBack);
        assertThat(person.getPromotedEventsLists()).doesNotContain(eventBack);
        assertThat(eventBack.getPromoteurPerson()).isNull();

        person.promotedEventsLists(new HashSet<>(Set.of(eventBack)));
        assertThat(person.getPromotedEventsLists()).containsOnly(eventBack);
        assertThat(eventBack.getPromoteurPerson()).isEqualTo(person);

        person.setPromotedEventsLists(new HashSet<>());
        assertThat(person.getPromotedEventsLists()).doesNotContain(eventBack);
        assertThat(eventBack.getPromoteurPerson()).isNull();
    }

    @Test
    void eventsProgramsListTest() {
        Person person = getPersonRandomSampleGenerator();
        EventProgram eventProgramBack = getEventProgramRandomSampleGenerator();

        person.addEventsProgramsList(eventProgramBack);
        assertThat(person.getEventsProgramsLists()).containsOnly(eventProgramBack);
        assertThat(eventProgramBack.getResponsiblePerson()).isEqualTo(person);

        person.removeEventsProgramsList(eventProgramBack);
        assertThat(person.getEventsProgramsLists()).doesNotContain(eventProgramBack);
        assertThat(eventProgramBack.getResponsiblePerson()).isNull();

        person.eventsProgramsLists(new HashSet<>(Set.of(eventProgramBack)));
        assertThat(person.getEventsProgramsLists()).containsOnly(eventProgramBack);
        assertThat(eventProgramBack.getResponsiblePerson()).isEqualTo(person);

        person.setEventsProgramsLists(new HashSet<>());
        assertThat(person.getEventsProgramsLists()).doesNotContain(eventProgramBack);
        assertThat(eventProgramBack.getResponsiblePerson()).isNull();
    }

    @Test
    void scheduledActivitiesListTest() {
        Person person = getPersonRandomSampleGenerator();
        ScheduledActivity scheduledActivityBack = getScheduledActivityRandomSampleGenerator();

        person.addScheduledActivitiesList(scheduledActivityBack);
        assertThat(person.getScheduledActivitiesLists()).containsOnly(scheduledActivityBack);
        assertThat(scheduledActivityBack.getResponsiblePerson()).isEqualTo(person);

        person.removeScheduledActivitiesList(scheduledActivityBack);
        assertThat(person.getScheduledActivitiesLists()).doesNotContain(scheduledActivityBack);
        assertThat(scheduledActivityBack.getResponsiblePerson()).isNull();

        person.scheduledActivitiesLists(new HashSet<>(Set.of(scheduledActivityBack)));
        assertThat(person.getScheduledActivitiesLists()).containsOnly(scheduledActivityBack);
        assertThat(scheduledActivityBack.getResponsiblePerson()).isEqualTo(person);

        person.setScheduledActivitiesLists(new HashSet<>());
        assertThat(person.getScheduledActivitiesLists()).doesNotContain(scheduledActivityBack);
        assertThat(scheduledActivityBack.getResponsiblePerson()).isNull();
    }

    @Test
    void eventAttendeesListTest() {
        Person person = getPersonRandomSampleGenerator();
        EventAttendee eventAttendeeBack = getEventAttendeeRandomSampleGenerator();

        person.addEventAttendeesList(eventAttendeeBack);
        assertThat(person.getEventAttendeesLists()).containsOnly(eventAttendeeBack);
        assertThat(eventAttendeeBack.getAttendeePerson()).isEqualTo(person);

        person.removeEventAttendeesList(eventAttendeeBack);
        assertThat(person.getEventAttendeesLists()).doesNotContain(eventAttendeeBack);
        assertThat(eventAttendeeBack.getAttendeePerson()).isNull();

        person.eventAttendeesLists(new HashSet<>(Set.of(eventAttendeeBack)));
        assertThat(person.getEventAttendeesLists()).containsOnly(eventAttendeeBack);
        assertThat(eventAttendeeBack.getAttendeePerson()).isEqualTo(person);

        person.setEventAttendeesLists(new HashSet<>());
        assertThat(person.getEventAttendeesLists()).doesNotContain(eventAttendeeBack);
        assertThat(eventAttendeeBack.getAttendeePerson()).isNull();
    }
}
