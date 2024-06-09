package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.StatusRawProcessingEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RawAssetProcTmp.
 */
@Entity
@Table(name = "tb_raw_asset_proc_tmp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RawAssetProcTmp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_raw_processing")
    private StatusRawProcessingEnum statusRawProcessing;

    @Size(max = 512)
    @Column(name = "full_filename_path", length = 512)
    private String fullFilenamePath;

    @Lob
    @Column(name = "asset_raw_content_as_blob")
    private byte[] assetRawContentAsBlob;

    @Column(name = "asset_raw_content_as_blob_content_type")
    private String assetRawContentAsBlobContentType;

    @Size(max = 4096)
    @Column(name = "custom_attributes_details_json", length = 4096)
    private String customAttributesDetailsJSON;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "rawAssetsProcsTmps", "assets" }, allowSetters = true)
    private AssetType assetType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rawAssetProcTmp")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetType", "rawAssetProcTmp", "assetMetadata", "assetCollections" }, allowSetters = true)
    private Set<Asset> assets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RawAssetProcTmp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RawAssetProcTmp name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusRawProcessingEnum getStatusRawProcessing() {
        return this.statusRawProcessing;
    }

    public RawAssetProcTmp statusRawProcessing(StatusRawProcessingEnum statusRawProcessing) {
        this.setStatusRawProcessing(statusRawProcessing);
        return this;
    }

    public void setStatusRawProcessing(StatusRawProcessingEnum statusRawProcessing) {
        this.statusRawProcessing = statusRawProcessing;
    }

    public String getFullFilenamePath() {
        return this.fullFilenamePath;
    }

    public RawAssetProcTmp fullFilenamePath(String fullFilenamePath) {
        this.setFullFilenamePath(fullFilenamePath);
        return this;
    }

    public void setFullFilenamePath(String fullFilenamePath) {
        this.fullFilenamePath = fullFilenamePath;
    }

    public byte[] getAssetRawContentAsBlob() {
        return this.assetRawContentAsBlob;
    }

    public RawAssetProcTmp assetRawContentAsBlob(byte[] assetRawContentAsBlob) {
        this.setAssetRawContentAsBlob(assetRawContentAsBlob);
        return this;
    }

    public void setAssetRawContentAsBlob(byte[] assetRawContentAsBlob) {
        this.assetRawContentAsBlob = assetRawContentAsBlob;
    }

    public String getAssetRawContentAsBlobContentType() {
        return this.assetRawContentAsBlobContentType;
    }

    public RawAssetProcTmp assetRawContentAsBlobContentType(String assetRawContentAsBlobContentType) {
        this.assetRawContentAsBlobContentType = assetRawContentAsBlobContentType;
        return this;
    }

    public void setAssetRawContentAsBlobContentType(String assetRawContentAsBlobContentType) {
        this.assetRawContentAsBlobContentType = assetRawContentAsBlobContentType;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public RawAssetProcTmp customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public RawAssetProcTmp assetType(AssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public Set<Asset> getAssets() {
        return this.assets;
    }

    public void setAssets(Set<Asset> assets) {
        if (this.assets != null) {
            this.assets.forEach(i -> i.setRawAssetProcTmp(null));
        }
        if (assets != null) {
            assets.forEach(i -> i.setRawAssetProcTmp(this));
        }
        this.assets = assets;
    }

    public RawAssetProcTmp assets(Set<Asset> assets) {
        this.setAssets(assets);
        return this;
    }

    public RawAssetProcTmp addAssets(Asset asset) {
        this.assets.add(asset);
        asset.setRawAssetProcTmp(this);
        return this;
    }

    public RawAssetProcTmp removeAssets(Asset asset) {
        this.assets.remove(asset);
        asset.setRawAssetProcTmp(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawAssetProcTmp)) {
            return false;
        }
        return getId() != null && getId().equals(((RawAssetProcTmp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawAssetProcTmp{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", statusRawProcessing='" + getStatusRawProcessing() + "'" +
            ", fullFilenamePath='" + getFullFilenamePath() + "'" +
            ", assetRawContentAsBlob='" + getAssetRawContentAsBlob() + "'" +
            ", assetRawContentAsBlobContentType='" + getAssetRawContentAsBlobContentType() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            "}";
    }
}
