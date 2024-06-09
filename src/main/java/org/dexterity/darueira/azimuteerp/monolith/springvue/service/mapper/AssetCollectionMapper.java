package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Article;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ActivityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArticleDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetCollectionDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.EventDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ScheduledActivityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetCollection} and its DTO {@link AssetCollectionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssetCollectionMapper extends EntityMapper<AssetCollectionDTO, AssetCollection> {
    @Mapping(target = "assets", source = "assets", qualifiedByName = "assetIdSet")
    @Mapping(target = "articles", source = "articles", qualifiedByName = "articleIdSet")
    @Mapping(target = "events", source = "events", qualifiedByName = "eventIdSet")
    @Mapping(target = "activities", source = "activities", qualifiedByName = "activityIdSet")
    @Mapping(target = "scheduledActivities", source = "scheduledActivities", qualifiedByName = "scheduledActivityIdSet")
    AssetCollectionDTO toDto(AssetCollection s);

    @Mapping(target = "removeAsset", ignore = true)
    @Mapping(target = "articles", ignore = true)
    @Mapping(target = "removeArticle", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "removeEvent", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "removeActivity", ignore = true)
    @Mapping(target = "scheduledActivities", ignore = true)
    @Mapping(target = "removeScheduledActivity", ignore = true)
    AssetCollection toEntity(AssetCollectionDTO assetCollectionDTO);

    @Named("assetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssetDTO toDtoAssetId(Asset asset);

    @Named("assetIdSet")
    default Set<AssetDTO> toDtoAssetIdSet(Set<Asset> asset) {
        return asset.stream().map(this::toDtoAssetId).collect(Collectors.toSet());
    }

    @Named("articleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleDTO toDtoArticleId(Article article);

    @Named("articleIdSet")
    default Set<ArticleDTO> toDtoArticleIdSet(Set<Article> article) {
        return article.stream().map(this::toDtoArticleId).collect(Collectors.toSet());
    }

    @Named("eventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDTO toDtoEventId(Event event);

    @Named("eventIdSet")
    default Set<EventDTO> toDtoEventIdSet(Set<Event> event) {
        return event.stream().map(this::toDtoEventId).collect(Collectors.toSet());
    }

    @Named("activityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ActivityDTO toDtoActivityId(Activity activity);

    @Named("activityIdSet")
    default Set<ActivityDTO> toDtoActivityIdSet(Set<Activity> activity) {
        return activity.stream().map(this::toDtoActivityId).collect(Collectors.toSet());
    }

    @Named("scheduledActivityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScheduledActivityDTO toDtoScheduledActivityId(ScheduledActivity scheduledActivity);

    @Named("scheduledActivityIdSet")
    default Set<ScheduledActivityDTO> toDtoScheduledActivityIdSet(Set<ScheduledActivity> scheduledActivity) {
        return scheduledActivity.stream().map(this::toDtoScheduledActivityId).collect(Collectors.toSet());
    }
}
