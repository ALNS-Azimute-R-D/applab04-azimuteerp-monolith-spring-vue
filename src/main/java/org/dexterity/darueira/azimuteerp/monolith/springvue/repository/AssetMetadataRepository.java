package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetMetadata entity.
 */
@Repository
public interface AssetMetadataRepository extends JpaRepository<AssetMetadata, Long> {
    default Optional<AssetMetadata> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AssetMetadata> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AssetMetadata> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select assetMetadata from AssetMetadata assetMetadata left join fetch assetMetadata.asset",
        countQuery = "select count(assetMetadata) from AssetMetadata assetMetadata"
    )
    Page<AssetMetadata> findAllWithToOneRelationships(Pageable pageable);

    @Query("select assetMetadata from AssetMetadata assetMetadata left join fetch assetMetadata.asset")
    List<AssetMetadata> findAllWithToOneRelationships();

    @Query("select assetMetadata from AssetMetadata assetMetadata left join fetch assetMetadata.asset where assetMetadata.id =:id")
    Optional<AssetMetadata> findOneWithToOneRelationships(@Param("id") Long id);
}
