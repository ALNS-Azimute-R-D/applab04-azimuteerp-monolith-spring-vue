package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationRole entity.
 */
@Repository
public interface OrganizationRoleRepository extends JpaRepository<OrganizationRole, Long> {
    default Optional<OrganizationRole> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<OrganizationRole> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<OrganizationRole> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select organizationRole from OrganizationRole organizationRole left join fetch organizationRole.organization",
        countQuery = "select count(organizationRole) from OrganizationRole organizationRole"
    )
    Page<OrganizationRole> findAllWithToOneRelationships(Pageable pageable);

    @Query("select organizationRole from OrganizationRole organizationRole left join fetch organizationRole.organization")
    List<OrganizationRole> findAllWithToOneRelationships();

    @Query(
        "select organizationRole from OrganizationRole organizationRole left join fetch organizationRole.organization where organizationRole.id =:id"
    )
    Optional<OrganizationRole> findOneWithToOneRelationships(@Param("id") Long id);
}
