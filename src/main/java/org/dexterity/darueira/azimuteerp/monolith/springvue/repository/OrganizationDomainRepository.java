package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationDomain entity.
 */
@Repository
public interface OrganizationDomainRepository extends JpaRepository<OrganizationDomain, Long> {
    default Optional<OrganizationDomain> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<OrganizationDomain> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<OrganizationDomain> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select organizationDomain from OrganizationDomain organizationDomain left join fetch organizationDomain.organization",
        countQuery = "select count(organizationDomain) from OrganizationDomain organizationDomain"
    )
    Page<OrganizationDomain> findAllWithToOneRelationships(Pageable pageable);

    @Query("select organizationDomain from OrganizationDomain organizationDomain left join fetch organizationDomain.organization")
    List<OrganizationDomain> findAllWithToOneRelationships();

    @Query(
        "select organizationDomain from OrganizationDomain organizationDomain left join fetch organizationDomain.organization where organizationDomain.id =:id"
    )
    Optional<OrganizationDomain> findOneWithToOneRelationships(@Param("id") Long id);
}
