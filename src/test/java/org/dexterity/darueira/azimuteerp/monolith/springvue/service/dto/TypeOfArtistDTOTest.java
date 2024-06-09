package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfArtistDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfArtistDTO.class);
        TypeOfArtistDTO typeOfArtistDTO1 = new TypeOfArtistDTO();
        typeOfArtistDTO1.setId(1L);
        TypeOfArtistDTO typeOfArtistDTO2 = new TypeOfArtistDTO();
        assertThat(typeOfArtistDTO1).isNotEqualTo(typeOfArtistDTO2);
        typeOfArtistDTO2.setId(typeOfArtistDTO1.getId());
        assertThat(typeOfArtistDTO1).isEqualTo(typeOfArtistDTO2);
        typeOfArtistDTO2.setId(2L);
        assertThat(typeOfArtistDTO1).isNotEqualTo(typeOfArtistDTO2);
        typeOfArtistDTO1.setId(null);
        assertThat(typeOfArtistDTO1).isNotEqualTo(typeOfArtistDTO2);
    }
}
