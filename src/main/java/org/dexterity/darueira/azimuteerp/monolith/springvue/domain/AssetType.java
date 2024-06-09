package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * - RawAssetProcTmp
 * - AssetType
 * - Asset
 * - AssetMetadata
 */
@Entity
@Table(name = "tb_type_asset")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "acronym", length = 30)
    private String acronym;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "handler_clazz_name", length = 255)
    private String handlerClazzName;

    @Size(max = 2048)
    @Column(name = "custom_attributes_details_json", length = 2048)
    private String customAttributesDetailsJSON;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assetType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetType", "assets" }, allowSetters = true)
    private Set<RawAssetProcTmp> rawAssetsProcsTmps = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assetType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assetType", "rawAssetProcTmp", "assetMetadata", "assetCollections" }, allowSetters = true)
    private Set<Asset> assets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AssetType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public AssetType acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return this.name;
    }

    public AssetType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public AssetType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHandlerClazzName() {
        return this.handlerClazzName;
    }

    public AssetType handlerClazzName(String handlerClazzName) {
        this.setHandlerClazzName(handlerClazzName);
        return this;
    }

    public void setHandlerClazzName(String handlerClazzName) {
        this.handlerClazzName = handlerClazzName;
    }

    public String getCustomAttributesDetailsJSON() {
        return this.customAttributesDetailsJSON;
    }

    public AssetType customAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.setCustomAttributesDetailsJSON(customAttributesDetailsJSON);
        return this;
    }

    public void setCustomAttributesDetailsJSON(String customAttributesDetailsJSON) {
        this.customAttributesDetailsJSON = customAttributesDetailsJSON;
    }

    public Set<RawAssetProcTmp> getRawAssetsProcsTmps() {
        return this.rawAssetsProcsTmps;
    }

    public void setRawAssetsProcsTmps(Set<RawAssetProcTmp> rawAssetProcTmps) {
        if (this.rawAssetsProcsTmps != null) {
            this.rawAssetsProcsTmps.forEach(i -> i.setAssetType(null));
        }
        if (rawAssetProcTmps != null) {
            rawAssetProcTmps.forEach(i -> i.setAssetType(this));
        }
        this.rawAssetsProcsTmps = rawAssetProcTmps;
    }

    public AssetType rawAssetsProcsTmps(Set<RawAssetProcTmp> rawAssetProcTmps) {
        this.setRawAssetsProcsTmps(rawAssetProcTmps);
        return this;
    }

    public AssetType addRawAssetsProcsTmps(RawAssetProcTmp rawAssetProcTmp) {
        this.rawAssetsProcsTmps.add(rawAssetProcTmp);
        rawAssetProcTmp.setAssetType(this);
        return this;
    }

    public AssetType removeRawAssetsProcsTmps(RawAssetProcTmp rawAssetProcTmp) {
        this.rawAssetsProcsTmps.remove(rawAssetProcTmp);
        rawAssetProcTmp.setAssetType(null);
        return this;
    }

    public Set<Asset> getAssets() {
        return this.assets;
    }

    public void setAssets(Set<Asset> assets) {
        if (this.assets != null) {
            this.assets.forEach(i -> i.setAssetType(null));
        }
        if (assets != null) {
            assets.forEach(i -> i.setAssetType(this));
        }
        this.assets = assets;
    }

    public AssetType assets(Set<Asset> assets) {
        this.setAssets(assets);
        return this;
    }

    public AssetType addAssets(Asset asset) {
        this.assets.add(asset);
        asset.setAssetType(this);
        return this;
    }

    public AssetType removeAssets(Asset asset) {
        this.assets.remove(asset);
        asset.setAssetType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetType)) {
            return false;
        }
        return getId() != null && getId().equals(((AssetType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetType{" +
            "id=" + getId() +
            ", acronym='" + getAcronym() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", handlerClazzName='" + getHandlerClazzName() + "'" +
            ", customAttributesDetailsJSON='" + getCustomAttributesDetailsJSON() + "'" +
            "}";
    }
}
