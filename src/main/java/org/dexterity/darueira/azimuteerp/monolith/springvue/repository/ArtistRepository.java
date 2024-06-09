package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artist entity.
 *
 * When extending this class, extend ArtistRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ArtistRepository extends ArtistRepositoryWithBagRelationships, JpaRepository<Artist, Long> {
    default Optional<Artist> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Artist> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Artist> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select artist from Artist artist left join fetch artist.typeOfArtmedia left join fetch artist.typeOfArtist left join fetch artist.artistAggregator",
        countQuery = "select count(artist) from Artist artist"
    )
    Page<Artist> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select artist from Artist artist left join fetch artist.typeOfArtmedia left join fetch artist.typeOfArtist left join fetch artist.artistAggregator"
    )
    List<Artist> findAllWithToOneRelationships();

    @Query(
        "select artist from Artist artist left join fetch artist.typeOfArtmedia left join fetch artist.typeOfArtist left join fetch artist.artistAggregator where artist.id =:id"
    )
    Optional<Artist> findOneWithToOneRelationships(@Param("id") Long id);
}
