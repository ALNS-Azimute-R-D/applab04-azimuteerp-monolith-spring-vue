package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkCast entity.
 */
@Repository
public interface ArtworkCastRepository extends JpaRepository<ArtworkCast, Long> {
    default Optional<ArtworkCast> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ArtworkCast> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ArtworkCast> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select artworkCast from ArtworkCast artworkCast left join fetch artworkCast.artwork left join fetch artworkCast.artist",
        countQuery = "select count(artworkCast) from ArtworkCast artworkCast"
    )
    Page<ArtworkCast> findAllWithToOneRelationships(Pageable pageable);

    @Query("select artworkCast from ArtworkCast artworkCast left join fetch artworkCast.artwork left join fetch artworkCast.artist")
    List<ArtworkCast> findAllWithToOneRelationships();

    @Query(
        "select artworkCast from ArtworkCast artworkCast left join fetch artworkCast.artwork left join fetch artworkCast.artist where artworkCast.id =:id"
    )
    Optional<ArtworkCast> findOneWithToOneRelationships(@Param("id") Long id);
}
