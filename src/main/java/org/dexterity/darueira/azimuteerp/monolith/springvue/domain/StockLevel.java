package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StockLevel.
 */
@Entity
@Table(name = "tb_stock_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StockLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "last_modified_date", nullable = false)
    private Instant lastModifiedDate;

    @NotNull
    @Column(name = "remaining_quantity", nullable = false)
    private Integer remainingQuantity;

    @Size(max = 2048)
    @Column(name = "common_attributes_details_json", length = 2048)
    private String commonAttributesDetailsJSON;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "inventoryTransactionsLists", "stockLevelsLists" }, allowSetters = true)
    private Warehouse warehouse;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "brand", "toSuppliers", "productsLists", "stockLevelsLists" }, allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StockLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public StockLevel lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getRemainingQuantity() {
        return this.remainingQuantity;
    }

    public StockLevel remainingQuantity(Integer remainingQuantity) {
        this.setRemainingQuantity(remainingQuantity);
        return this;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public String getCommonAttributesDetailsJSON() {
        return this.commonAttributesDetailsJSON;
    }

    public StockLevel commonAttributesDetailsJSON(String commonAttributesDetailsJSON) {
        this.setCommonAttributesDetailsJSON(commonAttributesDetailsJSON);
        return this;
    }

    public void setCommonAttributesDetailsJSON(String commonAttributesDetailsJSON) {
        this.commonAttributesDetailsJSON = commonAttributesDetailsJSON;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public StockLevel warehouse(Warehouse warehouse) {
        this.setWarehouse(warehouse);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public StockLevel product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockLevel)) {
            return false;
        }
        return getId() != null && getId().equals(((StockLevel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockLevel{" +
            "id=" + getId() +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", remainingQuantity=" + getRemainingQuantity() +
            ", commonAttributesDetailsJSON='" + getCommonAttributesDetailsJSON() + "'" +
            "}";
    }
}
