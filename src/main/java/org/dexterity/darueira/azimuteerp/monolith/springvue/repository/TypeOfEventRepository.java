package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypeOfEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeOfEventRepository extends JpaRepository<TypeOfEvent, Long> {}
