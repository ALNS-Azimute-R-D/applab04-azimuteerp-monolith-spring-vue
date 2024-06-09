package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.PreferredPurposeEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.StatusAssetEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.StorageTypeEnum;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 512)
    private String name;

    private StorageTypeEnum storageTypeUsed;

    @Size(max = 512)
    private String fullFilenamePath;

    private StatusAssetEnum status;

    private PreferredPurposeEnum preferredPurpose;

    @Lob
    private byte[] assetContentAsBlob;

    private String assetContentAsBlobContentType;

    @NotNull
    private ActivationStatusEnum activationStatus;

    @NotNull
    private AssetTypeDTO assetType;

    private RawAssetProcTmpDTO rawAssetProcTmp;

    private Set<AssetCollectionDTO> assetCollections = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageTypeEnum getStorageTypeUsed() {
        return storageTypeUsed;
    }

    public void setStorageTypeUsed(StorageTypeEnum storageTypeUsed) {
        this.storageTypeUsed = storageTypeUsed;
    }

    public String getFullFilenamePath() {
        return fullFilenamePath;
    }

    public void setFullFilenamePath(String fullFilenamePath) {
        this.fullFilenamePath = fullFilenamePath;
    }

    public StatusAssetEnum getStatus() {
        return status;
    }

    public void setStatus(StatusAssetEnum status) {
        this.status = status;
    }

    public PreferredPurposeEnum getPreferredPurpose() {
        return preferredPurpose;
    }

    public void setPreferredPurpose(PreferredPurposeEnum preferredPurpose) {
        this.preferredPurpose = preferredPurpose;
    }

    public byte[] getAssetContentAsBlob() {
        return assetContentAsBlob;
    }

    public void setAssetContentAsBlob(byte[] assetContentAsBlob) {
        this.assetContentAsBlob = assetContentAsBlob;
    }

    public String getAssetContentAsBlobContentType() {
        return assetContentAsBlobContentType;
    }

    public void setAssetContentAsBlobContentType(String assetContentAsBlobContentType) {
        this.assetContentAsBlobContentType = assetContentAsBlobContentType;
    }

    public ActivationStatusEnum getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public AssetTypeDTO getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetTypeDTO assetType) {
        this.assetType = assetType;
    }

    public RawAssetProcTmpDTO getRawAssetProcTmp() {
        return rawAssetProcTmp;
    }

    public void setRawAssetProcTmp(RawAssetProcTmpDTO rawAssetProcTmp) {
        this.rawAssetProcTmp = rawAssetProcTmp;
    }

    public Set<AssetCollectionDTO> getAssetCollections() {
        return assetCollections;
    }

    public void setAssetCollections(Set<AssetCollectionDTO> assetCollections) {
        this.assetCollections = assetCollections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetDTO)) {
            return false;
        }

        AssetDTO assetDTO = (AssetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", storageTypeUsed='" + getStorageTypeUsed() + "'" +
            ", fullFilenamePath='" + getFullFilenamePath() + "'" +
            ", status='" + getStatus() + "'" +
            ", preferredPurpose='" + getPreferredPurpose() + "'" +
            ", assetContentAsBlob='" + getAssetContentAsBlob() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            ", assetType=" + getAssetType() +
            ", rawAssetProcTmp=" + getRawAssetProcTmp() +
            ", assetCollections=" + getAssetCollections() +
            "}";
    }
}
