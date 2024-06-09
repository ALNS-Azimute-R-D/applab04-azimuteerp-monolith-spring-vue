package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfEventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeOfEvent} and its DTO {@link TypeOfEventDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeOfEventMapper extends EntityMapper<TypeOfEventDTO, TypeOfEvent> {}
