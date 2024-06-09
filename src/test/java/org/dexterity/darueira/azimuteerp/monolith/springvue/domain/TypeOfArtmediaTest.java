package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtistTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmediaTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfArtmediaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfArtmedia.class);
        TypeOfArtmedia typeOfArtmedia1 = getTypeOfArtmediaSample1();
        TypeOfArtmedia typeOfArtmedia2 = new TypeOfArtmedia();
        assertThat(typeOfArtmedia1).isNotEqualTo(typeOfArtmedia2);

        typeOfArtmedia2.setId(typeOfArtmedia1.getId());
        assertThat(typeOfArtmedia1).isEqualTo(typeOfArtmedia2);

        typeOfArtmedia2 = getTypeOfArtmediaSample2();
        assertThat(typeOfArtmedia1).isNotEqualTo(typeOfArtmedia2);
    }

    @Test
    void artistsListTest() {
        TypeOfArtmedia typeOfArtmedia = getTypeOfArtmediaRandomSampleGenerator();
        Artist artistBack = getArtistRandomSampleGenerator();

        typeOfArtmedia.addArtistsList(artistBack);
        assertThat(typeOfArtmedia.getArtistsLists()).containsOnly(artistBack);
        assertThat(artistBack.getTypeOfArtmedia()).isEqualTo(typeOfArtmedia);

        typeOfArtmedia.removeArtistsList(artistBack);
        assertThat(typeOfArtmedia.getArtistsLists()).doesNotContain(artistBack);
        assertThat(artistBack.getTypeOfArtmedia()).isNull();

        typeOfArtmedia.artistsLists(new HashSet<>(Set.of(artistBack)));
        assertThat(typeOfArtmedia.getArtistsLists()).containsOnly(artistBack);
        assertThat(artistBack.getTypeOfArtmedia()).isEqualTo(typeOfArtmedia);

        typeOfArtmedia.setArtistsLists(new HashSet<>());
        assertThat(typeOfArtmedia.getArtistsLists()).doesNotContain(artistBack);
        assertThat(artistBack.getTypeOfArtmedia()).isNull();
    }

    @Test
    void artworksListTest() {
        TypeOfArtmedia typeOfArtmedia = getTypeOfArtmediaRandomSampleGenerator();
        Artwork artworkBack = getArtworkRandomSampleGenerator();

        typeOfArtmedia.addArtworksList(artworkBack);
        assertThat(typeOfArtmedia.getArtworksLists()).containsOnly(artworkBack);
        assertThat(artworkBack.getTypeOfArtmedia()).isEqualTo(typeOfArtmedia);

        typeOfArtmedia.removeArtworksList(artworkBack);
        assertThat(typeOfArtmedia.getArtworksLists()).doesNotContain(artworkBack);
        assertThat(artworkBack.getTypeOfArtmedia()).isNull();

        typeOfArtmedia.artworksLists(new HashSet<>(Set.of(artworkBack)));
        assertThat(typeOfArtmedia.getArtworksLists()).containsOnly(artworkBack);
        assertThat(artworkBack.getTypeOfArtmedia()).isEqualTo(typeOfArtmedia);

        typeOfArtmedia.setArtworksLists(new HashSet<>());
        assertThat(typeOfArtmedia.getArtworksLists()).doesNotContain(artworkBack);
        assertThat(artworkBack.getTypeOfArtmedia()).isNull();
    }
}
