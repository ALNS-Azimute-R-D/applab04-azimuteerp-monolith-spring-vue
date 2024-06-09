package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "tb_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 25)
    @Column(name = "product_sku", length = 25)
    private String productSKU;

    @Size(max = 50)
    @Column(name = "product_name", length = 50)
    private String productName;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "standard_cost", precision = 21, scale = 2)
    private BigDecimal standardCost;

    @NotNull
    @Column(name = "list_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal listPrice;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Column(name = "target_level")
    private Integer targetLevel;

    @Size(max = 50)
    @Column(name = "quantity_per_unit", length = 50)
    private String quantityPerUnit;

    @NotNull
    @Column(name = "discontinued", nullable = false)
    private Boolean discontinued;

    @Column(name = "minimum_reorder_quantity")
    private Integer minimumReorderQuantity;

    @Size(max = 50)
    @Column(name = "suggested_category", length = 50)
    private String suggestedCategory;

    @Lob
    @Column(name = "attachments")
    private byte[] attachments;

    @Column(name = "attachments_content_type")
    private String attachmentsContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productsLists" }, allowSetters = true)
    private Brand brand;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_tb_product__to_supplier",
        joinColumns = @JoinColumn(name = "tb_product_id"),
        inverseJoinColumns = @JoinColumn(name = "to_supplier_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "representativePerson", "inventoryTransactionsLists", "toProducts" }, allowSetters = true)
    private Set<Supplier> toSuppliers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplier", "product", "warehouse" }, allowSetters = true)
    private Set<InventoryTransaction> productsLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "warehouse", "product" }, allowSetters = true)
    private Set<StockLevel> stockLevelsLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSKU() {
        return this.productSKU;
    }

    public Product productSKU(String productSKU) {
        this.setProductSKU(productSKU);
        return this;
    }

    public void setProductSKU(String productSKU) {
        this.productSKU = productSKU;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStandardCost() {
        return this.standardCost;
    }

    public Product standardCost(BigDecimal standardCost) {
        this.setStandardCost(standardCost);
        return this;
    }

    public void setStandardCost(BigDecimal standardCost) {
        this.standardCost = standardCost;
    }

    public BigDecimal getListPrice() {
        return this.listPrice;
    }

    public Product listPrice(BigDecimal listPrice) {
        this.setListPrice(listPrice);
        return this;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getReorderLevel() {
        return this.reorderLevel;
    }

    public Product reorderLevel(Integer reorderLevel) {
        this.setReorderLevel(reorderLevel);
        return this;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getTargetLevel() {
        return this.targetLevel;
    }

    public Product targetLevel(Integer targetLevel) {
        this.setTargetLevel(targetLevel);
        return this;
    }

    public void setTargetLevel(Integer targetLevel) {
        this.targetLevel = targetLevel;
    }

    public String getQuantityPerUnit() {
        return this.quantityPerUnit;
    }

    public Product quantityPerUnit(String quantityPerUnit) {
        this.setQuantityPerUnit(quantityPerUnit);
        return this;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public Boolean getDiscontinued() {
        return this.discontinued;
    }

    public Product discontinued(Boolean discontinued) {
        this.setDiscontinued(discontinued);
        return this;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public Integer getMinimumReorderQuantity() {
        return this.minimumReorderQuantity;
    }

    public Product minimumReorderQuantity(Integer minimumReorderQuantity) {
        this.setMinimumReorderQuantity(minimumReorderQuantity);
        return this;
    }

    public void setMinimumReorderQuantity(Integer minimumReorderQuantity) {
        this.minimumReorderQuantity = minimumReorderQuantity;
    }

    public String getSuggestedCategory() {
        return this.suggestedCategory;
    }

    public Product suggestedCategory(String suggestedCategory) {
        this.setSuggestedCategory(suggestedCategory);
        return this;
    }

    public void setSuggestedCategory(String suggestedCategory) {
        this.suggestedCategory = suggestedCategory;
    }

    public byte[] getAttachments() {
        return this.attachments;
    }

    public Product attachments(byte[] attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return this.attachmentsContentType;
    }

    public Product attachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
        return this;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Product activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Product brand(Brand brand) {
        this.setBrand(brand);
        return this;
    }

    public Set<Supplier> getToSuppliers() {
        return this.toSuppliers;
    }

    public void setToSuppliers(Set<Supplier> suppliers) {
        this.toSuppliers = suppliers;
    }

    public Product toSuppliers(Set<Supplier> suppliers) {
        this.setToSuppliers(suppliers);
        return this;
    }

    public Product addToSupplier(Supplier supplier) {
        this.toSuppliers.add(supplier);
        return this;
    }

    public Product removeToSupplier(Supplier supplier) {
        this.toSuppliers.remove(supplier);
        return this;
    }

    public Set<InventoryTransaction> getProductsLists() {
        return this.productsLists;
    }

    public void setProductsLists(Set<InventoryTransaction> inventoryTransactions) {
        if (this.productsLists != null) {
            this.productsLists.forEach(i -> i.setProduct(null));
        }
        if (inventoryTransactions != null) {
            inventoryTransactions.forEach(i -> i.setProduct(this));
        }
        this.productsLists = inventoryTransactions;
    }

    public Product productsLists(Set<InventoryTransaction> inventoryTransactions) {
        this.setProductsLists(inventoryTransactions);
        return this;
    }

    public Product addProductsList(InventoryTransaction inventoryTransaction) {
        this.productsLists.add(inventoryTransaction);
        inventoryTransaction.setProduct(this);
        return this;
    }

    public Product removeProductsList(InventoryTransaction inventoryTransaction) {
        this.productsLists.remove(inventoryTransaction);
        inventoryTransaction.setProduct(null);
        return this;
    }

    public Set<StockLevel> getStockLevelsLists() {
        return this.stockLevelsLists;
    }

    public void setStockLevelsLists(Set<StockLevel> stockLevels) {
        if (this.stockLevelsLists != null) {
            this.stockLevelsLists.forEach(i -> i.setProduct(null));
        }
        if (stockLevels != null) {
            stockLevels.forEach(i -> i.setProduct(this));
        }
        this.stockLevelsLists = stockLevels;
    }

    public Product stockLevelsLists(Set<StockLevel> stockLevels) {
        this.setStockLevelsLists(stockLevels);
        return this;
    }

    public Product addStockLevelsList(StockLevel stockLevel) {
        this.stockLevelsLists.add(stockLevel);
        stockLevel.setProduct(this);
        return this;
    }

    public Product removeStockLevelsList(StockLevel stockLevel) {
        this.stockLevelsLists.remove(stockLevel);
        stockLevel.setProduct(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productSKU='" + getProductSKU() + "'" +
            ", productName='" + getProductName() + "'" +
            ", description='" + getDescription() + "'" +
            ", standardCost=" + getStandardCost() +
            ", listPrice=" + getListPrice() +
            ", reorderLevel=" + getReorderLevel() +
            ", targetLevel=" + getTargetLevel() +
            ", quantityPerUnit='" + getQuantityPerUnit() + "'" +
            ", discontinued='" + getDiscontinued() + "'" +
            ", minimumReorderQuantity=" + getMinimumReorderQuantity() +
            ", suggestedCategory='" + getSuggestedCategory() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", attachmentsContentType='" + getAttachmentsContentType() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
