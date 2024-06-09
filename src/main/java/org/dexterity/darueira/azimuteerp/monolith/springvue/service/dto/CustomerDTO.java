package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.CustomerStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Customer} entity.
 */
@Schema(description = "- Category\n- Article\n- Order\n- OrderItem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String customerBusinessCode;

    @NotNull
    @Size(min = 2, max = 80)
    private String fullname;

    @Size(max = 2048)
    private String customAttributesDetailsJSON;

    @NotNull
    private CustomerStatusEnum customerStatus;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private PersonDTO buyerPerson;

    private CustomerTypeDTO customerType;

    private DistrictDTO district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerBusinessCode() {
        return customerBusinessCode;
    }

    public void setCustomerBusinessCode(String customerBusinessCode) {
        this.customerBusinessCode = customerBusinessCode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCustomAttributesDetailsJSON() {
        return customAttributesDetailsJSON;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public CustomerStatusEnum getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatusEnum customerStatus) {
        this.customerStatus = customerStatus;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public PersonDTO getBuyerPerson() {
        return buyerPerson;
    }

    public void setBuyerPerson(PersonDTO buyerPerson) {
        this.buyerPerson = buyerPerson;
    }

    public CustomerTypeDTO getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerTypeDTO customerType) {
        this.customerType = customerType;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", customerBusinessCode='" + getCustomerBusinessCode() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", customerStatus='" + getCustomerStatus() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", buyerPerson=" + getBuyerPerson() +
            ", customerType=" + getCustomerType() +
            ", district=" + getDistrict() +
            "}";
    }
}
