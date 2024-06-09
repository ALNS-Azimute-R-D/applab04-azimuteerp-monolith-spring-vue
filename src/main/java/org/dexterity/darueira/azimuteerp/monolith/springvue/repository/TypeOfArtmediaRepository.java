package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypeOfArtmedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeOfArtmediaRepository extends JpaRepository<TypeOfArtmedia, Long> {}
