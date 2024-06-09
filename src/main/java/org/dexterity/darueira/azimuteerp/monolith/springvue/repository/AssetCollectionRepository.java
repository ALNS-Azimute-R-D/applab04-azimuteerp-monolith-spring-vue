package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetCollection entity.
 *
 * When extending this class, extend AssetCollectionRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface AssetCollectionRepository extends AssetCollectionRepositoryWithBagRelationships, JpaRepository<AssetCollection, Long> {
    default Optional<AssetCollection> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<AssetCollection> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<AssetCollection> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
