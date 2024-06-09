package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationAttribute entity.
 */
@Repository
public interface OrganizationAttributeRepository extends JpaRepository<OrganizationAttribute, Long> {
    default Optional<OrganizationAttribute> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<OrganizationAttribute> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<OrganizationAttribute> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select organizationAttribute from OrganizationAttribute organizationAttribute left join fetch organizationAttribute.organization",
        countQuery = "select count(organizationAttribute) from OrganizationAttribute organizationAttribute"
    )
    Page<OrganizationAttribute> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select organizationAttribute from OrganizationAttribute organizationAttribute left join fetch organizationAttribute.organization"
    )
    List<OrganizationAttribute> findAllWithToOneRelationships();

    @Query(
        "select organizationAttribute from OrganizationAttribute organizationAttribute left join fetch organizationAttribute.organization where organizationAttribute.id =:id"
    )
    Optional<OrganizationAttribute> findOneWithToOneRelationships(@Param("id") Long id);
}
