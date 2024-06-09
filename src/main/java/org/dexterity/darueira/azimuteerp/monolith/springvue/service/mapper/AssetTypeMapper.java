package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetType} and its DTO {@link AssetTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetTypeMapper extends EntityMapper<AssetTypeDTO, AssetType> {}
