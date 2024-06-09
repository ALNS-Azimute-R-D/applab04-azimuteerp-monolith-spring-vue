package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationMemberRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationMemberRoleRepository extends JpaRepository<OrganizationMemberRole, Long> {}
