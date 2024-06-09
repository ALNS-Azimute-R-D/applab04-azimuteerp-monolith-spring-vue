package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RawAssetProcTmp entity.
 */
@Repository
public interface RawAssetProcTmpRepository extends JpaRepository<RawAssetProcTmp, Long> {
    default Optional<RawAssetProcTmp> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<RawAssetProcTmp> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<RawAssetProcTmp> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select rawAssetProcTmp from RawAssetProcTmp rawAssetProcTmp left join fetch rawAssetProcTmp.assetType",
        countQuery = "select count(rawAssetProcTmp) from RawAssetProcTmp rawAssetProcTmp"
    )
    Page<RawAssetProcTmp> findAllWithToOneRelationships(Pageable pageable);

    @Query("select rawAssetProcTmp from RawAssetProcTmp rawAssetProcTmp left join fetch rawAssetProcTmp.assetType")
    List<RawAssetProcTmp> findAllWithToOneRelationships();

    @Query(
        "select rawAssetProcTmp from RawAssetProcTmp rawAssetProcTmp left join fetch rawAssetProcTmp.assetType where rawAssetProcTmp.id =:id"
    )
    Optional<RawAssetProcTmp> findOneWithToOneRelationships(@Param("id") Long id);
}
