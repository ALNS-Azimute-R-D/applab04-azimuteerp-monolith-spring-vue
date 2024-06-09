package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationMembership entity.
 */
@Repository
public interface OrganizationMembershipRepository extends JpaRepository<OrganizationMembership, Long> {
    default Optional<OrganizationMembership> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<OrganizationMembership> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<OrganizationMembership> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select organizationMembership from OrganizationMembership organizationMembership left join fetch organizationMembership.organization left join fetch organizationMembership.person",
        countQuery = "select count(organizationMembership) from OrganizationMembership organizationMembership"
    )
    Page<OrganizationMembership> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select organizationMembership from OrganizationMembership organizationMembership left join fetch organizationMembership.organization left join fetch organizationMembership.person"
    )
    List<OrganizationMembership> findAllWithToOneRelationships();

    @Query(
        "select organizationMembership from OrganizationMembership organizationMembership left join fetch organizationMembership.organization left join fetch organizationMembership.person where organizationMembership.id =:id"
    )
    Optional<OrganizationMembership> findOneWithToOneRelationships(@Param("id") Long id);
}
