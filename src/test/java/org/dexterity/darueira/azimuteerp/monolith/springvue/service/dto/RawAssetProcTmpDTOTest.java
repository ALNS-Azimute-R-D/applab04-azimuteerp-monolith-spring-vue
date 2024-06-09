package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RawAssetProcTmpDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RawAssetProcTmpDTO.class);
        RawAssetProcTmpDTO rawAssetProcTmpDTO1 = new RawAssetProcTmpDTO();
        rawAssetProcTmpDTO1.setId(1L);
        RawAssetProcTmpDTO rawAssetProcTmpDTO2 = new RawAssetProcTmpDTO();
        assertThat(rawAssetProcTmpDTO1).isNotEqualTo(rawAssetProcTmpDTO2);
        rawAssetProcTmpDTO2.setId(rawAssetProcTmpDTO1.getId());
        assertThat(rawAssetProcTmpDTO1).isEqualTo(rawAssetProcTmpDTO2);
        rawAssetProcTmpDTO2.setId(2L);
        assertThat(rawAssetProcTmpDTO1).isNotEqualTo(rawAssetProcTmpDTO2);
        rawAssetProcTmpDTO1.setId(null);
        assertThat(rawAssetProcTmpDTO1).isNotEqualTo(rawAssetProcTmpDTO2);
    }
}
