package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.GenderEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String firstname;

    @NotNull
    @Size(max = 50)
    private String lastname;

    @Size(max = 100)
    private String fullname;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private GenderEnum gender;

    @Size(max = 20)
    private String codeBI;

    @Size(max = 20)
    private String codeNIF;

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

    @NotNull
    @Size(max = 120)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String mainEmail;

    @Size(max = 15)
    private String landPhoneNumber;

    @Size(max = 15)
    private String mobilePhoneNumber;

    @Size(max = 40)
    private String occupation;

    @Size(max = 5)
    private String preferredLanguage;

    @Size(max = 40)
    private String usernameInOAuth2;

    @Size(max = 80)
    private String userIdInOAuth2;

    @Size(max = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @Lob
    private byte[] avatarImg;

    private String avatarImgContentType;

    @NotNull
    private TypeOfPersonDTO typeOfPerson;

    private DistrictDTO district;

    private PersonDTO managerPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getCodeBI() {
        return codeBI;
    }

    public void setCodeBI(String codeBI) {
        this.codeBI = codeBI;
    }

    public String getCodeNIF() {
        return codeNIF;
    }

    public void setCodeNIF(String codeNIF) {
        this.codeNIF = codeNIF;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getUsernameInOAuth2() {
        return usernameInOAuth2;
    }

    public void setUsernameInOAuth2(String usernameInOAuth2) {
        this.usernameInOAuth2 = usernameInOAuth2;
    }

    public String getUserIdInOAuth2() {
        return userIdInOAuth2;
    }

    public void setUserIdInOAuth2(String userIdInOAuth2) {
        this.userIdInOAuth2 = userIdInOAuth2;
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

    public byte[] getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(byte[] avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getAvatarImgContentType() {
        return avatarImgContentType;
    }

    public void setAvatarImgContentType(String avatarImgContentType) {
        this.avatarImgContentType = avatarImgContentType;
    }

    public TypeOfPersonDTO getTypeOfPerson() {
        return typeOfPerson;
    }

    public void setTypeOfPerson(TypeOfPersonDTO typeOfPerson) {
        this.typeOfPerson = typeOfPerson;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public PersonDTO getManagerPerson() {
        return managerPerson;
    }

    public void setManagerPerson(PersonDTO managerPerson) {
        this.managerPerson = managerPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonDTO)) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonDTO{" +
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
            ", typeOfPerson=" + getTypeOfPerson() +
            ", district=" + getDistrict() +
            ", managerPerson=" + getManagerPerson() +
            "}";
    }
}
