package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Event entity.
 *
 * When extending this class, extend EventRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface EventRepository extends EventRepositoryWithBagRelationships, JpaRepository<Event, Long> {
    default Optional<Event> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Event> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Event> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select event from Event event left join fetch event.mainVenue left join fetch event.typeOfEvent left join fetch event.promoteurPerson",
        countQuery = "select count(event) from Event event"
    )
    Page<Event> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select event from Event event left join fetch event.mainVenue left join fetch event.typeOfEvent left join fetch event.promoteurPerson"
    )
    List<Event> findAllWithToOneRelationships();

    @Query(
        "select event from Event event left join fetch event.mainVenue left join fetch event.typeOfEvent left join fetch event.promoteurPerson where event.id =:id"
    )
    Optional<Event> findOneWithToOneRelationships(@Param("id") Long id);
}
