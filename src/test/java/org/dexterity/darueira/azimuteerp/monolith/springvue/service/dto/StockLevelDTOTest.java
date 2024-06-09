package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StockLevelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockLevelDTO.class);
        StockLevelDTO stockLevelDTO1 = new StockLevelDTO();
        stockLevelDTO1.setId(1L);
        StockLevelDTO stockLevelDTO2 = new StockLevelDTO();
        assertThat(stockLevelDTO1).isNotEqualTo(stockLevelDTO2);
        stockLevelDTO2.setId(stockLevelDTO1.getId());
        assertThat(stockLevelDTO1).isEqualTo(stockLevelDTO2);
        stockLevelDTO2.setId(2L);
        assertThat(stockLevelDTO1).isNotEqualTo(stockLevelDTO2);
        stockLevelDTO1.setId(null);
        assertThat(stockLevelDTO1).isNotEqualTo(stockLevelDTO2);
    }
}
