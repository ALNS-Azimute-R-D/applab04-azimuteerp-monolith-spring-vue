package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Tenant;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TenantDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantName")
    @Mapping(target = "typeOfOrganization", source = "typeOfOrganization", qualifiedByName = "typeOfOrganizationName")
    @Mapping(target = "organizationParent", source = "organizationParent", qualifiedByName = "organizationName")
    OrganizationDTO toDto(Organization s);

    @Named("tenantName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TenantDTO toDtoTenantName(Tenant tenant);

    @Named("typeOfOrganizationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TypeOfOrganizationDTO toDtoTypeOfOrganizationName(TypeOfOrganization typeOfOrganization);

    @Named("organizationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganizationDTO toDtoOrganizationName(Organization organization);
}
