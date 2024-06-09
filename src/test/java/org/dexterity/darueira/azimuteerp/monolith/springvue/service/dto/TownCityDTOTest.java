package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TownCityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownCityDTO.class);
        TownCityDTO townCityDTO1 = new TownCityDTO();
        townCityDTO1.setId(1L);
        TownCityDTO townCityDTO2 = new TownCityDTO();
        assertThat(townCityDTO1).isNotEqualTo(townCityDTO2);
        townCityDTO2.setId(townCityDTO1.getId());
        assertThat(townCityDTO1).isEqualTo(townCityDTO2);
        townCityDTO2.setId(2L);
        assertThat(townCityDTO1).isNotEqualTo(townCityDTO2);
        townCityDTO1.setId(null);
        assertThat(townCityDTO1).isNotEqualTo(townCityDTO2);
    }
}
