package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypeOfArtist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeOfArtistRepository extends JpaRepository<TypeOfArtist, Long> {}
