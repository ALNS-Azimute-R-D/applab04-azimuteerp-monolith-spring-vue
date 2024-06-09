package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Supplier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SupplierDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String acronym;

    @NotNull
    @Size(min = 2, max = 120)
    private String companyName;

    @NotNull
    @Size(max = 120)
    private String streetAddress;

    @Size(max = 20)
    private String houseNumber;

    @Size(max = 50)
    private String locationName;

    @Size(max = 50)
    private String city;

    @Size(max = 50)
    private String stateProvince;

    @Size(max = 15)
    private String zipPostalCode;

    @Size(max = 50)
    private String countryRegion;

    @Lob
    private byte[] pointLocation;

    private String pointLocationContentType;

    @Size(max = 120)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String mainEmail;

    @Size(max = 15)
    private String phoneNumber1;

    @Size(max = 15)
    private String phoneNumber2;

    @Size(max = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private PersonDTO representativePerson;

    private Set<ProductDTO> toProducts = new HashSet<>();

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getZipPostalCode() {
        return zipPostalCode;
    }

    public void setZipPostalCode(String zipPostalCode) {
        this.zipPostalCode = zipPostalCode;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
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

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
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

    public PersonDTO getRepresentativePerson() {
        return representativePerson;
    }

    public void setRepresentativePerson(PersonDTO representativePerson) {
        this.representativePerson = representativePerson;
    }

    public Set<ProductDTO> getToProducts() {
        return toProducts;
    }

    public void setToProducts(Set<ProductDTO> toProducts) {
        this.toProducts = toProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierDTO)) {
            return false;
        }

        SupplierDTO supplierDTO = (SupplierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, supplierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierDTO{" +
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
            ", mainEmail='" + getMainEmail() + "'" +
            ", phoneNumber1='" + getPhoneNumber1() + "'" +
            ", phoneNumber2='" + getPhoneNumber2() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", representativePerson=" + getRepresentativePerson() +
            ", toProducts=" + getToProducts() +
            "}";
    }
}
