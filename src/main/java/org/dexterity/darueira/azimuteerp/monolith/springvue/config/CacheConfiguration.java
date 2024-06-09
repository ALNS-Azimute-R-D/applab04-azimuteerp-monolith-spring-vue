package org.dexterity.darueira.azimuteerp.monolith.springvue.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Country.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Country.class.getName() + ".provincesLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Province.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Province.class.getName() + ".townCitiesLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity.class.getName() + ".districtsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District.class.getName() + ".commonLocalitiesLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District.class.getName() + ".personsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District.class.getName() + ".customersLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality.class.getName() + ".venuesLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Tenant.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Tenant.class.getName() + ".organizationsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization.class.getName() + ".organizationsLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization.class.getName() + ".organizationDomainsLists"
            );
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization.class.getName() + ".organizationAttributesLists"
            );
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization.class.getName() + ".businessUnitsLists"
            );
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization.class.getName() + ".childrenOrganizationsLists"
            );
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization.class.getName() + ".organizationRolesLists"
            );
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization.class.getName() + ".organizationMembershipsLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnit.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnit.class.getName() + ".childrenBusinessUnitsLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttribute.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPerson.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPerson.class.getName() + ".personsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".managedPersonsLists");
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".organizationMembershipsLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".suppliersLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".customersLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".activitiesLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".promotedEventsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".eventsProgramsLists");
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".scheduledActivitiesLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person.class.getName() + ".eventAttendeesLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole.class.getName() +
                ".organizationMemberRolesLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership.class.getName() +
                ".organizationMemberRolesLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType.class.getName() + ".rawAssetsProcsTmps");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType.class.getName() + ".assets");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmp.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmp.class.getName() + ".assets");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset.class.getName() + ".assetCollections");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetMetadata.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection.class.getName() + ".assets");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection.class.getName() + ".articles");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection.class.getName() + ".events");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection.class.getName() + ".activities");
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection.class.getName() + ".scheduledActivities"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Invoice.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Invoice.class.getName() + ".ordersLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Invoice.class.getName() + ".ticketsPurchasedLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway.class.getName() + ".invoicesAsPreferrableLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway.class.getName() + ".paymentsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Payment.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Warehouse.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Warehouse.class.getName() + ".inventoryTransactionsLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Warehouse.class.getName() + ".stockLevelsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Supplier.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Supplier.class.getName() + ".inventoryTransactionsLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Supplier.class.getName() + ".toProducts");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Brand.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Brand.class.getName() + ".productsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product.class.getName() + ".toSuppliers");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product.class.getName() + ".productsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product.class.getName() + ".stockLevelsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransaction.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevel.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Customer.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Customer.class.getName() + ".ordersLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerType.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerType.class.getName() + ".customersLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Category.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Category.class.getName() + ".articlesLists");
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Category.class.getName() + ".childrenCategoriesLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Article.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Article.class.getName() + ".assetCollections");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Article.class.getName() + ".ordersItemsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Order.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Order.class.getName() + ".orderItemsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrderItem.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia.class.getName() + ".artistsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia.class.getName() + ".artworksLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist.class.getName() + ".artistsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre.class.getName() + ".artisticGenres");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist.class.getName() + ".artists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist.class.getName() + ".artworkCastsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist.class.getName() + ".linkedArtistsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork.class.getName() + ".artworkCastsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork.class.getName() + ".linkedArtworksLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent.class.getName() + ".eventsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenue.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenue.class.getName() + ".venuesLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity.class.getName() + ".activitiesLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Venue.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Venue.class.getName() + ".eventsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity.class.getName() + ".assetCollections");
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity.class.getName() + ".scheduledActivitiesLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event.class.getName() + ".assetCollections");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event.class.getName() + ".eventProgramsLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event.class.getName() + ".ticketsPurchasedLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event.class.getName() + ".eventAttendeesLists");
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Program.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Program.class.getName() + ".programsLists");
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Program.class.getName() + ".scheduledActivitiesLists"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgram.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity.class.getName() + ".assetCollections"
            );
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendee.class.getName());
            createCache(cm, org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased.class.getName());
            createCache(
                cm,
                org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased.class.getName() + ".eventAttendeesLists"
            );
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
