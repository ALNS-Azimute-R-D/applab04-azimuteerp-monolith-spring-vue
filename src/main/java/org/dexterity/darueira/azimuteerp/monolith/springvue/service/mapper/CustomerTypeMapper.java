package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerType;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CustomerTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerType} and its DTO {@link CustomerTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerTypeMapper extends EntityMapper<CustomerTypeDTO, CustomerType> {}
