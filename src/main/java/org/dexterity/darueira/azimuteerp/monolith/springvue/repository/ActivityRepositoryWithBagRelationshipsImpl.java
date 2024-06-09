package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ActivityRepositoryWithBagRelationshipsImpl implements ActivityRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String ACTIVITIES_PARAMETER = "activities";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Activity> fetchBagRelationships(Optional<Activity> activity) {
        return activity.map(this::fetchAssetCollections);
    }

    @Override
    public Page<Activity> fetchBagRelationships(Page<Activity> activities) {
        return new PageImpl<>(fetchBagRelationships(activities.getContent()), activities.getPageable(), activities.getTotalElements());
    }

    @Override
    public List<Activity> fetchBagRelationships(List<Activity> activities) {
        return Optional.of(activities).map(this::fetchAssetCollections).orElse(Collections.emptyList());
    }

    Activity fetchAssetCollections(Activity result) {
        return entityManager
            .createQuery(
                "select activity from Activity activity left join fetch activity.assetCollections where activity.id = :id",
                Activity.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Activity> fetchAssetCollections(List<Activity> activities) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, activities.size()).forEach(index -> order.put(activities.get(index).getId(), index));
        List<Activity> result = entityManager
            .createQuery(
                "select activity from Activity activity left join fetch activity.assetCollections where activity in :activities",
                Activity.class
            )
            .setParameter(ACTIVITIES_PARAMETER, activities)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
