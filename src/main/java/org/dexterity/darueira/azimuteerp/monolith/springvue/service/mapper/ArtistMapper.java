package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtistDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtisticGenreDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtistDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtmediaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artist} and its DTO {@link ArtistDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtistMapper extends EntityMapper<ArtistDTO, Artist> {
    @Mapping(target = "typeOfArtmedia", source = "typeOfArtmedia", qualifiedByName = "typeOfArtmediaAcronym")
    @Mapping(target = "typeOfArtist", source = "typeOfArtist", qualifiedByName = "typeOfArtistAcronym")
    @Mapping(target = "artistAggregator", source = "artistAggregator", qualifiedByName = "artistAcronym")
    @Mapping(target = "artists", source = "artists", qualifiedByName = "artisticGenreIdSet")
    ArtistDTO toDto(Artist s);

    @Mapping(target = "removeArtist", ignore = true)
    Artist toEntity(ArtistDTO artistDTO);

    @Named("typeOfArtmediaAcronym")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "acronym", source = "acronym")
    TypeOfArtmediaDTO toDtoTypeOfArtmediaAcronym(TypeOfArtmedia typeOfArtmedia);

    @Named("typeOfArtistAcronym")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "acronym", source = "acronym")
    TypeOfArtistDTO toDtoTypeOfArtistAcronym(TypeOfArtist typeOfArtist);

    @Named("artistAcronym")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "acronym", source = "acronym")
    ArtistDTO toDtoArtistAcronym(Artist artist);

    @Named("artisticGenreId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtisticGenreDTO toDtoArtisticGenreId(ArtisticGenre artisticGenre);

    @Named("artisticGenreIdSet")
    default Set<ArtisticGenreDTO> toDtoArtisticGenreIdSet(Set<ArtisticGenre> artisticGenre) {
        return artisticGenre.stream().map(this::toDtoArtisticGenreId).collect(Collectors.toSet());
    }
}
