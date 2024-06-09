package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Province;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProvinceDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TownCityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TownCity} and its DTO {@link TownCityDTO}.
 */
@Mapper(componentModel = "spring")
public interface TownCityMapper extends EntityMapper<TownCityDTO, TownCity> {
    @Mapping(target = "province", source = "province", qualifiedByName = "provinceName")
    TownCityDTO toDto(TownCity s);

    @Named("provinceName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProvinceDTO toDtoProvinceName(Province province);
}
