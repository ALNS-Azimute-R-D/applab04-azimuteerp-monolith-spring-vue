package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ArtistAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtistAllPropertiesEquals(Artist expected, Artist actual) {
        assertArtistAutoGeneratedPropertiesEquals(expected, actual);
        assertArtistAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtistAllUpdatablePropertiesEquals(Artist expected, Artist actual) {
        assertArtistUpdatableFieldsEquals(expected, actual);
        assertArtistUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtistAutoGeneratedPropertiesEquals(Artist expected, Artist actual) {
        assertThat(expected)
            .as("Verify Artist auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtistUpdatableFieldsEquals(Artist expected, Artist actual) {
        assertThat(expected)
            .as("Verify Artist relevant properties")
            .satisfies(e -> assertThat(e.getAcronym()).as("check acronym").isEqualTo(actual.getAcronym()))
            .satisfies(e -> assertThat(e.getPublicName()).as("check publicName").isEqualTo(actual.getPublicName()))
            .satisfies(e -> assertThat(e.getRealName()).as("check realName").isEqualTo(actual.getRealName()))
            .satisfies(
                e -> assertThat(e.getBiographyDetailsJSON()).as("check biographyDetailsJSON").isEqualTo(actual.getBiographyDetailsJSON())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArtistUpdatableRelationshipsEquals(Artist expected, Artist actual) {
        assertThat(expected)
            .as("Verify Artist relationships")
            .satisfies(e -> assertThat(e.getTypeOfArtmedia()).as("check typeOfArtmedia").isEqualTo(actual.getTypeOfArtmedia()))
            .satisfies(e -> assertThat(e.getTypeOfArtist()).as("check typeOfArtist").isEqualTo(actual.getTypeOfArtist()))
            .satisfies(e -> assertThat(e.getArtistAggregator()).as("check artistAggregator").isEqualTo(actual.getArtistAggregator()))
            .satisfies(e -> assertThat(e.getArtists()).as("check artists").isEqualTo(actual.getArtists()));
    }
}