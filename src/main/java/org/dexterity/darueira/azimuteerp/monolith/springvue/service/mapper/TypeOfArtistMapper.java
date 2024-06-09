package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtistDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeOfArtist} and its DTO {@link TypeOfArtistDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeOfArtistMapper extends EntityMapper<TypeOfArtistDTO, TypeOfArtist> {}
