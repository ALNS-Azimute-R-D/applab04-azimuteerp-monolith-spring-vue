package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetMetadataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetMetadataDTO.class);
        AssetMetadataDTO assetMetadataDTO1 = new AssetMetadataDTO();
        assetMetadataDTO1.setId(1L);
        AssetMetadataDTO assetMetadataDTO2 = new AssetMetadataDTO();
        assertThat(assetMetadataDTO1).isNotEqualTo(assetMetadataDTO2);
        assetMetadataDTO2.setId(assetMetadataDTO1.getId());
        assertThat(assetMetadataDTO1).isEqualTo(assetMetadataDTO2);
        assetMetadataDTO2.setId(2L);
        assertThat(assetMetadataDTO1).isNotEqualTo(assetMetadataDTO2);
        assetMetadataDTO1.setId(null);
        assertThat(assetMetadataDTO1).isNotEqualTo(assetMetadataDTO2);
    }
}
