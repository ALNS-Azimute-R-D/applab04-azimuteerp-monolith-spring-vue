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
 * - Warehouse
 * - Supplier
 * - Brand
 * - Product
 * - InventoryTransaction
 * - StockLevel
 */
@Entity
@Table(name = "tb_warehouse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Warehouse implements Serializable {

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
    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

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
    @Column(name = "land_phone_number", length = 15)
    private String landPhoneNumber;

    @Size(max = 15)
    @Column(name = "mobile_phone_number", length = 15)
    private String mobilePhoneNumber;

    @Size(max = 15)
    @Column(name = "fax_number", length = 15)
    private String faxNumber;

    @Size(max = 2048)
    @Column(name = "custom_attributes_details_json", length = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplier", "product", "warehouse" }, allowSetters = true)
    private Set<InventoryTransaction> inventoryTransactionsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "warehouse", "product" }, allowSetters = true)
    private Set<StockLevel> stockLevelsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Warehouse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Warehouse acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public Warehouse name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Warehouse description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public Warehouse streetAddress(String streetAddress) {
        this.setStreetAddress(streetAddress);
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public Warehouse houseNumber(String houseNumber) {
        this.setHouseNumber(houseNumber);
        return this;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public Warehouse locationName(String locationName) {
        this.setLocationName(locationName);
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Warehouse postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public byte[] getPointLocation() {
        return this.pointLocation;
    }

    public Warehouse pointLocation(byte[] pointLocation) {
        this.setPointLocation(pointLocation);
        return this;
    }

    public void setPointLocation(byte[] pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getPointLocationContentType() {
        return this.pointLocationContentType;
    }

    public Warehouse pointLocationContentType(String pointLocationContentType) {
        this.pointLocationContentType = pointLocationContentType;
        return this;
    }

    public void setPointLocationContentType(String pointLocationContentType) {
        this.pointLocationContentType = pointLocationContentType;
    }

    public String getMainEmail() {
        return this.mainEmail;
    }

    public Warehouse mainEmail(String mainEmail) {
        this.setMainEmail(mainEmail);
        return this;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public String getLandPhoneNumber() {
        return this.landPhoneNumber;
    }

    public Warehouse landPhoneNumber(String landPhoneNumber) {
        this.setLandPhoneNumber(landPhoneNumber);
        return this;
    }

    public void setLandPhoneNumber(String landPhoneNumber) {
        this.landPhoneNumber = landPhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }

    public Warehouse mobilePhoneNumber(String mobilePhoneNumber) {
        this.setMobilePhoneNumber(mobilePhoneNumber);
        return this;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getFaxNumber() {
        return this.faxNumber;
    }

    public Warehouse faxNumber(String faxNumber) {
        this.setFaxNumber(faxNumber);
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public Warehouse customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Warehouse activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Set<InventoryTransaction> getInventoryTransactionsLists() {
        return this.inventoryTransactionsLists;
    }

    public void setInventoryTransactionsLists(Set<InventoryTransaction> inventoryTransactions) {
        if (this.inventoryTransactionsLists != null) {
            this.inventoryTransactionsLists.forEach(i -> i.setWarehouse(null));
        }
        if (inventoryTransactions != null) {
            inventoryTransactions.forEach(i -> i.setWarehouse(this));
        }
        this.inventoryTransactionsLists = inventoryTransactions;
    }

    public Warehouse inventoryTransactionsLists(Set<InventoryTransaction> inventoryTransactions) {
        this.setInventoryTransactionsLists(inventoryTransactions);
        return this;
    }

    public Warehouse addInventoryTransactionsList(InventoryTransaction inventoryTransaction) {
        this.inventoryTransactionsLists.add(inventoryTransaction);
        inventoryTransaction.setWarehouse(this);
        return this;
    }

    public Warehouse removeInventoryTransactionsList(InventoryTransaction inventoryTransaction) {
        this.inventoryTransactionsLists.remove(inventoryTransaction);
        inventoryTransaction.setWarehouse(null);
        return this;
    }

    public Set<StockLevel> getStockLevelsLists() {
        return this.stockLevelsLists;
    }

    public void setStockLevelsLists(Set<StockLevel> stockLevels) {
        if (this.stockLevelsLists != null) {
            this.stockLevelsLists.forEach(i -> i.setWarehouse(null));
        }
        if (stockLevels != null) {
            stockLevels.forEach(i -> i.setWarehouse(this));
        }
        this.stockLevelsLists = stockLevels;
    }

    public Warehouse stockLevelsLists(Set<StockLevel> stockLevels) {
        this.setStockLevelsLists(stockLevels);
        return this;
    }

    public Warehouse addStockLevelsList(StockLevel stockLevel) {
        this.stockLevelsLists.add(stockLevel);
        stockLevel.setWarehouse(this);
        return this;
    }

    public Warehouse removeStockLevelsList(StockLevel stockLevel) {
        this.stockLevelsLists.remove(stockLevel);
        stockLevel.setWarehouse(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warehouse)) {
            return false;
        }
        return getId() != null && getId().equals(((Warehouse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Warehouse{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", pointLocation='" + getPointLocation() + "'" +
            ", pointLocationContentType='" + getPointLocationContentType() + "'" +
            ", mainEmail='" + getMainEmail() + "'" +
            ", landPhoneNumber='" + getLandPhoneNumber() + "'" +
            ", mobilePhoneNumber='" + getMobilePhoneNumber() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
