package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BusinessUnit entity.
 */
@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {
    default Optional<BusinessUnit> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BusinessUnit> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BusinessUnit> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select businessUnit from BusinessUnit businessUnit left join fetch businessUnit.organization left join fetch businessUnit.businessUnitParent",
        countQuery = "select count(businessUnit) from BusinessUnit businessUnit"
    )
    Page<BusinessUnit> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select businessUnit from BusinessUnit businessUnit left join fetch businessUnit.organization left join fetch businessUnit.businessUnitParent"
    )
    List<BusinessUnit> findAllWithToOneRelationships();

    @Query(
        "select businessUnit from BusinessUnit businessUnit left join fetch businessUnit.organization left join fetch businessUnit.businessUnitParent where businessUnit.id =:id"
    )
    Optional<BusinessUnit> findOneWithToOneRelationships(@Param("id") Long id);
}
