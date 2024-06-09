package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Program;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ActivityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetCollectionDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PersonDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProgramDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ScheduledActivityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScheduledActivity} and its DTO {@link ScheduledActivityDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScheduledActivityMapper extends EntityMapper<ScheduledActivityDTO, ScheduledActivity> {
    @Mapping(target = "program", source = "program", qualifiedByName = "programAcronym")
    @Mapping(target = "activity", source = "activity", qualifiedByName = "activityName")
    @Mapping(target = "responsiblePerson", source = "responsiblePerson", qualifiedByName = "personFullname")
    @Mapping(target = "assetCollections", source = "assetCollections", qualifiedByName = "assetCollectionIdSet")
    ScheduledActivityDTO toDto(ScheduledActivity s);

    @Mapping(target = "removeAssetCollection", ignore = true)
    ScheduledActivity toEntity(ScheduledActivityDTO scheduledActivityDTO);

    @Named("programAcronym")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "acronym", source = "acronym")
    ProgramDTO toDtoProgramAcronym(Program program);

    @Named("activityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ActivityDTO toDtoActivityName(Activity activity);

    @Named("personFullname")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullname", source = "fullname")
    PersonDTO toDtoPersonFullname(Person person);

    @Named("assetCollectionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssetCollectionDTO toDtoAssetCollectionId(AssetCollection assetCollection);

    @Named("assetCollectionIdSet")
    default Set<AssetCollectionDTO> toDtoAssetCollectionIdSet(Set<AssetCollection> assetCollection) {
        return assetCollection.stream().map(this::toDtoAssetCollectionId).collect(Collectors.toSet());
    }
}
