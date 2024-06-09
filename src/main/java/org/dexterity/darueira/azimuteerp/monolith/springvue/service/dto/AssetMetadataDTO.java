package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetMetadata} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetMetadataDTO implements Serializable {

    private Long id;

    @Size(max = 8192)
    private String metadataDetailsJSON;

    @NotNull
    private AssetDTO asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetadataDetailsJSON() {
        return metadataDetailsJSON;
    }

    public void setMetadataDetailsJSON(String metadataDetailsJSON) {
        this.metadataDetailsJSON = metadataDetailsJSON;
    }

    public AssetDTO getAsset() {
        return asset;
    }

    public void setAsset(AssetDTO asset) {
        this.asset = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetMetadataDTO)) {
            return false;
        }

        AssetMetadataDTO assetMetadataDTO = (AssetMetadataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assetMetadataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetMetadataDTO{" +
            "id=" + getId() +
            ", metadataDetailsJSON='" + getMetadataDetailsJSON() + "'" +
            ", asset=" + getAsset() +
            "}";
    }
}
