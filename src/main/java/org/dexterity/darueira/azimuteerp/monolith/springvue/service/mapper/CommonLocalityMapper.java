package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CommonLocalityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.DistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonLocality} and its DTO {@link CommonLocalityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommonLocalityMapper extends EntityMapper<CommonLocalityDTO, CommonLocality> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtName")
    CommonLocalityDTO toDto(CommonLocality s);

    @Named("districtName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DistrictDTO toDtoDistrictName(District district);
}
