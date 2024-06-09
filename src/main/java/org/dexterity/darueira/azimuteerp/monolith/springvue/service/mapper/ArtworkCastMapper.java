package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtistDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtworkCastDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtworkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkCast} and its DTO {@link ArtworkCastDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkCastMapper extends EntityMapper<ArtworkCastDTO, ArtworkCast> {
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "artworkArtworkTitle")
    @Mapping(target = "artist", source = "artist", qualifiedByName = "artistPublicName")
    ArtworkCastDTO toDto(ArtworkCast s);

    @Named("artworkArtworkTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "artworkTitle", source = "artworkTitle")
    ArtworkDTO toDtoArtworkArtworkTitle(Artwork artwork);

    @Named("artistPublicName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "publicName", source = "publicName")
    ArtistDTO toDtoArtistPublicName(Artist artist);
}
