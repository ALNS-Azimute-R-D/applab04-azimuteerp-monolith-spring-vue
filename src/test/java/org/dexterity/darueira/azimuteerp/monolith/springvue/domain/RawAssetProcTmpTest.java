package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTypeTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmpTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RawAssetProcTmpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RawAssetProcTmp.class);
        RawAssetProcTmp rawAssetProcTmp1 = getRawAssetProcTmpSample1();
        RawAssetProcTmp rawAssetProcTmp2 = new RawAssetProcTmp();
        assertThat(rawAssetProcTmp1).isNotEqualTo(rawAssetProcTmp2);

        rawAssetProcTmp2.setId(rawAssetProcTmp1.getId());
        assertThat(rawAssetProcTmp1).isEqualTo(rawAssetProcTmp2);

        rawAssetProcTmp2 = getRawAssetProcTmpSample2();
        assertThat(rawAssetProcTmp1).isNotEqualTo(rawAssetProcTmp2);
    }

    @Test
    void assetTypeTest() {
        RawAssetProcTmp rawAssetProcTmp = getRawAssetProcTmpRandomSampleGenerator();
        AssetType assetTypeBack = getAssetTypeRandomSampleGenerator();

        rawAssetProcTmp.setAssetType(assetTypeBack);
        assertThat(rawAssetProcTmp.getAssetType()).isEqualTo(assetTypeBack);

        rawAssetProcTmp.assetType(null);
        assertThat(rawAssetProcTmp.getAssetType()).isNull();
    }

    @Test
    void assetsTest() {
        RawAssetProcTmp rawAssetProcTmp = getRawAssetProcTmpRandomSampleGenerator();
        Asset assetBack = getAssetRandomSampleGenerator();

        rawAssetProcTmp.addAssets(assetBack);
        assertThat(rawAssetProcTmp.getAssets()).containsOnly(assetBack);
        assertThat(assetBack.getRawAssetProcTmp()).isEqualTo(rawAssetProcTmp);

        rawAssetProcTmp.removeAssets(assetBack);
        assertThat(rawAssetProcTmp.getAssets()).doesNotContain(assetBack);
        assertThat(assetBack.getRawAssetProcTmp()).isNull();

        rawAssetProcTmp.assets(new HashSet<>(Set.of(assetBack)));
        assertThat(rawAssetProcTmp.getAssets()).containsOnly(assetBack);
        assertThat(assetBack.getRawAssetProcTmp()).isEqualTo(rawAssetProcTmp);

        rawAssetProcTmp.setAssets(new HashSet<>());
        assertThat(rawAssetProcTmp.getAssets()).doesNotContain(assetBack);
        assertThat(assetBack.getRawAssetProcTmp()).isNull();
    }
}
