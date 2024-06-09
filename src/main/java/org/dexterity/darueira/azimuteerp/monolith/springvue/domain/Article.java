package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.SizeOptionEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Article.
 */
@Entity
@Table(name = "tb_article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "inventory_product_id", nullable = false)
    private Long inventoryProductId;

    @Size(max = 60)
    @Column(name = "sku_code", length = 60)
    private String skuCode;

    @Size(max = 150)
    @Column(name = "custom_name", length = 150)
    private String customName;

    @Size(max = 8192)
    @Column(name = "custom_description", length = 8192)
    private String customDescription;

    @Column(name = "price_value", precision = 21, scale = 2)
    private BigDecimal priceValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_size", nullable = false)
    private SizeOptionEnum itemSize;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_article__asset_collection",
        joinColumns = @JoinColumn(name = "tb_article_id"),
        inverseJoinColumns = @JoinColumn(name = "asset_collection_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assets", "articles", "events", "activities", "scheduledActivities" }, allowSetters = true)
    private Set<AssetCollection> assetCollections = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "articlesLists", "categoryParent", "childrenCategoriesLists" }, allowSetters = true)
    private Category mainCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "article", "order" }, allowSetters = true)
    private Set<OrderItem> ordersItemsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Article id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventoryProductId() {
        return this.inventoryProductId;
    }

    public Article inventoryProductId(Long inventoryProductId) {
        this.setInventoryProductId(inventoryProductId);
        return this;
    }

    public void setInventoryProductId(Long inventoryProductId) {
        this.inventoryProductId = inventoryProductId;
    }

    public String getSkuCode() {
        return this.skuCode;
    }

    public Article skuCode(String skuCode) {
        this.setSkuCode(skuCode);
        return this;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getCustomName() {
        return this.customName;
    }

    public Article customName(String customName) {
        this.setCustomName(customName);
        return this;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomDescription() {
        return this.customDescription;
    }

    public Article customDescription(String customDescription) {
        this.setCustomDescription(customDescription);
        return this;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }

    public BigDecimal getPriceValue() {
        return this.priceValue;
    }

    public Article priceValue(BigDecimal priceValue) {
        this.setPriceValue(priceValue);
        return this;
    }

    public void setPriceValue(BigDecimal priceValue) {
        this.priceValue = priceValue;
    }

    public SizeOptionEnum getItemSize() {
        return this.itemSize;
    }

    public Article itemSize(SizeOptionEnum itemSize) {
        this.setItemSize(itemSize);
        return this;
    }

    public void setItemSize(SizeOptionEnum itemSize) {
        this.itemSize = itemSize;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Article activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Set<AssetCollection> getAssetCollections() {
        return this.assetCollections;
    }

    public void setAssetCollections(Set<AssetCollection> assetCollections) {
        this.assetCollections = assetCollections;
    }

    public Article assetCollections(Set<AssetCollection> assetCollections) {
        this.setAssetCollections(assetCollections);
        return this;
    }

    public Article addAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.add(assetCollection);
        return this;
    }

    public Article removeAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.remove(assetCollection);
        return this;
    }

    public Category getMainCategory() {
        return this.mainCategory;
    }

    public void setMainCategory(Category category) {
        this.mainCategory = category;
    }

    public Article mainCategory(Category category) {
        this.setMainCategory(category);
        return this;
    }

    public Set<OrderItem> getOrdersItemsLists() {
        return this.ordersItemsLists;
    }

    public void setOrdersItemsLists(Set<OrderItem> orderItems) {
        if (this.ordersItemsLists != null) {
            this.ordersItemsLists.forEach(i -> i.setArticle(null));
        }
        if (orderItems != null) {
            orderItems.forEach(i -> i.setArticle(this));
        }
        this.ordersItemsLists = orderItems;
    }

    public Article ordersItemsLists(Set<OrderItem> orderItems) {
        this.setOrdersItemsLists(orderItems);
        return this;
    }

    public Article addOrdersItemsList(OrderItem orderItem) {
        this.ordersItemsLists.add(orderItem);
        orderItem.setArticle(this);
        return this;
    }

    public Article removeOrdersItemsList(OrderItem orderItem) {
        this.ordersItemsLists.remove(orderItem);
        orderItem.setArticle(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return getId() != null && getId().equals(((Article) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", inventoryProductId=" + getInventoryProductId() +
            ", skuCode='" + getSkuCode() + "'" +
            ", customName='" + getCustomName() + "'" +
            ", customDescription='" + getCustomDescription() + "'" +
            ", priceValue=" + getPriceValue() +
            ", itemSize='" + getItemSize() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
