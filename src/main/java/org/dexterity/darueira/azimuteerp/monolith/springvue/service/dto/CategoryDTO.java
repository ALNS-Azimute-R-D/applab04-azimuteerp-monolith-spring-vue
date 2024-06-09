package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Category} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoryDTO implements Serializable {

    private Long id;

    @Size(max = 30)
    private String acronym;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String handlerClazzName;

    private CategoryDTO categoryParent;

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

    public String getHandlerClazzName() {
        return handlerClazzName;
    }

    public void setHandlerClazzName(String handlerClazzName) {
        this.handlerClazzName = handlerClazzName;
    }

    public CategoryDTO getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(CategoryDTO categoryParent) {
        this.categoryParent = categoryParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", handlerClazzName='" + getHandlerClazzName() + "'" +
            ", categoryParent=" + getCategoryParent() +
            "}";
    }
}
