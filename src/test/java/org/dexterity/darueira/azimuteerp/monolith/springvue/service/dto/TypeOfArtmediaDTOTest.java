package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfArtmediaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfArtmediaDTO.class);
        TypeOfArtmediaDTO typeOfArtmediaDTO1 = new TypeOfArtmediaDTO();
        typeOfArtmediaDTO1.setId(1L);
        TypeOfArtmediaDTO typeOfArtmediaDTO2 = new TypeOfArtmediaDTO();
        assertThat(typeOfArtmediaDTO1).isNotEqualTo(typeOfArtmediaDTO2);
        typeOfArtmediaDTO2.setId(typeOfArtmediaDTO1.getId());
        assertThat(typeOfArtmediaDTO1).isEqualTo(typeOfArtmediaDTO2);
        typeOfArtmediaDTO2.setId(2L);
        assertThat(typeOfArtmediaDTO1).isNotEqualTo(typeOfArtmediaDTO2);
        typeOfArtmediaDTO1.setId(null);
        assertThat(typeOfArtmediaDTO1).isNotEqualTo(typeOfArtmediaDTO2);
    }
}
