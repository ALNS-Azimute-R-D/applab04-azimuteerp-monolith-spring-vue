package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Activity entity.
 *
 * When extending this class, extend ActivityRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ActivityRepository extends ActivityRepositoryWithBagRelationships, JpaRepository<Activity, Long> {
    default Optional<Activity> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Activity> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Activity> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select activity from Activity activity left join fetch activity.typeOfActivity left join fetch activity.createdByUser",
        countQuery = "select count(activity) from Activity activity"
    )
    Page<Activity> findAllWithToOneRelationships(Pageable pageable);

    @Query("select activity from Activity activity left join fetch activity.typeOfActivity left join fetch activity.createdByUser")
    List<Activity> findAllWithToOneRelationships();

    @Query(
        "select activity from Activity activity left join fetch activity.typeOfActivity left join fetch activity.createdByUser where activity.id =:id"
    )
    Optional<Activity> findOneWithToOneRelationships(@Param("id") Long id);
}
