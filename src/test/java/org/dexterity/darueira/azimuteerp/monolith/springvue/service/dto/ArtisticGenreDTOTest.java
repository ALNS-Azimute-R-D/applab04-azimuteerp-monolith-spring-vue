package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtisticGenreDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtisticGenreDTO.class);
        ArtisticGenreDTO artisticGenreDTO1 = new ArtisticGenreDTO();
        artisticGenreDTO1.setId(1L);
        ArtisticGenreDTO artisticGenreDTO2 = new ArtisticGenreDTO();
        assertThat(artisticGenreDTO1).isNotEqualTo(artisticGenreDTO2);
        artisticGenreDTO2.setId(artisticGenreDTO1.getId());
        assertThat(artisticGenreDTO1).isEqualTo(artisticGenreDTO2);
        artisticGenreDTO2.setId(2L);
        assertThat(artisticGenreDTO1).isNotEqualTo(artisticGenreDTO2);
        artisticGenreDTO1.setId(null);
        assertThat(artisticGenreDTO1).isNotEqualTo(artisticGenreDTO2);
    }
}
