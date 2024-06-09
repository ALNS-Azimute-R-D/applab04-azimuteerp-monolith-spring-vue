package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Country;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Province;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CountryDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProvinceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Province} and its DTO {@link ProvinceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProvinceMapper extends EntityMapper<ProvinceDTO, Province> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryName")
    ProvinceDTO toDto(Province s);

    @Named("countryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountryDTO toDtoCountryName(Country country);
}
