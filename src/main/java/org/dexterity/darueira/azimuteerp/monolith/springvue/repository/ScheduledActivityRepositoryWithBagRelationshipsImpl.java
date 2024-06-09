package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ScheduledActivityRepositoryWithBagRelationshipsImpl implements ScheduledActivityRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String SCHEDULEDACTIVITIES_PARAMETER = "scheduledActivities";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ScheduledActivity> fetchBagRelationships(Optional<ScheduledActivity> scheduledActivity) {
        return scheduledActivity.map(this::fetchAssetCollections);
    }

    @Override
    public Page<ScheduledActivity> fetchBagRelationships(Page<ScheduledActivity> scheduledActivities) {
        return new PageImpl<>(
            fetchBagRelationships(scheduledActivities.getContent()),
            scheduledActivities.getPageable(),
            scheduledActivities.getTotalElements()
        );
    }

    @Override
    public List<ScheduledActivity> fetchBagRelationships(List<ScheduledActivity> scheduledActivities) {
        return Optional.of(scheduledActivities).map(this::fetchAssetCollections).orElse(Collections.emptyList());
    }

    ScheduledActivity fetchAssetCollections(ScheduledActivity result) {
        return entityManager
            .createQuery(
                "select scheduledActivity from ScheduledActivity scheduledActivity left join fetch scheduledActivity.assetCollections where scheduledActivity.id = :id",
                ScheduledActivity.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<ScheduledActivity> fetchAssetCollections(List<ScheduledActivity> scheduledActivities) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, scheduledActivities.size()).forEach(index -> order.put(scheduledActivities.get(index).getId(), index));
        List<ScheduledActivity> result = entityManager
            .createQuery(
                "select scheduledActivity from ScheduledActivity scheduledActivity left join fetch scheduledActivity.assetCollections where scheduledActivity in :scheduledActivities",
                ScheduledActivity.class
            )
            .setParameter(SCHEDULEDACTIVITIES_PARAMETER, scheduledActivities)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
