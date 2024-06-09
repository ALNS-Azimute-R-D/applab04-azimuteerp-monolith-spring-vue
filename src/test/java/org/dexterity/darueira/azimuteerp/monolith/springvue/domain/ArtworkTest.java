package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCastTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmediaTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtworkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artwork.class);
        Artwork artwork1 = getArtworkSample1();
        Artwork artwork2 = new Artwork();
        assertThat(artwork1).isNotEqualTo(artwork2);

        artwork2.setId(artwork1.getId());
        assertThat(artwork1).isEqualTo(artwork2);

        artwork2 = getArtworkSample2();
        assertThat(artwork1).isNotEqualTo(artwork2);
    }

    @Test
    void typeOfArtmediaTest() {
        Artwork artwork = getArtworkRandomSampleGenerator();
        TypeOfArtmedia typeOfArtmediaBack = getTypeOfArtmediaRandomSampleGenerator();

        artwork.setTypeOfArtmedia(typeOfArtmediaBack);
        assertThat(artwork.getTypeOfArtmedia()).isEqualTo(typeOfArtmediaBack);

        artwork.typeOfArtmedia(null);
        assertThat(artwork.getTypeOfArtmedia()).isNull();
    }

    @Test
    void artworkAggregatorTest() {
        Artwork artwork = getArtworkRandomSampleGenerator();
        Artwork artworkBack = getArtworkRandomSampleGenerator();

        artwork.setArtworkAggregator(artworkBack);
        assertThat(artwork.getArtworkAggregator()).isEqualTo(artworkBack);

        artwork.artworkAggregator(null);
        assertThat(artwork.getArtworkAggregator()).isNull();
    }

    @Test
    void artworkCastsListTest() {
        Artwork artwork = getArtworkRandomSampleGenerator();
        ArtworkCast artworkCastBack = getArtworkCastRandomSampleGenerator();

        artwork.addArtworkCastsList(artworkCastBack);
        assertThat(artwork.getArtworkCastsLists()).containsOnly(artworkCastBack);
        assertThat(artworkCastBack.getArtwork()).isEqualTo(artwork);

        artwork.removeArtworkCastsList(artworkCastBack);
        assertThat(artwork.getArtworkCastsLists()).doesNotContain(artworkCastBack);
        assertThat(artworkCastBack.getArtwork()).isNull();

        artwork.artworkCastsLists(new HashSet<>(Set.of(artworkCastBack)));
        assertThat(artwork.getArtworkCastsLists()).containsOnly(artworkCastBack);
        assertThat(artworkCastBack.getArtwork()).isEqualTo(artwork);

        artwork.setArtworkCastsLists(new HashSet<>());
        assertThat(artwork.getArtworkCastsLists()).doesNotContain(artworkCastBack);
        assertThat(artworkCastBack.getArtwork()).isNull();
    }

    @Test
    void linkedArtworksListTest() {
        Artwork artwork = getArtworkRandomSampleGenerator();
        Artwork artworkBack = getArtworkRandomSampleGenerator();

        artwork.addLinkedArtworksList(artworkBack);
        assertThat(artwork.getLinkedArtworksLists()).containsOnly(artworkBack);
        assertThat(artworkBack.getArtworkAggregator()).isEqualTo(artwork);

        artwork.removeLinkedArtworksList(artworkBack);
        assertThat(artwork.getLinkedArtworksLists()).doesNotContain(artworkBack);
        assertThat(artworkBack.getArtworkAggregator()).isNull();

        artwork.linkedArtworksLists(new HashSet<>(Set.of(artworkBack)));
        assertThat(artwork.getLinkedArtworksLists()).containsOnly(artworkBack);
        assertThat(artworkBack.getArtworkAggregator()).isEqualTo(artwork);

        artwork.setLinkedArtworksLists(new HashSet<>());
        assertThat(artwork.getLinkedArtworksLists()).doesNotContain(artworkBack);
        assertThat(artworkBack.getArtworkAggregator()).isNull();
    }
}
