package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPerson;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfPersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeOfPerson} and its DTO {@link TypeOfPersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeOfPersonMapper extends EntityMapper<TypeOfPersonDTO, TypeOfPerson> {}
