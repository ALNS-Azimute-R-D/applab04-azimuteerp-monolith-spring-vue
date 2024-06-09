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
 * A Supplier.
 */
@Entity
@Table(name = "tb_supplier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "acronym", length = 50, nullable = false)
    private String acronym;

    @NotNull
    @Size(min = 2, max = 120)
    @Column(name = "company_name", length = 120, nullable = false)
    private String companyName;

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

    @Size(max = 50)
    @Column(name = "city", length = 50)
    private String city;

    @Size(max = 50)
    @Column(name = "state_province", length = 50)
    private String stateProvince;

    @Size(max = 15)
    @Column(name = "zip_postal_code", length = 15)
    private String zipPostalCode;

    @Size(max = 50)
    @Column(name = "country_region", length = 50)
    private String countryRegion;

    @Lob
    @Column(name = "point_location")
    private byte[] pointLocation;

    @Column(name = "point_location_content_type")
    private String pointLocationContentType;

    @Size(max = 120)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "main_email", length = 120)
    private String mainEmail;

    @Size(max = 15)
    @Column(name = "phone_number_1", length = 15)
    private String phoneNumber1;

    @Size(max = 15)
    @Column(name = "phone_number_2", length = 15)
    private String phoneNumber2;

    @Size(max = 2048)
    @Column(name = "custom_attributes_details_json", length = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(optional = false)
    @NotNull
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
    private Person representativePerson;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplier", "product", "warehouse" }, allowSetters = true)
    private Set<InventoryTransaction> inventoryTransactionsLists = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "toSuppliers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "brand", "toSuppliers", "productsLists", "stockLevelsLists" }, allowSetters = true)
    private Set<Product> toProducts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Supplier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Supplier acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Supplier companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public Supplier streetAddress(String streetAddress) {
        this.setStreetAddress(streetAddress);
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public Supplier houseNumber(String houseNumber) {
        this.setHouseNumber(houseNumber);
        return this;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public Supplier locationName(String locationName) {
        this.setLocationName(locationName);
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCity() {
        return this.city;
    }

    public Supplier city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return this.stateProvince;
    }

    public Supplier stateProvince(String stateProvince) {
        this.setStateProvince(stateProvince);
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getZipPostalCode() {
        return this.zipPostalCode;
    }

    public Supplier zipPostalCode(String zipPostalCode) {
        this.setZipPostalCode(zipPostalCode);
        return this;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getCountryRegion() {
        return this.countryRegion;
    }

    public Supplier countryRegion(String countryRegion) {
        this.setCountryRegion(countryRegion);
        return this;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public byte[] getPointLocation() {
        return this.pointLocation;
    }

    public Supplier pointLocation(byte[] pointLocation) {
        this.setPointLocation(pointLocation);
        return this;
    }

    public void setPointLocation(byte[] pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getPointLocationContentType() {
        return this.pointLocationContentType;
    }

    public Supplier pointLocationContentType(String pointLocationContentType) {
        this.pointLocationContentType = pointLocationContentType;
        return this;
    }

    public void setPointLocationContentType(String pointLocationContentType) {
        this.pointLocationContentType = pointLocationContentType;
    }

    public String getMainEmail() {
        return this.mainEmail;
    }

    public Supplier mainEmail(String mainEmail) {
        this.setMainEmail(mainEmail);
        return this;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public String getPhoneNumber1() {
        return this.phoneNumber1;
    }

    public Supplier phoneNumber1(String phoneNumber1) {
        this.setPhoneNumber1(phoneNumber1);
        return this;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return this.phoneNumber2;
    }

    public Supplier phoneNumber2(String phoneNumber2) {
        this.setPhoneNumber2(phoneNumber2);
        return this;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public Supplier customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Supplier activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Person getRepresentativePerson() {
        return this.representativePerson;
    }

    public void setRepresentativePerson(Person person) {
        this.representativePerson = person;
    }

    public Supplier representativePerson(Person person) {
        this.setRepresentativePerson(person);
        return this;
    }

    public Set<InventoryTransaction> getInventoryTransactionsLists() {
        return this.inventoryTransactionsLists;
    }

    public void setInventoryTransactionsLists(Set<InventoryTransaction> inventoryTransactions) {
        if (this.inventoryTransactionsLists != null) {
            this.inventoryTransactionsLists.forEach(i -> i.setSupplier(null));
        }
        if (inventoryTransactions != null) {
            inventoryTransactions.forEach(i -> i.setSupplier(this));
        }
        this.inventoryTransactionsLists = inventoryTransactions;
    }

    public Supplier inventoryTransactionsLists(Set<InventoryTransaction> inventoryTransactions) {
        this.setInventoryTransactionsLists(inventoryTransactions);
        return this;
    }

    public Supplier addInventoryTransactionsList(InventoryTransaction inventoryTransaction) {
        this.inventoryTransactionsLists.add(inventoryTransaction);
        inventoryTransaction.setSupplier(this);
        return this;
    }

    public Supplier removeInventoryTransactionsList(InventoryTransaction inventoryTransaction) {
        this.inventoryTransactionsLists.remove(inventoryTransaction);
        inventoryTransaction.setSupplier(null);
        return this;
    }

    public Set<Product> getToProducts() {
        return this.toProducts;
    }

    public void setToProducts(Set<Product> products) {
        if (this.toProducts != null) {
            this.toProducts.forEach(i -> i.removeToSupplier(this));
        }
        if (products != null) {
            products.forEach(i -> i.addToSupplier(this));
        }
        this.toProducts = products;
    }

    public Supplier toProducts(Set<Product> products) {
        this.setToProducts(products);
        return this;
    }

    public Supplier addToProduct(Product product) {
        this.toProducts.add(product);
        product.getToSuppliers().add(this);
        return this;
    }

    public Supplier removeToProduct(Product product) {
        this.toProducts.remove(product);
        product.getToSuppliers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supplier)) {
            return false;
        }
        return getId() != null && getId().equals(((Supplier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Supplier{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", zipPostalCode='" + getZipPostalCode() + "'" +
            ", countryRegion='" + getCountryRegion() + "'" +
            ", pointLocation='" + getPointLocation() + "'" +
            ", pointLocationContentType='" + getPointLocationContentType() + "'" +
            ", mainEmail='" + getMainEmail() + "'" +
            ", phoneNumber1='" + getPhoneNumber1() + "'" +
            ", phoneNumber2='" + getPhoneNumber2() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
