package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.DistrictDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TownCityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "townCity", source = "townCity", qualifiedByName = "townCityName")
    DistrictDTO toDto(District s);

    @Named("townCityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TownCityDTO toDtoTownCityName(TownCity townCity);
}
