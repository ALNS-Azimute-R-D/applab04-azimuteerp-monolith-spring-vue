package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Organization entity.
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    default Optional<Organization> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Organization> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Organization> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select organization from Organization organization left join fetch organization.tenant left join fetch organization.typeOfOrganization left join fetch organization.organizationParent",
        countQuery = "select count(organization) from Organization organization"
    )
    Page<Organization> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select organization from Organization organization left join fetch organization.tenant left join fetch organization.typeOfOrganization left join fetch organization.organizationParent"
    )
    List<Organization> findAllWithToOneRelationships();

    @Query(
        "select organization from Organization organization left join fetch organization.tenant left join fetch organization.typeOfOrganization left join fetch organization.organizationParent where organization.id =:id"
    )
    Optional<Organization> findOneWithToOneRelationships(@Param("id") Long id);
}
