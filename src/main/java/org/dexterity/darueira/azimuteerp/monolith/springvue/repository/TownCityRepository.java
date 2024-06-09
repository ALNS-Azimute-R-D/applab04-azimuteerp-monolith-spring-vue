package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TownCity entity.
 */
@Repository
public interface TownCityRepository extends JpaRepository<TownCity, Long> {
    default Optional<TownCity> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TownCity> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TownCity> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select townCity from TownCity townCity left join fetch townCity.province",
        countQuery = "select count(townCity) from TownCity townCity"
    )
    Page<TownCity> findAllWithToOneRelationships(Pageable pageable);

    @Query("select townCity from TownCity townCity left join fetch townCity.province")
    List<TownCity> findAllWithToOneRelationships();

    @Query("select townCity from TownCity townCity left join fetch townCity.province where townCity.id =:id")
    Optional<TownCity> findOneWithToOneRelationships(@Param("id") Long id);
}
