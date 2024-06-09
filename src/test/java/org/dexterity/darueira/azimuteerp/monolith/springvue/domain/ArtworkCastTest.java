package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtistTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCastTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkTestSamples.*;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkCastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtworkCast.class);
        ArtworkCast artworkCast1 = getArtworkCastSample1();
        ArtworkCast artworkCast2 = new ArtworkCast();
        assertThat(artworkCast1).isNotEqualTo(artworkCast2);

        artworkCast2.setId(artworkCast1.getId());
        assertThat(artworkCast1).isEqualTo(artworkCast2);

        artworkCast2 = getArtworkCastSample2();
        assertThat(artworkCast1).isNotEqualTo(artworkCast2);
    }

    @Test
    void artworkTest() {
        ArtworkCast artworkCast = getArtworkCastRandomSampleGenerator();
        Artwork artworkBack = getArtworkRandomSampleGenerator();

        artworkCast.setArtwork(artworkBack);
        assertThat(artworkCast.getArtwork()).isEqualTo(artworkBack);

        artworkCast.artwork(null);
        assertThat(artworkCast.getArtwork()).isNull();
    }

    @Test
    void artistTest() {
        ArtworkCast artworkCast = getArtworkCastRandomSampleGenerator();
        Artist artistBack = getArtistRandomSampleGenerator();

        artworkCast.setArtist(artistBack);
        assertThat(artworkCast.getArtist()).isEqualTo(artistBack);

        artworkCast.artist(null);
        assertThat(artworkCast.getArtist()).isNull();
    }
}
