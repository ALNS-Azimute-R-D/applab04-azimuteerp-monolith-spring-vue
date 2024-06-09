package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Warehouse} entity.
 */
@Schema(description = "- Warehouse\n- Supplier\n- Brand\n- Product\n- InventoryTransaction\n- StockLevel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarehouseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String acronym;

    @NotNull
    @Size(min = 2, max = 120)
    private String name;

    @Size(max = 1024)
    private String description;

    @NotNull
    @Size(max = 120)
    private String streetAddress;

    @Size(max = 20)
    private String houseNumber;

    @Size(max = 50)
    private String locationName;

    @NotNull
    @Size(max = 9)
    private String postalCode;

    @Lob
    private byte[] pointLocation;

    private String pointLocationContentType;

    @Size(max = 120)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String mainEmail;

    @Size(max = 15)
    private String landPhoneNumber;

    @Size(max = 15)
    private String mobilePhoneNumber;

    @Size(max = 15)
    private String faxNumber;

    @Size(max = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    private ActivationStatusEnum activationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public byte[] getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(byte[] pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getPointLocationContentType() {
        return pointLocationContentType;
    }

    public void setPointLocationContentType(String pointLocationContentType) {
        this.pointLocationContentType = pointLocationContentType;
    }

    public String getMainEmail() {
        return mainEmail;
    }

    public void setMainEmail(String mainEmail) {
        this.mainEmail = mainEmail;
    }

    public String getLandPhoneNumber() {
        return landPhoneNumber;
    }

    public void setLandPhoneNumber(String landPhoneNumber) {
        this.landPhoneNumber = landPhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getCustomAttributesDetailsJSON() {
        return customAttributesDetailsJSON;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarehouseDTO)) {
            return false;
        }

        WarehouseDTO warehouseDTO = (WarehouseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, warehouseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarehouseDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", pointLocation='" + getPointLocation() + "'" +
            ", mainEmail='" + getMainEmail() + "'" +
            ", landPhoneNumber='" + getLandPhoneNumber() + "'" +
            ", mobilePhoneNumber='" + getMobilePhoneNumber() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
