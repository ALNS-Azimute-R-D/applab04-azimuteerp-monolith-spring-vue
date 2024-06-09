package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {

    private Long id;

    @Size(max = 25)
    private String productSKU;

    @Size(max = 50)
    private String productName;

    @Size(max = 512)
    private String description;

    private BigDecimal standardCost;

    @NotNull
    private BigDecimal listPrice;

    private Integer reorderLevel;

    private Integer targetLevel;

    @Size(max = 50)
    private String quantityPerUnit;

    @NotNull
    private Boolean discontinued;

    private Integer minimumReorderQuantity;

    @Size(max = 50)
    private String suggestedCategory;

    @Lob
    private byte[] attachments;

    private String attachmentsContentType;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private BrandDTO brand;

    private Set<SupplierDTO> toSuppliers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSKU() {
        return productSKU;
    }

    public void setProductSKU(String productSKU) {
        this.productSKU = productSKU;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(BigDecimal standardCost) {
        this.standardCost = standardCost;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getTargetLevel() {
        return targetLevel;
    }

    public void setTargetLevel(Integer targetLevel) {
        this.targetLevel = targetLevel;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    public Integer getMinimumReorderQuantity() {
        return minimumReorderQuantity;
    }

    public void setMinimumReorderQuantity(Integer minimumReorderQuantity) {
        this.minimumReorderQuantity = minimumReorderQuantity;
    }

    public String getSuggestedCategory() {
        return suggestedCategory;
    }

    public void setSuggestedCategory(String suggestedCategory) {
        this.suggestedCategory = suggestedCategory;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return attachmentsContentType;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    public Set<SupplierDTO> getToSuppliers() {
        return toSuppliers;
    }

    public void setToSuppliers(Set<SupplierDTO> toSuppliers) {
        this.toSuppliers = toSuppliers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
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
            ", activationStatus='" + getActivationStatus() + "'" +
            ", brand=" + getBrand() +
            ", toSuppliers=" + getToSuppliers() +
            "}";
    }
}
