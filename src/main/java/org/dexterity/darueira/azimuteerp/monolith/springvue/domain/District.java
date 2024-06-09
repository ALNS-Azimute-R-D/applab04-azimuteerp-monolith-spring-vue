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
 * A District.
 */
@Entity
@Table(name = "tb_district")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 8)
    @Column(name = "acronym", length = 8, nullable = false)
    private String acronym;

    @NotNull
    @Size(max = 40)
    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Lob
    @Column(name = "geo_polygon_area")
    private byte[] geoPolygonArea;

    @Column(name = "geo_polygon_area_content_type")
    private String geoPolygonAreaContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "province", "districtsLists" }, allowSetters = true)
    private TownCity townCity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "district")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "district", "venuesLists" }, allowSetters = true)
    private Set<CommonLocality> commonLocalitiesLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "district")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "district")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "buyerPerson", "customerType", "district", "ordersLists" }, allowSetters = true)
    private Set<Customer> customersLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public District id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public District acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public District name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getGeoPolygonArea() {
        return this.geoPolygonArea;
    }

    public District geoPolygonArea(byte[] geoPolygonArea) {
        this.setGeoPolygonArea(geoPolygonArea);
        return this;
    }

    public void setGeoPolygonArea(byte[] geoPolygonArea) {
        this.geoPolygonArea = geoPolygonArea;
    }

    public String getGeoPolygonAreaContentType() {
        return this.geoPolygonAreaContentType;
    }

    public District geoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
        return this;
    }

    public void setGeoPolygonAreaContentType(String geoPolygonAreaContentType) {
        this.geoPolygonAreaContentType = geoPolygonAreaContentType;
    }

    public TownCity getTownCity() {
        return this.townCity;
    }

    public void setTownCity(TownCity townCity) {
        this.townCity = townCity;
    }

    public District townCity(TownCity townCity) {
        this.setTownCity(townCity);
        return this;
    }

    public Set<CommonLocality> getCommonLocalitiesLists() {
        return this.commonLocalitiesLists;
    }

    public void setCommonLocalitiesLists(Set<CommonLocality> commonLocalities) {
        if (this.commonLocalitiesLists != null) {
            this.commonLocalitiesLists.forEach(i -> i.setDistrict(null));
        }
        if (commonLocalities != null) {
            commonLocalities.forEach(i -> i.setDistrict(this));
        }
        this.commonLocalitiesLists = commonLocalities;
    }

    public District commonLocalitiesLists(Set<CommonLocality> commonLocalities) {
        this.setCommonLocalitiesLists(commonLocalities);
        return this;
    }

    public District addCommonLocalitiesList(CommonLocality commonLocality) {
        this.commonLocalitiesLists.add(commonLocality);
        commonLocality.setDistrict(this);
        return this;
    }

    public District removeCommonLocalitiesList(CommonLocality commonLocality) {
        this.commonLocalitiesLists.remove(commonLocality);
        commonLocality.setDistrict(null);
        return this;
    }

    public Set<Person> getPersonsLists() {
        return this.personsLists;
    }

    public void setPersonsLists(Set<Person> people) {
        if (this.personsLists != null) {
            this.personsLists.forEach(i -> i.setDistrict(null));
        }
        if (people != null) {
            people.forEach(i -> i.setDistrict(this));
        }
        this.personsLists = people;
    }

    public District personsLists(Set<Person> people) {
        this.setPersonsLists(people);
        return this;
    }

    public District addPersonsList(Person person) {
        this.personsLists.add(person);
        person.setDistrict(this);
        return this;
    }

    public District removePersonsList(Person person) {
        this.personsLists.remove(person);
        person.setDistrict(null);
        return this;
    }

    public Set<Customer> getCustomersLists() {
        return this.customersLists;
    }

    public void setCustomersLists(Set<Customer> customers) {
        if (this.customersLists != null) {
            this.customersLists.forEach(i -> i.setDistrict(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setDistrict(this));
        }
        this.customersLists = customers;
    }

    public District customersLists(Set<Customer> customers) {
        this.setCustomersLists(customers);
        return this;
    }

    public District addCustomersList(Customer customer) {
        this.customersLists.add(customer);
        customer.setDistrict(this);
        return this;
    }

    public District removeCustomersList(Customer customer) {
        this.customersLists.remove(customer);
        customer.setDistrict(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return getId() != null && getId().equals(((District) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "District{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", geoPolygonArea='" + getGeoPolygonArea() + "'" +
            ", geoPolygonAreaContentType='" + getGeoPolygonAreaContentType() + "'" +
            "}";
    }
}
