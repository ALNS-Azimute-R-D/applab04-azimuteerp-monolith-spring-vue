package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMemberRoleDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMembershipDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationRoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationMemberRole} and its DTO {@link OrganizationMemberRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMemberRoleMapper extends EntityMapper<OrganizationMemberRoleDTO, OrganizationMemberRole> {
    @Mapping(target = "organizationMembership", source = "organizationMembership", qualifiedByName = "organizationMembershipId")
    @Mapping(target = "organizationRole", source = "organizationRole", qualifiedByName = "organizationRoleId")
    OrganizationMemberRoleDTO toDto(OrganizationMemberRole s);

    @Named("organizationMembershipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationMembershipDTO toDtoOrganizationMembershipId(OrganizationMembership organizationMembership);

    @Named("organizationRoleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationRoleDTO toDtoOrganizationRoleId(OrganizationRole organizationRole);
}
