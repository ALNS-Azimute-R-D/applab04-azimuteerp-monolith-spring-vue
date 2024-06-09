package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentGatewayDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String aliasCode;

    @NotNull
    @Size(max = 120)
    private String description;

    @Size(max = 512)
    private String businessHandlerClazz;

    @NotNull
    private ActivationStatusEnum activationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliasCode() {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessHandlerClazz() {
        return businessHandlerClazz;
    }

    public void setBusinessHandlerClazz(String businessHandlerClazz) {
        this.businessHandlerClazz = businessHandlerClazz;
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
        if (!(o instanceof PaymentGatewayDTO)) {
            return false;
        }

        PaymentGatewayDTO paymentGatewayDTO = (PaymentGatewayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentGatewayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentGatewayDTO{" +
            "id=" + getId() +
            ", aliasCode='" + getAliasCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", businessHandlerClazz='" + getBusinessHandlerClazz() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
