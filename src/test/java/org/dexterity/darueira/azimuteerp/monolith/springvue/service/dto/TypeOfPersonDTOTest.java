package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfPersonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfPersonDTO.class);
        TypeOfPersonDTO typeOfPersonDTO1 = new TypeOfPersonDTO();
        typeOfPersonDTO1.setId(1L);
        TypeOfPersonDTO typeOfPersonDTO2 = new TypeOfPersonDTO();
        assertThat(typeOfPersonDTO1).isNotEqualTo(typeOfPersonDTO2);
        typeOfPersonDTO2.setId(typeOfPersonDTO1.getId());
        assertThat(typeOfPersonDTO1).isEqualTo(typeOfPersonDTO2);
        typeOfPersonDTO2.setId(2L);
        assertThat(typeOfPersonDTO1).isNotEqualTo(typeOfPersonDTO2);
        typeOfPersonDTO1.setId(null);
        assertThat(typeOfPersonDTO1).isNotEqualTo(typeOfPersonDTO2);
    }
}
