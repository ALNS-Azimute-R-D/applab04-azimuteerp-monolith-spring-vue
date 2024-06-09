package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeOfOrganization} and its DTO {@link TypeOfOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeOfOrganizationMapper extends EntityMapper<TypeOfOrganizationDTO, TypeOfOrganization> {}
