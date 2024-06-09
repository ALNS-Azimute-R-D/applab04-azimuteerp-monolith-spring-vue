package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssetCollection.
 */
@Entity
@Table(name = "tb_assets_collection")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 512)
    @Column(name = "name", length = 512, nullable = false)
    private String name;

    @Size(max = 512)
    @Column(name = "full_filename_path", length = 512)
    private String fullFilenamePath;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_assets_collection__asset",
        joinColumns = @JoinColumn(name = "tb_assets_collection_id"),
        inverseJoinColumns = @JoinColumn(name = "asset_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetType", "rawAssetProcTmp", "assetMetadata", "assetCollections" }, allowSetters = true)
    private Set<Asset> assets = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "assetCollections")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetCollections", "mainCategory", "ordersItemsLists" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "assetCollections")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "mainVenue",
            "typeOfEvent",
            "promoteurPerson",
            "assetCollections",
            "eventProgramsLists",
            "ticketsPurchasedLists",
            "eventAttendeesLists",
        },
        allowSetters = true
    )
    private Set<Event> events = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "assetCollections")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "typeOfActivity", "createdByUser", "assetCollections", "scheduledActivitiesLists" },
        allowSetters = true
    )
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "assetCollections")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "program", "activity", "responsiblePerson", "assetCollections" }, allowSetters = true)
    private Set<ScheduledActivity> scheduledActivities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AssetCollection id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AssetCollection name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullFilenamePath() {
        return this.fullFilenamePath;
    }

    public AssetCollection fullFilenamePath(String fullFilenamePath) {
        this.setFullFilenamePath(fullFilenamePath);
        return this;
    }

    public void setFullFilenamePath(String fullFilenamePath) {
        this.fullFilenamePath = fullFilenamePath;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public AssetCollection activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Set<Asset> getAssets() {
        return this.assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public AssetCollection assets(Set<Asset> assets) {
        this.setAssets(assets);
        return this;
    }

    public AssetCollection addAsset(Asset asset) {
        this.assets.add(asset);
        return this;
    }

    public AssetCollection removeAsset(Asset asset) {
        this.assets.remove(asset);
        return this;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.removeAssetCollection(this));
        }
        if (articles != null) {
            articles.forEach(i -> i.addAssetCollection(this));
        }
        this.articles = articles;
    }

    public AssetCollection articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public AssetCollection addArticle(Article article) {
        this.articles.add(article);
        article.getAssetCollections().add(this);
        return this;
    }

    public AssetCollection removeArticle(Article article) {
        this.articles.remove(article);
        article.getAssetCollections().remove(this);
        return this;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Event> events) {
        if (this.events != null) {
            this.events.forEach(i -> i.removeAssetCollection(this));
        }
        if (events != null) {
            events.forEach(i -> i.addAssetCollection(this));
        }
        this.events = events;
    }

    public AssetCollection events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public AssetCollection addEvent(Event event) {
        this.events.add(event);
        event.getAssetCollections().add(this);
        return this;
    }

    public AssetCollection removeEvent(Event event) {
        this.events.remove(event);
        event.getAssetCollections().remove(this);
        return this;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.removeAssetCollection(this));
        }
        if (activities != null) {
            activities.forEach(i -> i.addAssetCollection(this));
        }
        this.activities = activities;
    }

    public AssetCollection activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public AssetCollection addActivity(Activity activity) {
        this.activities.add(activity);
        activity.getAssetCollections().add(this);
        return this;
    }

    public AssetCollection removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.getAssetCollections().remove(this);
        return this;
    }

    public Set<ScheduledActivity> getScheduledActivities() {
        return this.scheduledActivities;
    }

    public void setScheduledActivities(Set<ScheduledActivity> scheduledActivities) {
        if (this.scheduledActivities != null) {
            this.scheduledActivities.forEach(i -> i.removeAssetCollection(this));
        }
        if (scheduledActivities != null) {
            scheduledActivities.forEach(i -> i.addAssetCollection(this));
        }
        this.scheduledActivities = scheduledActivities;
    }

    public AssetCollection scheduledActivities(Set<ScheduledActivity> scheduledActivities) {
        this.setScheduledActivities(scheduledActivities);
        return this;
    }

    public AssetCollection addScheduledActivity(ScheduledActivity scheduledActivity) {
        this.scheduledActivities.add(scheduledActivity);
        scheduledActivity.getAssetCollections().add(this);
        return this;
    }

    public AssetCollection removeScheduledActivity(ScheduledActivity scheduledActivity) {
        this.scheduledActivities.remove(scheduledActivity);
        scheduledActivity.getAssetCollections().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetCollection)) {
            return false;
        }
        return getId() != null && getId().equals(((AssetCollection) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetCollection{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fullFilenamePath='" + getFullFilenamePath() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
