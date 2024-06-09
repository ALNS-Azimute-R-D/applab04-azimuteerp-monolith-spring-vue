package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfActivityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfActivityDTO.class);
        TypeOfActivityDTO typeOfActivityDTO1 = new TypeOfActivityDTO();
        typeOfActivityDTO1.setId(1L);
        TypeOfActivityDTO typeOfActivityDTO2 = new TypeOfActivityDTO();
        assertThat(typeOfActivityDTO1).isNotEqualTo(typeOfActivityDTO2);
        typeOfActivityDTO2.setId(typeOfActivityDTO1.getId());
        assertThat(typeOfActivityDTO1).isEqualTo(typeOfActivityDTO2);
        typeOfActivityDTO2.setId(2L);
        assertThat(typeOfActivityDTO1).isNotEqualTo(typeOfActivityDTO2);
        typeOfActivityDTO1.setId(null);
        assertThat(typeOfActivityDTO1).isNotEqualTo(typeOfActivityDTO2);
    }
}
