package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtistTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtistTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfArtistTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfArtist.class);
        TypeOfArtist typeOfArtist1 = getTypeOfArtistSample1();
        TypeOfArtist typeOfArtist2 = new TypeOfArtist();
        assertThat(typeOfArtist1).isNotEqualTo(typeOfArtist2);

        typeOfArtist2.setId(typeOfArtist1.getId());
        assertThat(typeOfArtist1).isEqualTo(typeOfArtist2);

        typeOfArtist2 = getTypeOfArtistSample2();
        assertThat(typeOfArtist1).isNotEqualTo(typeOfArtist2);
    }

    @Test
    void artistsListTest() {
        TypeOfArtist typeOfArtist = getTypeOfArtistRandomSampleGenerator();
        Artist artistBack = getArtistRandomSampleGenerator();

        typeOfArtist.addArtistsList(artistBack);
        assertThat(typeOfArtist.getArtistsLists()).containsOnly(artistBack);
        assertThat(artistBack.getTypeOfArtist()).isEqualTo(typeOfArtist);

        typeOfArtist.removeArtistsList(artistBack);
        assertThat(typeOfArtist.getArtistsLists()).doesNotContain(artistBack);
        assertThat(artistBack.getTypeOfArtist()).isNull();

        typeOfArtist.artistsLists(new HashSet<>(Set.of(artistBack)));
        assertThat(typeOfArtist.getArtistsLists()).containsOnly(artistBack);
        assertThat(artistBack.getTypeOfArtist()).isEqualTo(typeOfArtist);

        typeOfArtist.setArtistsLists(new HashSet<>());
        assertThat(typeOfArtist.getArtistsLists()).doesNotContain(artistBack);
        assertThat(artistBack.getTypeOfArtist()).isNull();
    }
}
