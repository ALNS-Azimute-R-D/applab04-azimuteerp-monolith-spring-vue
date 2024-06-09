package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtistTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtistTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenreTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCastTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtistTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmediaTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtistTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artist.class);
        Artist artist1 = getArtistSample1();
        Artist artist2 = new Artist();
        assertThat(artist1).isNotEqualTo(artist2);

        artist2.setId(artist1.getId());
        assertThat(artist1).isEqualTo(artist2);

        artist2 = getArtistSample2();
        assertThat(artist1).isNotEqualTo(artist2);
    }

    @Test
    void typeOfArtmediaTest() {
        Artist artist = getArtistRandomSampleGenerator();
        TypeOfArtmedia typeOfArtmediaBack = getTypeOfArtmediaRandomSampleGenerator();

        artist.setTypeOfArtmedia(typeOfArtmediaBack);
        assertThat(artist.getTypeOfArtmedia()).isEqualTo(typeOfArtmediaBack);

        artist.typeOfArtmedia(null);
        assertThat(artist.getTypeOfArtmedia()).isNull();
    }

    @Test
    void typeOfArtistTest() {
        Artist artist = getArtistRandomSampleGenerator();
        TypeOfArtist typeOfArtistBack = getTypeOfArtistRandomSampleGenerator();

        artist.setTypeOfArtist(typeOfArtistBack);
        assertThat(artist.getTypeOfArtist()).isEqualTo(typeOfArtistBack);

        artist.typeOfArtist(null);
        assertThat(artist.getTypeOfArtist()).isNull();
    }

    @Test
    void artistAggregatorTest() {
        Artist artist = getArtistRandomSampleGenerator();
        Artist artistBack = getArtistRandomSampleGenerator();

        artist.setArtistAggregator(artistBack);
        assertThat(artist.getArtistAggregator()).isEqualTo(artistBack);

        artist.artistAggregator(null);
        assertThat(artist.getArtistAggregator()).isNull();
    }

    @Test
    void artistTest() {
        Artist artist = getArtistRandomSampleGenerator();
        ArtisticGenre artisticGenreBack = getArtisticGenreRandomSampleGenerator();

        artist.addArtist(artisticGenreBack);
        assertThat(artist.getArtists()).containsOnly(artisticGenreBack);

        artist.removeArtist(artisticGenreBack);
        assertThat(artist.getArtists()).doesNotContain(artisticGenreBack);

        artist.artists(new HashSet<>(Set.of(artisticGenreBack)));
        assertThat(artist.getArtists()).containsOnly(artisticGenreBack);

        artist.setArtists(new HashSet<>());
        assertThat(artist.getArtists()).doesNotContain(artisticGenreBack);
    }

    @Test
    void artworkCastsListTest() {
        Artist artist = getArtistRandomSampleGenerator();
        ArtworkCast artworkCastBack = getArtworkCastRandomSampleGenerator();

        artist.addArtworkCastsList(artworkCastBack);
        assertThat(artist.getArtworkCastsLists()).containsOnly(artworkCastBack);
        assertThat(artworkCastBack.getArtist()).isEqualTo(artist);

        artist.removeArtworkCastsList(artworkCastBack);
        assertThat(artist.getArtworkCastsLists()).doesNotContain(artworkCastBack);
        assertThat(artworkCastBack.getArtist()).isNull();

        artist.artworkCastsLists(new HashSet<>(Set.of(artworkCastBack)));
        assertThat(artist.getArtworkCastsLists()).containsOnly(artworkCastBack);
        assertThat(artworkCastBack.getArtist()).isEqualTo(artist);

        artist.setArtworkCastsLists(new HashSet<>());
        assertThat(artist.getArtworkCastsLists()).doesNotContain(artworkCastBack);
        assertThat(artworkCastBack.getArtist()).isNull();
    }

    @Test
    void linkedArtistsListTest() {
        Artist artist = getArtistRandomSampleGenerator();
        Artist artistBack = getArtistRandomSampleGenerator();

        artist.addLinkedArtistsList(artistBack);
        assertThat(artist.getLinkedArtistsLists()).containsOnly(artistBack);
        assertThat(artistBack.getArtistAggregator()).isEqualTo(artist);

        artist.removeLinkedArtistsList(artistBack);
        assertThat(artist.getLinkedArtistsLists()).doesNotContain(artistBack);
        assertThat(artistBack.getArtistAggregator()).isNull();

        artist.linkedArtistsLists(new HashSet<>(Set.of(artistBack)));
        assertThat(artist.getLinkedArtistsLists()).containsOnly(artistBack);
        assertThat(artistBack.getArtistAggregator()).isEqualTo(artist);

        artist.setLinkedArtistsLists(new HashSet<>());
        assertThat(artist.getLinkedArtistsLists()).doesNotContain(artistBack);
        assertThat(artistBack.getArtistAggregator()).isNull();
    }
}
