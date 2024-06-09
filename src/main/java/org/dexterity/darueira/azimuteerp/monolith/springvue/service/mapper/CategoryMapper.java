package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Category;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Mapping(target = "categoryParent", source = "categoryParent", qualifiedByName = "categoryAcronym")
    CategoryDTO toDto(Category s);

    @Named("categoryAcronym")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "acronym", source = "acronym")
    CategoryDTO toDtoCategoryAcronym(Category category);
}
