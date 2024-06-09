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
 * - TypeOfPerson
 * - Person
 * - OrganizationRole
 * - OrganizationMembership
 * - OrganizationMemberRole
 */
@Entity
@Table(name = "tb_type_person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @NotNull
    @Size(max = 80)
    @Column(name = "description", length = 80, nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeOfPerson")
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
    private Set<Person> personsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TypeOfPerson id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TypeOfPerson code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public TypeOfPerson description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Person> getPersonsLists() {
        return this.personsLists;
    }

    public void setPersonsLists(Set<Person> people) {
        if (this.personsLists != null) {
            this.personsLists.forEach(i -> i.setTypeOfPerson(null));
        }
        if (people != null) {
            people.forEach(i -> i.setTypeOfPerson(this));
        }
        this.personsLists = people;
    }

    public TypeOfPerson personsLists(Set<Person> people) {
        this.setPersonsLists(people);
        return this;
    }

    public TypeOfPerson addPersonsList(Person person) {
        this.personsLists.add(person);
        person.setTypeOfPerson(this);
        return this;
    }

    public TypeOfPerson removePersonsList(Person person) {
        this.personsLists.remove(person);
        person.setTypeOfPerson(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfPerson)) {
            return false;
        }
        return getId() != null && getId().equals(((TypeOfPerson) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfPerson{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
