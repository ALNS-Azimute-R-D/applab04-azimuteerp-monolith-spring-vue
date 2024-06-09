package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtistTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenreTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtisticGenreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtisticGenre.class);
        ArtisticGenre artisticGenre1 = getArtisticGenreSample1();
        ArtisticGenre artisticGenre2 = new ArtisticGenre();
        assertThat(artisticGenre1).isNotEqualTo(artisticGenre2);

        artisticGenre2.setId(artisticGenre1.getId());
        assertThat(artisticGenre1).isEqualTo(artisticGenre2);

        artisticGenre2 = getArtisticGenreSample2();
        assertThat(artisticGenre1).isNotEqualTo(artisticGenre2);
    }

    @Test
    void artisticGenreTest() {
        ArtisticGenre artisticGenre = getArtisticGenreRandomSampleGenerator();
        Artist artistBack = getArtistRandomSampleGenerator();

        artisticGenre.addArtisticGenre(artistBack);
        assertThat(artisticGenre.getArtisticGenres()).containsOnly(artistBack);
        assertThat(artistBack.getArtists()).containsOnly(artisticGenre);

        artisticGenre.removeArtisticGenre(artistBack);
        assertThat(artisticGenre.getArtisticGenres()).doesNotContain(artistBack);
        assertThat(artistBack.getArtists()).doesNotContain(artisticGenre);

        artisticGenre.artisticGenres(new HashSet<>(Set.of(artistBack)));
        assertThat(artisticGenre.getArtisticGenres()).containsOnly(artistBack);
        assertThat(artistBack.getArtists()).containsOnly(artisticGenre);

        artisticGenre.setArtisticGenres(new HashSet<>());
        assertThat(artisticGenre.getArtisticGenres()).doesNotContain(artistBack);
        assertThat(artistBack.getArtists()).doesNotContain(artisticGenre);
    }
}
