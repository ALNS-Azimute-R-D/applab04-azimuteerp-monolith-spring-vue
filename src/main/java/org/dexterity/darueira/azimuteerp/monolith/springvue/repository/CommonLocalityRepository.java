package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommonLocality entity.
 */
@Repository
public interface CommonLocalityRepository extends JpaRepository<CommonLocality, Long> {
    default Optional<CommonLocality> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CommonLocality> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CommonLocality> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select commonLocality from CommonLocality commonLocality left join fetch commonLocality.district",
        countQuery = "select count(commonLocality) from CommonLocality commonLocality"
    )
    Page<CommonLocality> findAllWithToOneRelationships(Pageable pageable);

    @Query("select commonLocality from CommonLocality commonLocality left join fetch commonLocality.district")
    List<CommonLocality> findAllWithToOneRelationships();

    @Query("select commonLocality from CommonLocality commonLocality left join fetch commonLocality.district where commonLocality.id =:id")
    Optional<CommonLocality> findOneWithToOneRelationships(@Param("id") Long id);
}
