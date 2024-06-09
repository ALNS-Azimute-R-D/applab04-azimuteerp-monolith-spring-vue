package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfActivityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeOfActivity} and its DTO {@link TypeOfActivityDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeOfActivityMapper extends EntityMapper<TypeOfActivityDTO, TypeOfActivity> {}
