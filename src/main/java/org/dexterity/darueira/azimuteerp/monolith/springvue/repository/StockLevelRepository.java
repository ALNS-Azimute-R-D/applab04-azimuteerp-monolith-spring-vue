package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StockLevel entity.
 */
@Repository
public interface StockLevelRepository extends JpaRepository<StockLevel, Long> {
    default Optional<StockLevel> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<StockLevel> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<StockLevel> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select stockLevel from StockLevel stockLevel left join fetch stockLevel.warehouse left join fetch stockLevel.product",
        countQuery = "select count(stockLevel) from StockLevel stockLevel"
    )
    Page<StockLevel> findAllWithToOneRelationships(Pageable pageable);

    @Query("select stockLevel from StockLevel stockLevel left join fetch stockLevel.warehouse left join fetch stockLevel.product")
    List<StockLevel> findAllWithToOneRelationships();

    @Query(
        "select stockLevel from StockLevel stockLevel left join fetch stockLevel.warehouse left join fetch stockLevel.product where stockLevel.id =:id"
    )
    Optional<StockLevel> findOneWithToOneRelationships(@Param("id") Long id);
}
