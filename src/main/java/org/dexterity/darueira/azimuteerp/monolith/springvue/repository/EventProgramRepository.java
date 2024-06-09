package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventProgram entity.
 */
@Repository
public interface EventProgramRepository extends JpaRepository<EventProgram, Long> {
    default Optional<EventProgram> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EventProgram> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EventProgram> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select eventProgram from EventProgram eventProgram left join fetch eventProgram.event left join fetch eventProgram.program left join fetch eventProgram.responsiblePerson",
        countQuery = "select count(eventProgram) from EventProgram eventProgram"
    )
    Page<EventProgram> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select eventProgram from EventProgram eventProgram left join fetch eventProgram.event left join fetch eventProgram.program left join fetch eventProgram.responsiblePerson"
    )
    List<EventProgram> findAllWithToOneRelationships();

    @Query(
        "select eventProgram from EventProgram eventProgram left join fetch eventProgram.event left join fetch eventProgram.program left join fetch eventProgram.responsiblePerson where eventProgram.id =:id"
    )
    Optional<EventProgram> findOneWithToOneRelationships(@Param("id") Long id);
}
