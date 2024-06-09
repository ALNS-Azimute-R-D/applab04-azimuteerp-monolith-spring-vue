package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScheduledActivity entity.
 *
 * When extending this class, extend ScheduledActivityRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ScheduledActivityRepository
    extends ScheduledActivityRepositoryWithBagRelationships, JpaRepository<ScheduledActivity, Long> {
    default Optional<ScheduledActivity> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<ScheduledActivity> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<ScheduledActivity> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select scheduledActivity from ScheduledActivity scheduledActivity left join fetch scheduledActivity.program left join fetch scheduledActivity.activity left join fetch scheduledActivity.responsiblePerson",
        countQuery = "select count(scheduledActivity) from ScheduledActivity scheduledActivity"
    )
    Page<ScheduledActivity> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select scheduledActivity from ScheduledActivity scheduledActivity left join fetch scheduledActivity.program left join fetch scheduledActivity.activity left join fetch scheduledActivity.responsiblePerson"
    )
    List<ScheduledActivity> findAllWithToOneRelationships();

    @Query(
        "select scheduledActivity from ScheduledActivity scheduledActivity left join fetch scheduledActivity.program left join fetch scheduledActivity.activity left join fetch scheduledActivity.responsiblePerson where scheduledActivity.id =:id"
    )
    Optional<ScheduledActivity> findOneWithToOneRelationships(@Param("id") Long id);
}
