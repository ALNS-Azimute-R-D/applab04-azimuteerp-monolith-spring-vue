package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDomainDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationDomain} and its DTO {@link OrganizationDomainDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationDomainMapper extends EntityMapper<OrganizationDomainDTO, OrganizationDomain> {
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationName")
    OrganizationDomainDTO toDto(OrganizationDomain s);

    @Named("organizationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganizationDTO toDtoOrganizationName(Organization organization);
}
