package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollectionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetMetadataTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTypeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmpTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asset.class);
        Asset asset1 = getAssetSample1();
        Asset asset2 = new Asset();
        assertThat(asset1).isNotEqualTo(asset2);

        asset2.setId(asset1.getId());
        assertThat(asset1).isEqualTo(asset2);

        asset2 = getAssetSample2();
        assertThat(asset1).isNotEqualTo(asset2);
    }

    @Test
    void assetTypeTest() {
        Asset asset = getAssetRandomSampleGenerator();
        AssetType assetTypeBack = getAssetTypeRandomSampleGenerator();

        asset.setAssetType(assetTypeBack);
        assertThat(asset.getAssetType()).isEqualTo(assetTypeBack);

        asset.assetType(null);
        assertThat(asset.getAssetType()).isNull();
    }

    @Test
    void rawAssetProcTmpTest() {
        Asset asset = getAssetRandomSampleGenerator();
        RawAssetProcTmp rawAssetProcTmpBack = getRawAssetProcTmpRandomSampleGenerator();

        asset.setRawAssetProcTmp(rawAssetProcTmpBack);
        assertThat(asset.getRawAssetProcTmp()).isEqualTo(rawAssetProcTmpBack);

        asset.rawAssetProcTmp(null);
        assertThat(asset.getRawAssetProcTmp()).isNull();
    }

    @Test
    void assetMetadataTest() {
        Asset asset = getAssetRandomSampleGenerator();
        AssetMetadata assetMetadataBack = getAssetMetadataRandomSampleGenerator();

        asset.setAssetMetadata(assetMetadataBack);
        assertThat(asset.getAssetMetadata()).isEqualTo(assetMetadataBack);
        assertThat(assetMetadataBack.getAsset()).isEqualTo(asset);

        asset.assetMetadata(null);
        assertThat(asset.getAssetMetadata()).isNull();
        assertThat(assetMetadataBack.getAsset()).isNull();
    }

    @Test
    void assetCollectionTest() {
        Asset asset = getAssetRandomSampleGenerator();
        AssetCollection assetCollectionBack = getAssetCollectionRandomSampleGenerator();

        asset.addAssetCollection(assetCollectionBack);
        assertThat(asset.getAssetCollections()).containsOnly(assetCollectionBack);
        assertThat(assetCollectionBack.getAssets()).containsOnly(asset);

        asset.removeAssetCollection(assetCollectionBack);
        assertThat(asset.getAssetCollections()).doesNotContain(assetCollectionBack);
        assertThat(assetCollectionBack.getAssets()).doesNotContain(asset);

        asset.assetCollections(new HashSet<>(Set.of(assetCollectionBack)));
        assertThat(asset.getAssetCollections()).containsOnly(assetCollectionBack);
        assertThat(assetCollectionBack.getAssets()).containsOnly(asset);

        asset.setAssetCollections(new HashSet<>());
        assertThat(asset.getAssetCollections()).doesNotContain(assetCollectionBack);
        assertThat(assetCollectionBack.getAssets()).doesNotContain(asset);
    }
}
