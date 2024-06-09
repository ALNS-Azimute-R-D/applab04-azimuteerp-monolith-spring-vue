package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkCastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkCastDTO.class);
        ArtworkCastDTO artworkCastDTO1 = new ArtworkCastDTO();
        artworkCastDTO1.setId(1L);
        ArtworkCastDTO artworkCastDTO2 = new ArtworkCastDTO();
        assertThat(artworkCastDTO1).isNotEqualTo(artworkCastDTO2);
        artworkCastDTO2.setId(artworkCastDTO1.getId());
        assertThat(artworkCastDTO1).isEqualTo(artworkCastDTO2);
        artworkCastDTO2.setId(2L);
        assertThat(artworkCastDTO1).isNotEqualTo(artworkCastDTO2);
        artworkCastDTO1.setId(null);
        assertThat(artworkCastDTO1).isNotEqualTo(artworkCastDTO2);
    }
}
