package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfEventDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfEventDTO.class);
        TypeOfEventDTO typeOfEventDTO1 = new TypeOfEventDTO();
        typeOfEventDTO1.setId(1L);
        TypeOfEventDTO typeOfEventDTO2 = new TypeOfEventDTO();
        assertThat(typeOfEventDTO1).isNotEqualTo(typeOfEventDTO2);
        typeOfEventDTO2.setId(typeOfEventDTO1.getId());
        assertThat(typeOfEventDTO1).isEqualTo(typeOfEventDTO2);
        typeOfEventDTO2.setId(2L);
        assertThat(typeOfEventDTO1).isNotEqualTo(typeOfEventDTO2);
        typeOfEventDTO1.setId(null);
        assertThat(typeOfEventDTO1).isNotEqualTo(typeOfEventDTO2);
    }
}
