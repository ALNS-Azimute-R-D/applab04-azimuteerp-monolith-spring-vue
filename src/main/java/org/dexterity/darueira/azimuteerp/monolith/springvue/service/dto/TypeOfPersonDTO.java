package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPerson} entity.
 */
@Schema(description = "- TypeOfPerson\n- Person\n- OrganizationRole\n- OrganizationMembership\n- OrganizationMemberRole")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeOfPersonDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    @Size(max = 80)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOfPersonDTO)) {
            return false;
        }

        TypeOfPersonDTO typeOfPersonDTO = (TypeOfPersonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeOfPersonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeOfPersonDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
