package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.PreferredPurposeEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.StatusAssetEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.StorageTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Asset.
 */
@Entity
@Table(name = "tb_asset")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Asset implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type_used")
    private StorageTypeEnum storageTypeUsed;

    @Size(max = 512)
    @Column(name = "full_filename_path", length = 512)
    private String fullFilenamePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAssetEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_purpose")
    private PreferredPurposeEnum preferredPurpose;

    @Lob
    @Column(name = "asset_content_as_blob")
    private byte[] assetContentAsBlob;

    @Column(name = "asset_content_as_blob_content_type")
    private String assetContentAsBlobContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activation_status", nullable = false)
    private ActivationStatusEnum activationStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "rawAssetsProcsTmps", "assets" }, allowSetters = true)
    private AssetType assetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "assetType", "assets" }, allowSetters = true)
    private RawAssetProcTmp rawAssetProcTmp;

    @JsonIgnoreProperties(value = { "asset" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "asset")
    private AssetMetadata assetMetadata;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "assets")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assets", "articles", "events", "activities", "scheduledActivities" }, allowSetters = true)
    private Set<AssetCollection> assetCollections = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asset id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Asset name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageTypeEnum getStorageTypeUsed() {
        return this.storageTypeUsed;
    }

    public Asset storageTypeUsed(StorageTypeEnum storageTypeUsed) {
        this.setStorageTypeUsed(storageTypeUsed);
        return this;
    }

    public void setStorageTypeUsed(StorageTypeEnum storageTypeUsed) {
        this.storageTypeUsed = storageTypeUsed;
    }

    public String getFullFilenamePath() {
        return this.fullFilenamePath;
    }

    public Asset fullFilenamePath(String fullFilenamePath) {
        this.setFullFilenamePath(fullFilenamePath);
        return this;
    }

    public void setFullFilenamePath(String fullFilenamePath) {
        this.fullFilenamePath = fullFilenamePath;
    }

    public StatusAssetEnum getStatus() {
        return this.status;
    }

    public Asset status(StatusAssetEnum status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusAssetEnum status) {
        this.status = status;
    }

    public PreferredPurposeEnum getPreferredPurpose() {
        return this.preferredPurpose;
    }

    public Asset preferredPurpose(PreferredPurposeEnum preferredPurpose) {
        this.setPreferredPurpose(preferredPurpose);
        return this;
    }

    public void setPreferredPurpose(PreferredPurposeEnum preferredPurpose) {
        this.preferredPurpose = preferredPurpose;
    }

    public byte[] getAssetContentAsBlob() {
        return this.assetContentAsBlob;
    }

    public Asset assetContentAsBlob(byte[] assetContentAsBlob) {
        this.setAssetContentAsBlob(assetContentAsBlob);
        return this;
    }

    public void setAssetContentAsBlob(byte[] assetContentAsBlob) {
        this.assetContentAsBlob = assetContentAsBlob;
    }

    public String getAssetContentAsBlobContentType() {
        return this.assetContentAsBlobContentType;
    }

    public Asset assetContentAsBlobContentType(String assetContentAsBlobContentType) {
        this.assetContentAsBlobContentType = assetContentAsBlobContentType;
        return this;
    }

    public void setAssetContentAsBlobContentType(String assetContentAsBlobContentType) {
        this.assetContentAsBlobContentType = assetContentAsBlobContentType;
    }

    public ActivationStatusEnum getActivationStatus() {
        return this.activationStatus;
    }

    public Asset activationStatus(ActivationStatusEnum activationStatus) {
        this.setActivationStatus(activationStatus);
        return this;
    }

    public void setActivationStatus(ActivationStatusEnum activationStatus) {
        this.activationStatus = activationStatus;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Asset assetType(AssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public RawAssetProcTmp getRawAssetProcTmp() {
        return this.rawAssetProcTmp;
    }

    public void setRawAssetProcTmp(RawAssetProcTmp rawAssetProcTmp) {
        this.rawAssetProcTmp = rawAssetProcTmp;
    }

    public Asset rawAssetProcTmp(RawAssetProcTmp rawAssetProcTmp) {
        this.setRawAssetProcTmp(rawAssetProcTmp);
        return this;
    }

    public AssetMetadata getAssetMetadata() {
        return this.assetMetadata;
    }

    public void setAssetMetadata(AssetMetadata assetMetadata) {
        if (this.assetMetadata != null) {
            this.assetMetadata.setAsset(null);
        }
        if (assetMetadata != null) {
            assetMetadata.setAsset(this);
        }
        this.assetMetadata = assetMetadata;
    }

    public Asset assetMetadata(AssetMetadata assetMetadata) {
        this.setAssetMetadata(assetMetadata);
        return this;
    }

    public Set<AssetCollection> getAssetCollections() {
        return this.assetCollections;
    }

    public void setAssetCollections(Set<AssetCollection> assetCollections) {
        if (this.assetCollections != null) {
            this.assetCollections.forEach(i -> i.removeAsset(this));
        }
        if (assetCollections != null) {
            assetCollections.forEach(i -> i.addAsset(this));
        }
        this.assetCollections = assetCollections;
    }

    public Asset assetCollections(Set<AssetCollection> assetCollections) {
        this.setAssetCollections(assetCollections);
        return this;
    }

    public Asset addAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.add(assetCollection);
        assetCollection.getAssets().add(this);
        return this;
    }

    public Asset removeAssetCollection(AssetCollection assetCollection) {
        this.assetCollections.remove(assetCollection);
        assetCollection.getAssets().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asset)) {
            return false;
        }
        return getId() != null && getId().equals(((Asset) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asset{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", storageTypeUsed='" + getStorageTypeUsed() + "'" +
            ", fullFilenamePath='" + getFullFilenamePath() + "'" +
            ", status='" + getStatus() + "'" +
            ", preferredPurpose='" + getPreferredPurpose() + "'" +
            ", assetContentAsBlob='" + getAssetContentAsBlob() + "'" +
            ", assetContentAsBlobContentType='" + getAssetContentAsBlobContentType() + "'" +
            ", activationStatus='" + getActivationStatus() + "'" +
            "}";
    }
}
