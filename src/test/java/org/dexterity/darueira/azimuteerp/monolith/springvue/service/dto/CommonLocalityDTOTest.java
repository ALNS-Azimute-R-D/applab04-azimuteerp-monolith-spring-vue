package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommonLocalityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonLocalityDTO.class);
        CommonLocalityDTO commonLocalityDTO1 = new CommonLocalityDTO();
        commonLocalityDTO1.setId(1L);
        CommonLocalityDTO commonLocalityDTO2 = new CommonLocalityDTO();
        assertThat(commonLocalityDTO1).isNotEqualTo(commonLocalityDTO2);
        commonLocalityDTO2.setId(commonLocalityDTO1.getId());
        assertThat(commonLocalityDTO1).isEqualTo(commonLocalityDTO2);
        commonLocalityDTO2.setId(2L);
        assertThat(commonLocalityDTO1).isNotEqualTo(commonLocalityDTO2);
        commonLocalityDTO1.setId(null);
        assertThat(commonLocalityDTO1).isNotEqualTo(commonLocalityDTO2);
    }
}
