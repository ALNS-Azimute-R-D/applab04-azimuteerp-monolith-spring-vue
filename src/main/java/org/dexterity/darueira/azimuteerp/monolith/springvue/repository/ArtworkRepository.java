package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artwork entity.
 */
@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    default Optional<Artwork> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Artwork> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Artwork> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select artwork from Artwork artwork left join fetch artwork.typeOfArtmedia left join fetch artwork.artworkAggregator",
        countQuery = "select count(artwork) from Artwork artwork"
    )
    Page<Artwork> findAllWithToOneRelationships(Pageable pageable);

    @Query("select artwork from Artwork artwork left join fetch artwork.typeOfArtmedia left join fetch artwork.artworkAggregator")
    List<Artwork> findAllWithToOneRelationships();

    @Query(
        "select artwork from Artwork artwork left join fetch artwork.typeOfArtmedia left join fetch artwork.artworkAggregator where artwork.id =:id"
    )
    Optional<Artwork> findOneWithToOneRelationships(@Param("id") Long id);
}
