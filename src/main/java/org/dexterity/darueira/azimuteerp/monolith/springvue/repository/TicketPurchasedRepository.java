package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TicketPurchased entity.
 */
@Repository
public interface TicketPurchasedRepository extends JpaRepository<TicketPurchased, Long> {
    default Optional<TicketPurchased> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TicketPurchased> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TicketPurchased> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select ticketPurchased from TicketPurchased ticketPurchased left join fetch ticketPurchased.event",
        countQuery = "select count(ticketPurchased) from TicketPurchased ticketPurchased"
    )
    Page<TicketPurchased> findAllWithToOneRelationships(Pageable pageable);

    @Query("select ticketPurchased from TicketPurchased ticketPurchased left join fetch ticketPurchased.event")
    List<TicketPurchased> findAllWithToOneRelationships();

    @Query(
        "select ticketPurchased from TicketPurchased ticketPurchased left join fetch ticketPurchased.event where ticketPurchased.id =:id"
    )
    Optional<TicketPurchased> findOneWithToOneRelationships(@Param("id") Long id);
}
