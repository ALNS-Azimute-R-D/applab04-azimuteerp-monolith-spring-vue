import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Country = () => import('@/entities/country/country.vue');
const CountryUpdate = () => import('@/entities/country/country-update.vue');
const CountryDetails = () => import('@/entities/country/country-details.vue');

const Province = () => import('@/entities/province/province.vue');
const ProvinceUpdate = () => import('@/entities/province/province-update.vue');
const ProvinceDetails = () => import('@/entities/province/province-details.vue');

const TownCity = () => import('@/entities/town-city/town-city.vue');
const TownCityUpdate = () => import('@/entities/town-city/town-city-update.vue');
const TownCityDetails = () => import('@/entities/town-city/town-city-details.vue');

const District = () => import('@/entities/district/district.vue');
const DistrictUpdate = () => import('@/entities/district/district-update.vue');
const DistrictDetails = () => import('@/entities/district/district-details.vue');

const CommonLocality = () => import('@/entities/common-locality/common-locality.vue');
const CommonLocalityUpdate = () => import('@/entities/common-locality/common-locality-update.vue');
const CommonLocalityDetails = () => import('@/entities/common-locality/common-locality-details.vue');

const Tenant = () => import('@/entities/tenant/tenant.vue');
const TenantUpdate = () => import('@/entities/tenant/tenant-update.vue');
const TenantDetails = () => import('@/entities/tenant/tenant-details.vue');

const TypeOfOrganization = () => import('@/entities/type-of-organization/type-of-organization.vue');
const TypeOfOrganizationUpdate = () => import('@/entities/type-of-organization/type-of-organization-update.vue');
const TypeOfOrganizationDetails = () => import('@/entities/type-of-organization/type-of-organization-details.vue');

const Organization = () => import('@/entities/organization/organization.vue');
const OrganizationUpdate = () => import('@/entities/organization/organization-update.vue');
const OrganizationDetails = () => import('@/entities/organization/organization-details.vue');

const BusinessUnit = () => import('@/entities/business-unit/business-unit.vue');
const BusinessUnitUpdate = () => import('@/entities/business-unit/business-unit-update.vue');
const BusinessUnitDetails = () => import('@/entities/business-unit/business-unit-details.vue');

const OrganizationDomain = () => import('@/entities/organization-domain/organization-domain.vue');
const OrganizationDomainUpdate = () => import('@/entities/organization-domain/organization-domain-update.vue');
const OrganizationDomainDetails = () => import('@/entities/organization-domain/organization-domain-details.vue');

const OrganizationAttribute = () => import('@/entities/organization-attribute/organization-attribute.vue');
const OrganizationAttributeUpdate = () => import('@/entities/organization-attribute/organization-attribute-update.vue');
const OrganizationAttributeDetails = () => import('@/entities/organization-attribute/organization-attribute-details.vue');

const TypeOfPerson = () => import('@/entities/type-of-person/type-of-person.vue');
const TypeOfPersonUpdate = () => import('@/entities/type-of-person/type-of-person-update.vue');
const TypeOfPersonDetails = () => import('@/entities/type-of-person/type-of-person-details.vue');

const Person = () => import('@/entities/person/person.vue');
const PersonUpdate = () => import('@/entities/person/person-update.vue');
const PersonDetails = () => import('@/entities/person/person-details.vue');

const OrganizationRole = () => import('@/entities/organization-role/organization-role.vue');
const OrganizationRoleUpdate = () => import('@/entities/organization-role/organization-role-update.vue');
const OrganizationRoleDetails = () => import('@/entities/organization-role/organization-role-details.vue');

const OrganizationMembership = () => import('@/entities/organization-membership/organization-membership.vue');
const OrganizationMembershipUpdate = () => import('@/entities/organization-membership/organization-membership-update.vue');
const OrganizationMembershipDetails = () => import('@/entities/organization-membership/organization-membership-details.vue');

const OrganizationMemberRole = () => import('@/entities/organization-member-role/organization-member-role.vue');
const OrganizationMemberRoleUpdate = () => import('@/entities/organization-member-role/organization-member-role-update.vue');
const OrganizationMemberRoleDetails = () => import('@/entities/organization-member-role/organization-member-role-details.vue');

const AssetType = () => import('@/entities/asset-type/asset-type.vue');
const AssetTypeUpdate = () => import('@/entities/asset-type/asset-type-update.vue');
const AssetTypeDetails = () => import('@/entities/asset-type/asset-type-details.vue');

const RawAssetProcTmp = () => import('@/entities/raw-asset-proc-tmp/raw-asset-proc-tmp.vue');
const RawAssetProcTmpUpdate = () => import('@/entities/raw-asset-proc-tmp/raw-asset-proc-tmp-update.vue');
const RawAssetProcTmpDetails = () => import('@/entities/raw-asset-proc-tmp/raw-asset-proc-tmp-details.vue');

const Asset = () => import('@/entities/asset/asset.vue');
const AssetUpdate = () => import('@/entities/asset/asset-update.vue');
const AssetDetails = () => import('@/entities/asset/asset-details.vue');

const AssetMetadata = () => import('@/entities/asset-metadata/asset-metadata.vue');
const AssetMetadataUpdate = () => import('@/entities/asset-metadata/asset-metadata-update.vue');
const AssetMetadataDetails = () => import('@/entities/asset-metadata/asset-metadata-details.vue');

const AssetCollection = () => import('@/entities/asset-collection/asset-collection.vue');
const AssetCollectionUpdate = () => import('@/entities/asset-collection/asset-collection-update.vue');
const AssetCollectionDetails = () => import('@/entities/asset-collection/asset-collection-details.vue');

const Invoice = () => import('@/entities/invoice/invoice.vue');
const InvoiceUpdate = () => import('@/entities/invoice/invoice-update.vue');
const InvoiceDetails = () => import('@/entities/invoice/invoice-details.vue');

const PaymentGateway = () => import('@/entities/payment-gateway/payment-gateway.vue');
const PaymentGatewayUpdate = () => import('@/entities/payment-gateway/payment-gateway-update.vue');
const PaymentGatewayDetails = () => import('@/entities/payment-gateway/payment-gateway-details.vue');

const Payment = () => import('@/entities/payment/payment.vue');
const PaymentUpdate = () => import('@/entities/payment/payment-update.vue');
const PaymentDetails = () => import('@/entities/payment/payment-details.vue');

const Warehouse = () => import('@/entities/warehouse/warehouse.vue');
const WarehouseUpdate = () => import('@/entities/warehouse/warehouse-update.vue');
const WarehouseDetails = () => import('@/entities/warehouse/warehouse-details.vue');

const Supplier = () => import('@/entities/supplier/supplier.vue');
const SupplierUpdate = () => import('@/entities/supplier/supplier-update.vue');
const SupplierDetails = () => import('@/entities/supplier/supplier-details.vue');

const Brand = () => import('@/entities/brand/brand.vue');
const BrandUpdate = () => import('@/entities/brand/brand-update.vue');
const BrandDetails = () => import('@/entities/brand/brand-details.vue');

const Product = () => import('@/entities/product/product.vue');
const ProductUpdate = () => import('@/entities/product/product-update.vue');
const ProductDetails = () => import('@/entities/product/product-details.vue');

const InventoryTransaction = () => import('@/entities/inventory-transaction/inventory-transaction.vue');
const InventoryTransactionUpdate = () => import('@/entities/inventory-transaction/inventory-transaction-update.vue');
const InventoryTransactionDetails = () => import('@/entities/inventory-transaction/inventory-transaction-details.vue');

const StockLevel = () => import('@/entities/stock-level/stock-level.vue');
const StockLevelUpdate = () => import('@/entities/stock-level/stock-level-update.vue');
const StockLevelDetails = () => import('@/entities/stock-level/stock-level-details.vue');

const Customer = () => import('@/entities/customer/customer.vue');
const CustomerUpdate = () => import('@/entities/customer/customer-update.vue');
const CustomerDetails = () => import('@/entities/customer/customer-details.vue');

const CustomerType = () => import('@/entities/customer-type/customer-type.vue');
const CustomerTypeUpdate = () => import('@/entities/customer-type/customer-type-update.vue');
const CustomerTypeDetails = () => import('@/entities/customer-type/customer-type-details.vue');

const Category = () => import('@/entities/category/category.vue');
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
const CategoryDetails = () => import('@/entities/category/category-details.vue');

const Article = () => import('@/entities/article/article.vue');
const ArticleUpdate = () => import('@/entities/article/article-update.vue');
const ArticleDetails = () => import('@/entities/article/article-details.vue');

const Order = () => import('@/entities/order/order.vue');
const OrderUpdate = () => import('@/entities/order/order-update.vue');
const OrderDetails = () => import('@/entities/order/order-details.vue');

const OrderItem = () => import('@/entities/order-item/order-item.vue');
const OrderItemUpdate = () => import('@/entities/order-item/order-item-update.vue');
const OrderItemDetails = () => import('@/entities/order-item/order-item-details.vue');

const TypeOfArtmedia = () => import('@/entities/type-of-artmedia/type-of-artmedia.vue');
const TypeOfArtmediaUpdate = () => import('@/entities/type-of-artmedia/type-of-artmedia-update.vue');
const TypeOfArtmediaDetails = () => import('@/entities/type-of-artmedia/type-of-artmedia-details.vue');

const TypeOfArtist = () => import('@/entities/type-of-artist/type-of-artist.vue');
const TypeOfArtistUpdate = () => import('@/entities/type-of-artist/type-of-artist-update.vue');
const TypeOfArtistDetails = () => import('@/entities/type-of-artist/type-of-artist-details.vue');

const ArtisticGenre = () => import('@/entities/artistic-genre/artistic-genre.vue');
const ArtisticGenreUpdate = () => import('@/entities/artistic-genre/artistic-genre-update.vue');
const ArtisticGenreDetails = () => import('@/entities/artistic-genre/artistic-genre-details.vue');

const Artist = () => import('@/entities/artist/artist.vue');
const ArtistUpdate = () => import('@/entities/artist/artist-update.vue');
const ArtistDetails = () => import('@/entities/artist/artist-details.vue');

const Artwork = () => import('@/entities/artwork/artwork.vue');
const ArtworkUpdate = () => import('@/entities/artwork/artwork-update.vue');
const ArtworkDetails = () => import('@/entities/artwork/artwork-details.vue');

const ArtworkCast = () => import('@/entities/artwork-cast/artwork-cast.vue');
const ArtworkCastUpdate = () => import('@/entities/artwork-cast/artwork-cast-update.vue');
const ArtworkCastDetails = () => import('@/entities/artwork-cast/artwork-cast-details.vue');

const TypeOfEvent = () => import('@/entities/type-of-event/type-of-event.vue');
const TypeOfEventUpdate = () => import('@/entities/type-of-event/type-of-event-update.vue');
const TypeOfEventDetails = () => import('@/entities/type-of-event/type-of-event-details.vue');

const TypeOfVenue = () => import('@/entities/type-of-venue/type-of-venue.vue');
const TypeOfVenueUpdate = () => import('@/entities/type-of-venue/type-of-venue-update.vue');
const TypeOfVenueDetails = () => import('@/entities/type-of-venue/type-of-venue-details.vue');

const TypeOfActivity = () => import('@/entities/type-of-activity/type-of-activity.vue');
const TypeOfActivityUpdate = () => import('@/entities/type-of-activity/type-of-activity-update.vue');
const TypeOfActivityDetails = () => import('@/entities/type-of-activity/type-of-activity-details.vue');

const Venue = () => import('@/entities/venue/venue.vue');
const VenueUpdate = () => import('@/entities/venue/venue-update.vue');
const VenueDetails = () => import('@/entities/venue/venue-details.vue');

const Activity = () => import('@/entities/activity/activity.vue');
const ActivityUpdate = () => import('@/entities/activity/activity-update.vue');
const ActivityDetails = () => import('@/entities/activity/activity-details.vue');

const Event = () => import('@/entities/event/event.vue');
const EventUpdate = () => import('@/entities/event/event-update.vue');
const EventDetails = () => import('@/entities/event/event-details.vue');

const Program = () => import('@/entities/program/program.vue');
const ProgramUpdate = () => import('@/entities/program/program-update.vue');
const ProgramDetails = () => import('@/entities/program/program-details.vue');

const EventProgram = () => import('@/entities/event-program/event-program.vue');
const EventProgramUpdate = () => import('@/entities/event-program/event-program-update.vue');
const EventProgramDetails = () => import('@/entities/event-program/event-program-details.vue');

const ScheduledActivity = () => import('@/entities/scheduled-activity/scheduled-activity.vue');
const ScheduledActivityUpdate = () => import('@/entities/scheduled-activity/scheduled-activity-update.vue');
const ScheduledActivityDetails = () => import('@/entities/scheduled-activity/scheduled-activity-details.vue');

const EventAttendee = () => import('@/entities/event-attendee/event-attendee.vue');
const EventAttendeeUpdate = () => import('@/entities/event-attendee/event-attendee-update.vue');
const EventAttendeeDetails = () => import('@/entities/event-attendee/event-attendee-details.vue');

const TicketPurchased = () => import('@/entities/ticket-purchased/ticket-purchased.vue');
const TicketPurchasedUpdate = () => import('@/entities/ticket-purchased/ticket-purchased-update.vue');
const TicketPurchasedDetails = () => import('@/entities/ticket-purchased/ticket-purchased-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'country',
      name: 'Country',
      component: Country,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/new',
      name: 'CountryCreate',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/edit',
      name: 'CountryEdit',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/view',
      name: 'CountryView',
      component: CountryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province',
      name: 'Province',
      component: Province,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/new',
      name: 'ProvinceCreate',
      component: ProvinceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/:provinceId/edit',
      name: 'ProvinceEdit',
      component: ProvinceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'province/:provinceId/view',
      name: 'ProvinceView',
      component: ProvinceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'town-city',
      name: 'TownCity',
      component: TownCity,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'town-city/new',
      name: 'TownCityCreate',
      component: TownCityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'town-city/:townCityId/edit',
      name: 'TownCityEdit',
      component: TownCityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'town-city/:townCityId/view',
      name: 'TownCityView',
      component: TownCityDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district',
      name: 'District',
      component: District,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/new',
      name: 'DistrictCreate',
      component: DistrictUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/:districtId/edit',
      name: 'DistrictEdit',
      component: DistrictUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/:districtId/view',
      name: 'DistrictView',
      component: DistrictDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'common-locality',
      name: 'CommonLocality',
      component: CommonLocality,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'common-locality/new',
      name: 'CommonLocalityCreate',
      component: CommonLocalityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'common-locality/:commonLocalityId/edit',
      name: 'CommonLocalityEdit',
      component: CommonLocalityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'common-locality/:commonLocalityId/view',
      name: 'CommonLocalityView',
      component: CommonLocalityDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tenant',
      name: 'Tenant',
      component: Tenant,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tenant/new',
      name: 'TenantCreate',
      component: TenantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tenant/:tenantId/edit',
      name: 'TenantEdit',
      component: TenantUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tenant/:tenantId/view',
      name: 'TenantView',
      component: TenantDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-organization',
      name: 'TypeOfOrganization',
      component: TypeOfOrganization,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-organization/new',
      name: 'TypeOfOrganizationCreate',
      component: TypeOfOrganizationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-organization/:typeOfOrganizationId/edit',
      name: 'TypeOfOrganizationEdit',
      component: TypeOfOrganizationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-organization/:typeOfOrganizationId/view',
      name: 'TypeOfOrganizationView',
      component: TypeOfOrganizationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization',
      name: 'Organization',
      component: Organization,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization/new',
      name: 'OrganizationCreate',
      component: OrganizationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization/:organizationId/edit',
      name: 'OrganizationEdit',
      component: OrganizationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization/:organizationId/view',
      name: 'OrganizationView',
      component: OrganizationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'business-unit',
      name: 'BusinessUnit',
      component: BusinessUnit,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'business-unit/new',
      name: 'BusinessUnitCreate',
      component: BusinessUnitUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'business-unit/:businessUnitId/edit',
      name: 'BusinessUnitEdit',
      component: BusinessUnitUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'business-unit/:businessUnitId/view',
      name: 'BusinessUnitView',
      component: BusinessUnitDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-domain',
      name: 'OrganizationDomain',
      component: OrganizationDomain,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-domain/new',
      name: 'OrganizationDomainCreate',
      component: OrganizationDomainUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-domain/:organizationDomainId/edit',
      name: 'OrganizationDomainEdit',
      component: OrganizationDomainUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-domain/:organizationDomainId/view',
      name: 'OrganizationDomainView',
      component: OrganizationDomainDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-attribute',
      name: 'OrganizationAttribute',
      component: OrganizationAttribute,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-attribute/new',
      name: 'OrganizationAttributeCreate',
      component: OrganizationAttributeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-attribute/:organizationAttributeId/edit',
      name: 'OrganizationAttributeEdit',
      component: OrganizationAttributeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-attribute/:organizationAttributeId/view',
      name: 'OrganizationAttributeView',
      component: OrganizationAttributeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-person',
      name: 'TypeOfPerson',
      component: TypeOfPerson,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-person/new',
      name: 'TypeOfPersonCreate',
      component: TypeOfPersonUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-person/:typeOfPersonId/edit',
      name: 'TypeOfPersonEdit',
      component: TypeOfPersonUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-person/:typeOfPersonId/view',
      name: 'TypeOfPersonView',
      component: TypeOfPersonDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'person',
      name: 'Person',
      component: Person,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'person/new',
      name: 'PersonCreate',
      component: PersonUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'person/:personId/edit',
      name: 'PersonEdit',
      component: PersonUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'person/:personId/view',
      name: 'PersonView',
      component: PersonDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-role',
      name: 'OrganizationRole',
      component: OrganizationRole,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-role/new',
      name: 'OrganizationRoleCreate',
      component: OrganizationRoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-role/:organizationRoleId/edit',
      name: 'OrganizationRoleEdit',
      component: OrganizationRoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-role/:organizationRoleId/view',
      name: 'OrganizationRoleView',
      component: OrganizationRoleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-membership',
      name: 'OrganizationMembership',
      component: OrganizationMembership,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-membership/new',
      name: 'OrganizationMembershipCreate',
      component: OrganizationMembershipUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-membership/:organizationMembershipId/edit',
      name: 'OrganizationMembershipEdit',
      component: OrganizationMembershipUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-membership/:organizationMembershipId/view',
      name: 'OrganizationMembershipView',
      component: OrganizationMembershipDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-member-role',
      name: 'OrganizationMemberRole',
      component: OrganizationMemberRole,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-member-role/new',
      name: 'OrganizationMemberRoleCreate',
      component: OrganizationMemberRoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-member-role/:organizationMemberRoleId/edit',
      name: 'OrganizationMemberRoleEdit',
      component: OrganizationMemberRoleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'organization-member-role/:organizationMemberRoleId/view',
      name: 'OrganizationMemberRoleView',
      component: OrganizationMemberRoleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-type',
      name: 'AssetType',
      component: AssetType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-type/new',
      name: 'AssetTypeCreate',
      component: AssetTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-type/:assetTypeId/edit',
      name: 'AssetTypeEdit',
      component: AssetTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-type/:assetTypeId/view',
      name: 'AssetTypeView',
      component: AssetTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'raw-asset-proc-tmp',
      name: 'RawAssetProcTmp',
      component: RawAssetProcTmp,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'raw-asset-proc-tmp/new',
      name: 'RawAssetProcTmpCreate',
      component: RawAssetProcTmpUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'raw-asset-proc-tmp/:rawAssetProcTmpId/edit',
      name: 'RawAssetProcTmpEdit',
      component: RawAssetProcTmpUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'raw-asset-proc-tmp/:rawAssetProcTmpId/view',
      name: 'RawAssetProcTmpView',
      component: RawAssetProcTmpDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset',
      name: 'Asset',
      component: Asset,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset/new',
      name: 'AssetCreate',
      component: AssetUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset/:assetId/edit',
      name: 'AssetEdit',
      component: AssetUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset/:assetId/view',
      name: 'AssetView',
      component: AssetDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-metadata',
      name: 'AssetMetadata',
      component: AssetMetadata,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-metadata/new',
      name: 'AssetMetadataCreate',
      component: AssetMetadataUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-metadata/:assetMetadataId/edit',
      name: 'AssetMetadataEdit',
      component: AssetMetadataUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-metadata/:assetMetadataId/view',
      name: 'AssetMetadataView',
      component: AssetMetadataDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-collection',
      name: 'AssetCollection',
      component: AssetCollection,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-collection/new',
      name: 'AssetCollectionCreate',
      component: AssetCollectionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-collection/:assetCollectionId/edit',
      name: 'AssetCollectionEdit',
      component: AssetCollectionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'asset-collection/:assetCollectionId/view',
      name: 'AssetCollectionView',
      component: AssetCollectionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'invoice',
      name: 'Invoice',
      component: Invoice,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'invoice/new',
      name: 'InvoiceCreate',
      component: InvoiceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'invoice/:invoiceId/edit',
      name: 'InvoiceEdit',
      component: InvoiceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'invoice/:invoiceId/view',
      name: 'InvoiceView',
      component: InvoiceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-gateway',
      name: 'PaymentGateway',
      component: PaymentGateway,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-gateway/new',
      name: 'PaymentGatewayCreate',
      component: PaymentGatewayUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-gateway/:paymentGatewayId/edit',
      name: 'PaymentGatewayEdit',
      component: PaymentGatewayUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-gateway/:paymentGatewayId/view',
      name: 'PaymentGatewayView',
      component: PaymentGatewayDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment',
      name: 'Payment',
      component: Payment,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment/new',
      name: 'PaymentCreate',
      component: PaymentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment/:paymentId/edit',
      name: 'PaymentEdit',
      component: PaymentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment/:paymentId/view',
      name: 'PaymentView',
      component: PaymentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'warehouse',
      name: 'Warehouse',
      component: Warehouse,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'warehouse/new',
      name: 'WarehouseCreate',
      component: WarehouseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'warehouse/:warehouseId/edit',
      name: 'WarehouseEdit',
      component: WarehouseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'warehouse/:warehouseId/view',
      name: 'WarehouseView',
      component: WarehouseDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'supplier',
      name: 'Supplier',
      component: Supplier,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'supplier/new',
      name: 'SupplierCreate',
      component: SupplierUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'supplier/:supplierId/edit',
      name: 'SupplierEdit',
      component: SupplierUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'supplier/:supplierId/view',
      name: 'SupplierView',
      component: SupplierDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'brand',
      name: 'Brand',
      component: Brand,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'brand/new',
      name: 'BrandCreate',
      component: BrandUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'brand/:brandId/edit',
      name: 'BrandEdit',
      component: BrandUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'brand/:brandId/view',
      name: 'BrandView',
      component: BrandDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product',
      name: 'Product',
      component: Product,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/new',
      name: 'ProductCreate',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/edit',
      name: 'ProductEdit',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/view',
      name: 'ProductView',
      component: ProductDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventory-transaction',
      name: 'InventoryTransaction',
      component: InventoryTransaction,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventory-transaction/new',
      name: 'InventoryTransactionCreate',
      component: InventoryTransactionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventory-transaction/:inventoryTransactionId/edit',
      name: 'InventoryTransactionEdit',
      component: InventoryTransactionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventory-transaction/:inventoryTransactionId/view',
      name: 'InventoryTransactionView',
      component: InventoryTransactionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'stock-level',
      name: 'StockLevel',
      component: StockLevel,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'stock-level/new',
      name: 'StockLevelCreate',
      component: StockLevelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'stock-level/:stockLevelId/edit',
      name: 'StockLevelEdit',
      component: StockLevelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'stock-level/:stockLevelId/view',
      name: 'StockLevelView',
      component: StockLevelDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer',
      name: 'Customer',
      component: Customer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/new',
      name: 'CustomerCreate',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/edit',
      name: 'CustomerEdit',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/view',
      name: 'CustomerView',
      component: CustomerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer-type',
      name: 'CustomerType',
      component: CustomerType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer-type/new',
      name: 'CustomerTypeCreate',
      component: CustomerTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer-type/:customerTypeId/edit',
      name: 'CustomerTypeEdit',
      component: CustomerTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer-type/:customerTypeId/view',
      name: 'CustomerTypeView',
      component: CustomerTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category',
      name: 'Category',
      component: Category,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/new',
      name: 'CategoryCreate',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/edit',
      name: 'CategoryEdit',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/view',
      name: 'CategoryView',
      component: CategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'article',
      name: 'Article',
      component: Article,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'article/new',
      name: 'ArticleCreate',
      component: ArticleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'article/:articleId/edit',
      name: 'ArticleEdit',
      component: ArticleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'article/:articleId/view',
      name: 'ArticleView',
      component: ArticleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order',
      name: 'Order',
      component: Order,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/new',
      name: 'OrderCreate',
      component: OrderUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/:orderId/edit',
      name: 'OrderEdit',
      component: OrderUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order/:orderId/view',
      name: 'OrderView',
      component: OrderDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order-item',
      name: 'OrderItem',
      component: OrderItem,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order-item/new',
      name: 'OrderItemCreate',
      component: OrderItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order-item/:orderItemId/edit',
      name: 'OrderItemEdit',
      component: OrderItemUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'order-item/:orderItemId/view',
      name: 'OrderItemView',
      component: OrderItemDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-artmedia',
      name: 'TypeOfArtmedia',
      component: TypeOfArtmedia,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-artmedia/new',
      name: 'TypeOfArtmediaCreate',
      component: TypeOfArtmediaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-artmedia/:typeOfArtmediaId/edit',
      name: 'TypeOfArtmediaEdit',
      component: TypeOfArtmediaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-artmedia/:typeOfArtmediaId/view',
      name: 'TypeOfArtmediaView',
      component: TypeOfArtmediaDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-artist',
      name: 'TypeOfArtist',
      component: TypeOfArtist,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-artist/new',
      name: 'TypeOfArtistCreate',
      component: TypeOfArtistUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-artist/:typeOfArtistId/edit',
      name: 'TypeOfArtistEdit',
      component: TypeOfArtistUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-artist/:typeOfArtistId/view',
      name: 'TypeOfArtistView',
      component: TypeOfArtistDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artistic-genre',
      name: 'ArtisticGenre',
      component: ArtisticGenre,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artistic-genre/new',
      name: 'ArtisticGenreCreate',
      component: ArtisticGenreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artistic-genre/:artisticGenreId/edit',
      name: 'ArtisticGenreEdit',
      component: ArtisticGenreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artistic-genre/:artisticGenreId/view',
      name: 'ArtisticGenreView',
      component: ArtisticGenreDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artist',
      name: 'Artist',
      component: Artist,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artist/new',
      name: 'ArtistCreate',
      component: ArtistUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artist/:artistId/edit',
      name: 'ArtistEdit',
      component: ArtistUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artist/:artistId/view',
      name: 'ArtistView',
      component: ArtistDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artwork',
      name: 'Artwork',
      component: Artwork,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artwork/new',
      name: 'ArtworkCreate',
      component: ArtworkUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artwork/:artworkId/edit',
      name: 'ArtworkEdit',
      component: ArtworkUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artwork/:artworkId/view',
      name: 'ArtworkView',
      component: ArtworkDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artwork-cast',
      name: 'ArtworkCast',
      component: ArtworkCast,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artwork-cast/new',
      name: 'ArtworkCastCreate',
      component: ArtworkCastUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artwork-cast/:artworkCastId/edit',
      name: 'ArtworkCastEdit',
      component: ArtworkCastUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'artwork-cast/:artworkCastId/view',
      name: 'ArtworkCastView',
      component: ArtworkCastDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-event',
      name: 'TypeOfEvent',
      component: TypeOfEvent,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-event/new',
      name: 'TypeOfEventCreate',
      component: TypeOfEventUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-event/:typeOfEventId/edit',
      name: 'TypeOfEventEdit',
      component: TypeOfEventUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-event/:typeOfEventId/view',
      name: 'TypeOfEventView',
      component: TypeOfEventDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-venue',
      name: 'TypeOfVenue',
      component: TypeOfVenue,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-venue/new',
      name: 'TypeOfVenueCreate',
      component: TypeOfVenueUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-venue/:typeOfVenueId/edit',
      name: 'TypeOfVenueEdit',
      component: TypeOfVenueUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-venue/:typeOfVenueId/view',
      name: 'TypeOfVenueView',
      component: TypeOfVenueDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-activity',
      name: 'TypeOfActivity',
      component: TypeOfActivity,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-activity/new',
      name: 'TypeOfActivityCreate',
      component: TypeOfActivityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-activity/:typeOfActivityId/edit',
      name: 'TypeOfActivityEdit',
      component: TypeOfActivityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'type-of-activity/:typeOfActivityId/view',
      name: 'TypeOfActivityView',
      component: TypeOfActivityDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venue',
      name: 'Venue',
      component: Venue,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venue/new',
      name: 'VenueCreate',
      component: VenueUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venue/:venueId/edit',
      name: 'VenueEdit',
      component: VenueUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'venue/:venueId/view',
      name: 'VenueView',
      component: VenueDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'activity',
      name: 'Activity',
      component: Activity,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'activity/new',
      name: 'ActivityCreate',
      component: ActivityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'activity/:activityId/edit',
      name: 'ActivityEdit',
      component: ActivityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'activity/:activityId/view',
      name: 'ActivityView',
      component: ActivityDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event',
      name: 'Event',
      component: Event,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event/new',
      name: 'EventCreate',
      component: EventUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event/:eventId/edit',
      name: 'EventEdit',
      component: EventUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event/:eventId/view',
      name: 'EventView',
      component: EventDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'program',
      name: 'Program',
      component: Program,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'program/new',
      name: 'ProgramCreate',
      component: ProgramUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'program/:programId/edit',
      name: 'ProgramEdit',
      component: ProgramUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'program/:programId/view',
      name: 'ProgramView',
      component: ProgramDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-program',
      name: 'EventProgram',
      component: EventProgram,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-program/new',
      name: 'EventProgramCreate',
      component: EventProgramUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-program/:eventProgramId/edit',
      name: 'EventProgramEdit',
      component: EventProgramUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-program/:eventProgramId/view',
      name: 'EventProgramView',
      component: EventProgramDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'scheduled-activity',
      name: 'ScheduledActivity',
      component: ScheduledActivity,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'scheduled-activity/new',
      name: 'ScheduledActivityCreate',
      component: ScheduledActivityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'scheduled-activity/:scheduledActivityId/edit',
      name: 'ScheduledActivityEdit',
      component: ScheduledActivityUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'scheduled-activity/:scheduledActivityId/view',
      name: 'ScheduledActivityView',
      component: ScheduledActivityDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-attendee',
      name: 'EventAttendee',
      component: EventAttendee,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-attendee/new',
      name: 'EventAttendeeCreate',
      component: EventAttendeeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-attendee/:eventAttendeeId/edit',
      name: 'EventAttendeeEdit',
      component: EventAttendeeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'event-attendee/:eventAttendeeId/view',
      name: 'EventAttendeeView',
      component: EventAttendeeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket-purchased',
      name: 'TicketPurchased',
      component: TicketPurchased,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket-purchased/new',
      name: 'TicketPurchasedCreate',
      component: TicketPurchasedUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket-purchased/:ticketPurchasedId/edit',
      name: 'TicketPurchasedEdit',
      component: TicketPurchasedUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ticket-purchased/:ticketPurchasedId/view',
      name: 'TicketPurchasedView',
      component: TicketPurchasedDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
