package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventAttendee entity.
 */
@Repository
public interface EventAttendeeRepository extends JpaRepository<EventAttendee, Long> {
    default Optional<EventAttendee> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EventAttendee> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EventAttendee> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select eventAttendee from EventAttendee eventAttendee left join fetch eventAttendee.attendeePerson left join fetch eventAttendee.event left join fetch eventAttendee.ticketPurchased",
        countQuery = "select count(eventAttendee) from EventAttendee eventAttendee"
    )
    Page<EventAttendee> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select eventAttendee from EventAttendee eventAttendee left join fetch eventAttendee.attendeePerson left join fetch eventAttendee.event left join fetch eventAttendee.ticketPurchased"
    )
    List<EventAttendee> findAllWithToOneRelationships();

    @Query(
        "select eventAttendee from EventAttendee eventAttendee left join fetch eventAttendee.attendeePerson left join fetch eventAttendee.event left join fetch eventAttendee.ticketPurchased where eventAttendee.id =:id"
    )
    Optional<EventAttendee> findOneWithToOneRelationships(@Param("id") Long id);
}
