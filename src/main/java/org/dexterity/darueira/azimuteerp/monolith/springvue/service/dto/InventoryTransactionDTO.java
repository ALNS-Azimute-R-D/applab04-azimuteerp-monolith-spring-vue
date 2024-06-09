package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventoryTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private Long invoiceId;

    private Instant transactionCreatedDate;

    private Instant transactionModifiedDate;

    @NotNull
    private Integer quantity;

    @Size(max = 512)
    private String transactionComments;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private SupplierDTO supplier;

    @NotNull
    private ProductDTO product;

    @NotNull
    private WarehouseDTO warehouse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Instant getTransactionCreatedDate() {
        return transactionCreatedDate;
    }

    public void setTransactionCreatedDate(Instant transactionCreatedDate) {
        this.transactionCreatedDate = transactionCreatedDate;
    }

    public Instant getTransactionModifiedDate() {
        return transactionModifiedDate;
    }

    public void setTransactionModifiedDate(Instant transactionModifiedDate) {
        this.transactionModifiedDate = transactionModifiedDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTransactionComments() {
        return transactionComments;
    }

    public void setTransactionComments(String transactionComments) {
        this.transactionComments = transactionComments;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public WarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDTO warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryTransactionDTO)) {
            return false;
        }

        InventoryTransactionDTO inventoryTransactionDTO = (InventoryTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inventoryTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryTransactionDTO{" +
            "id=" + getId() +
            ", invoiceId=" + getInvoiceId() +
            ", transactionCreatedDate='" + getTransactionCreatedDate() + "'" +
            ", transactionModifiedDate='" + getTransactionModifiedDate() + "'" +
            ", quantity=" + getQuantity() +
            ", transactionComments='" + getTransactionComments() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", supplier=" + getSupplier() +
            ", product=" + getProduct() +
            ", warehouse=" + getWarehouse() +
            "}";
    }
}
