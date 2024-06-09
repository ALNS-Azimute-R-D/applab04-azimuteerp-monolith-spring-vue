package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmp;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetTypeDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.RawAssetProcTmpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RawAssetProcTmp} and its DTO {@link RawAssetProcTmpDTO}.
 */
@Mapper(componentModel = "spring")
public interface RawAssetProcTmpMapper extends EntityMapper<RawAssetProcTmpDTO, RawAssetProcTmp> {
    @Mapping(target = "assetType", source = "assetType", qualifiedByName = "assetTypeName")
    RawAssetProcTmpDTO toDto(RawAssetProcTmp s);

    @Named("assetTypeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AssetTypeDTO toDtoAssetTypeName(AssetType assetType);
}
