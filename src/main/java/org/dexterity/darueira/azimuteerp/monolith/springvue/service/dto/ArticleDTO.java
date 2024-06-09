package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.SizeOptionEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Article} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleDTO implements Serializable {

    private Long id;

    @NotNull
    private Long inventoryProductId;

    @Size(max = 60)
    private String skuCode;

    @Size(max = 150)
    private String customName;

    @Size(max = 8192)
    private String customDescription;

    private BigDecimal priceValue;

    @NotNull
    private SizeOptionEnum itemSize;

    @NotNull
    private ActivationStatusEnum activationStatus;

    private Set<AssetCollectionDTO> assetCollections = new HashSet<>();

    private CategoryDTO mainCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventoryProductId() {
        return inventoryProductId;
    }

    public void setInventoryProductId(Long inventoryProductId) {
        this.inventoryProductId = inventoryProductId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }

    public BigDecimal getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(BigDecimal priceValue) {
        this.priceValue = priceValue;
    }

    public SizeOptionEnum getItemSize() {
        return itemSize;
    }

    public void setItemSize(SizeOptionEnum itemSize) {
        this.itemSize = itemSize;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Set<AssetCollectionDTO> getAssetCollections() {
        return assetCollections;
    }

    public void setAssetCollections(Set<AssetCollectionDTO> assetCollections) {
        this.assetCollections = assetCollections;
    }

    public CategoryDTO getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(CategoryDTO mainCategory) {
        this.mainCategory = mainCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDTO)) {
            return false;
        }

        ArticleDTO articleDTO = (ArticleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleDTO{" +
            "id=" + getId() +
            ", inventoryProductId=" + getInventoryProductId() +
            ", skuCode='" + getSkuCode() + "'" +
            ", customName='" + getCustomName() + "'" +
            ", customDescription='" + getCustomDescription() + "'" +
            ", priceValue=" + getPriceValue() +
            ", itemSize='" + getItemSize() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", assetCollections=" + getAssetCollections() +
            ", mainCategory=" + getMainCategory() +
            "}";
    }
}
