package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.OrganizationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Organization.
 */
@Entity
@Table(name = "tb_organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "acronym", length = 20, nullable = false)
    private String acronym;

    @NotNull
    @Size(max = 15)
    @Column(name = "business_code", length = 15, nullable = false)
    private String businessCode;

    @Size(max = 30)
    @Column(name = "hierarchical_level", length = 30)
    private String hierarchicalLevel;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 512)
    @Column(name = "description", length = 512, nullable = false)
    private String description;

    @Size(max = 512)
    @Column(name = "business_handler_clazz", length = 512)
    private String businessHandlerClazz;

    @Size(max = 2048)
    @Column(name = "main_contact_person_details_json", length = 2048)
    private String mainContactPersonDetailsJSON;

    @Size(max = 4096)
    @Column(name = "technical_environments_details_json", length = 4096)
    private String technicalEnvironmentsDetailsJSON;

    @Size(max = 4096)
    @Column(name = "custom_attributes_details_json", length = 4096)
    private String customAttributesDetailsJSON;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "organization_status", nullable = false)
    private OrganizationStatusEnum organizationStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @Lob
    @Column(name = "logo_img")
    private byte[] logoImg;

    @Column(name = "logo_img_content_type")
    private String logoImgContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "organizationsLists" }, allowSetters = true)
    private Tenant tenant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "organizationsLists" }, allowSetters = true)
    private TypeOfOrganization typeOfOrganization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "tenant",
            "typeOfOrganization",
            "organizationParent",
            "organizationDomainsLists",
            "organizationAttributesLists",
            "businessUnitsLists",
            "childrenOrganizationsLists",
            "organizationRolesLists",
            "organizationMembershipsLists",
        },
        allowSetters = true
    )
    private Organization organizationParent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization" }, allowSetters = true)
    private Set<OrganizationDomain> organizationDomainsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization" }, allowSetters = true)
    private Set<OrganizationAttribute> organizationAttributesLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization", "businessUnitParent", "childrenBusinessUnitsLists" }, allowSetters = true)
    private Set<BusinessUnit> businessUnitsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizationParent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "tenant",
            "typeOfOrganization",
            "organizationParent",
            "organizationDomainsLists",
            "organizationAttributesLists",
            "businessUnitsLists",
            "childrenOrganizationsLists",
            "organizationRolesLists",
            "organizationMembershipsLists",
        },
        allowSetters = true
    )
    private Set<Organization> childrenOrganizationsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization", "organizationMemberRolesLists" }, allowSetters = true)
    private Set<OrganizationRole> organizationRolesLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organization", "person", "organizationMemberRolesLists" }, allowSetters = true)
    private Set<OrganizationMembership> organizationMembershipsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Organization acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getBusinessCode() {
        return this.businessCode;
    }

    public Organization businessCode(String businessCode) {
        this.setBusinessCode(businessCode);
        return this;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getHierarchicalLevel() {
        return this.hierarchicalLevel;
    }

    public Organization hierarchicalLevel(String hierarchicalLevel) {
        this.setHierarchicalLevel(hierarchicalLevel);
        return this;
    }

    public void setHierarchicalLevel(String hierarchicalLevel) {
        this.hierarchicalLevel = hierarchicalLevel;
    }

    public String getName() {
        return this.name;
    }

    public Organization name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Organization description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessHandlerClazz() {
        return this.businessHandlerClazz;
    }

    public Organization businessHandlerClazz(String businessHandlerClazz) {
        this.setBusinessHandlerClazz(businessHandlerClazz);
        return this;
    }

    public void setBusinessHandlerClazz(String businessHandlerClazz) {
        this.businessHandlerClazz = businessHandlerClazz;
    }

    public String getMainContactPersonDetailsJSON() {
        return this.mainContactPersonDetailsJSON;
    }

    public Organization mainContactPersonDetailsJSON(String mainContactPersonDetailsJSON) {
        this.setMainContactPersonDetailsJSON(mainContactPersonDetailsJSON);
        return this;
    }

    public void setMainContactPersonDetailsJSON(String mainContactPersonDetailsJSON) {
        this.mainContactPersonDetailsJSON = mainContactPersonDetailsJSON;
    }

    public String getTechnicalEnvironmentsDetailsJSON() {
        return this.technicalEnvironmentsDetailsJSON;
    }

    public Organization technicalEnvironmentsDetailsJSON(String technicalEnvironmentsDetailsJSON) {
        this.setTechnicalEnvironmentsDetailsJSON(technicalEnvironmentsDetailsJSON);
        return this;
    }

    public void setTechnicalEnvironmentsDetailsJSON(String technicalEnvironmentsDetailsJSON) {
        this.technicalEnvironmentsDetailsJSON = technicalEnvironmentsDetailsJSON;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public Organization customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public OrganizationStatusEnum getOrganizationStatus() {
        return this.organizationStatus;
    }

    public Organization organizationStatus(OrganizationStatusEnum organizationStatus) {
        this.setOrganizationStatus(organizationStatus);
        return this;
    }

    public void setOrganizationStatus(OrganizationStatusEnum organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Organization activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public byte[] getLogoImg() {
        return this.logoImg;
    }

    public Organization logoImg(byte[] logoImg) {
        this.setLogoImg(logoImg);
        return this;
    }

    public void setLogoImg(byte[] logoImg) {
        this.logoImg = logoImg;
    }

    public String getLogoImgContentType() {
        return this.logoImgContentType;
    }

    public Organization logoImgContentType(String logoImgContentType) {
        this.logoImgContentType = logoImgContentType;
        return this;
    }

    public void setLogoImgContentType(String logoImgContentType) {
        this.logoImgContentType = logoImgContentType;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Organization tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    public TypeOfOrganization getTypeOfOrganization() {
        return this.typeOfOrganization;
    }

    public void setTypeOfOrganization(TypeOfOrganization typeOfOrganization) {
        this.typeOfOrganization = typeOfOrganization;
    }

    public Organization typeOfOrganization(TypeOfOrganization typeOfOrganization) {
        this.setTypeOfOrganization(typeOfOrganization);
        return this;
    }

    public Organization getOrganizationParent() {
        return this.organizationParent;
    }

    public void setOrganizationParent(Organization organization) {
        this.organizationParent = organization;
    }

    public Organization organizationParent(Organization organization) {
        this.setOrganizationParent(organization);
        return this;
    }

    public Set<OrganizationDomain> getOrganizationDomainsLists() {
        return this.organizationDomainsLists;
    }

    public void setOrganizationDomainsLists(Set<OrganizationDomain> organizationDomains) {
        if (this.organizationDomainsLists != null) {
            this.organizationDomainsLists.forEach(i -> i.setOrganization(null));
        }
        if (organizationDomains != null) {
            organizationDomains.forEach(i -> i.setOrganization(this));
        }
        this.organizationDomainsLists = organizationDomains;
    }

    public Organization organizationDomainsLists(Set<OrganizationDomain> organizationDomains) {
        this.setOrganizationDomainsLists(organizationDomains);
        return this;
    }

    public Organization addOrganizationDomainsList(OrganizationDomain organizationDomain) {
        this.organizationDomainsLists.add(organizationDomain);
        organizationDomain.setOrganization(this);
        return this;
    }

    public Organization removeOrganizationDomainsList(OrganizationDomain organizationDomain) {
        this.organizationDomainsLists.remove(organizationDomain);
        organizationDomain.setOrganization(null);
        return this;
    }

    public Set<OrganizationAttribute> getOrganizationAttributesLists() {
        return this.organizationAttributesLists;
    }

    public void setOrganizationAttributesLists(Set<OrganizationAttribute> organizationAttributes) {
        if (this.organizationAttributesLists != null) {
            this.organizationAttributesLists.forEach(i -> i.setOrganization(null));
        }
        if (organizationAttributes != null) {
            organizationAttributes.forEach(i -> i.setOrganization(this));
        }
        this.organizationAttributesLists = organizationAttributes;
    }

    public Organization organizationAttributesLists(Set<OrganizationAttribute> organizationAttributes) {
        this.setOrganizationAttributesLists(organizationAttributes);
        return this;
    }

    public Organization addOrganizationAttributesList(OrganizationAttribute organizationAttribute) {
        this.organizationAttributesLists.add(organizationAttribute);
        organizationAttribute.setOrganization(this);
        return this;
    }

    public Organization removeOrganizationAttributesList(OrganizationAttribute organizationAttribute) {
        this.organizationAttributesLists.remove(organizationAttribute);
        organizationAttribute.setOrganization(null);
        return this;
    }

    public Set<BusinessUnit> getBusinessUnitsLists() {
        return this.businessUnitsLists;
    }

    public void setBusinessUnitsLists(Set<BusinessUnit> businessUnits) {
        if (this.businessUnitsLists != null) {
            this.businessUnitsLists.forEach(i -> i.setOrganization(null));
        }
        if (businessUnits != null) {
            businessUnits.forEach(i -> i.setOrganization(this));
        }
        this.businessUnitsLists = businessUnits;
    }

    public Organization businessUnitsLists(Set<BusinessUnit> businessUnits) {
        this.setBusinessUnitsLists(businessUnits);
        return this;
    }

    public Organization addBusinessUnitsList(BusinessUnit businessUnit) {
        this.businessUnitsLists.add(businessUnit);
        businessUnit.setOrganization(this);
        return this;
    }

    public Organization removeBusinessUnitsList(BusinessUnit businessUnit) {
        this.businessUnitsLists.remove(businessUnit);
        businessUnit.setOrganization(null);
        return this;
    }

    public Set<Organization> getChildrenOrganizationsLists() {
        return this.childrenOrganizationsLists;
    }

    public void setChildrenOrganizationsLists(Set<Organization> organizations) {
        if (this.childrenOrganizationsLists != null) {
            this.childrenOrganizationsLists.forEach(i -> i.setOrganizationParent(null));
        }
        if (organizations != null) {
            organizations.forEach(i -> i.setOrganizationParent(this));
        }
        this.childrenOrganizationsLists = organizations;
    }

    public Organization childrenOrganizationsLists(Set<Organization> organizations) {
        this.setChildrenOrganizationsLists(organizations);
        return this;
    }

    public Organization addChildrenOrganizationsList(Organization organization) {
        this.childrenOrganizationsLists.add(organization);
        organization.setOrganizationParent(this);
        return this;
    }

    public Organization removeChildrenOrganizationsList(Organization organization) {
        this.childrenOrganizationsLists.remove(organization);
        organization.setOrganizationParent(null);
        return this;
    }

    public Set<OrganizationRole> getOrganizationRolesLists() {
        return this.organizationRolesLists;
    }

    public void setOrganizationRolesLists(Set<OrganizationRole> organizationRoles) {
        if (this.organizationRolesLists != null) {
            this.organizationRolesLists.forEach(i -> i.setOrganization(null));
        }
        if (organizationRoles != null) {
            organizationRoles.forEach(i -> i.setOrganization(this));
        }
        this.organizationRolesLists = organizationRoles;
    }

    public Organization organizationRolesLists(Set<OrganizationRole> organizationRoles) {
        this.setOrganizationRolesLists(organizationRoles);
        return this;
    }

    public Organization addOrganizationRolesList(OrganizationRole organizationRole) {
        this.organizationRolesLists.add(organizationRole);
        organizationRole.setOrganization(this);
        return this;
    }

    public Organization removeOrganizationRolesList(OrganizationRole organizationRole) {
        this.organizationRolesLists.remove(organizationRole);
        organizationRole.setOrganization(null);
        return this;
    }

    public Set<OrganizationMembership> getOrganizationMembershipsLists() {
        return this.organizationMembershipsLists;
    }

    public void setOrganizationMembershipsLists(Set<OrganizationMembership> organizationMemberships) {
        if (this.organizationMembershipsLists != null) {
            this.organizationMembershipsLists.forEach(i -> i.setOrganization(null));
        }
        if (organizationMemberships != null) {
            organizationMemberships.forEach(i -> i.setOrganization(this));
        }
        this.organizationMembershipsLists = organizationMemberships;
    }

    public Organization organizationMembershipsLists(Set<OrganizationMembership> organizationMemberships) {
        this.setOrganizationMembershipsLists(organizationMemberships);
        return this;
    }

    public Organization addOrganizationMembershipsList(OrganizationMembership organizationMembership) {
        this.organizationMembershipsLists.add(organizationMembership);
        organizationMembership.setOrganization(this);
        return this;
    }

    public Organization removeOrganizationMembershipsList(OrganizationMembership organizationMembership) {
        this.organizationMembershipsLists.remove(organizationMembership);
        organizationMembership.setOrganization(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return getId() != null && getId().equals(((Organization) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", businessCode='" + getBusinessCode() + "'" +
            ", hierarchicalLevel='" + getHierarchicalLevel() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", businessHandlerClazz='" + getBusinessHandlerClazz() + "'" +
            ", mainContactPersonDetailsJSON='" + getMainContactPersonDetailsJSON() + "'" +
            ", technicalEnvironmentsDetailsJSON='" + getTechnicalEnvironmentsDetailsJSON() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            ", organizationStatus='" + getOrganizationStatus() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", logoImg='" + getLogoImg() + "'" +
            ", logoImgContentType='" + getLogoImgContentType() + "'" +
            "}";
    }
}
