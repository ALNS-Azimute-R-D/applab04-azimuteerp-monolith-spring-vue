package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AssetCollectionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAssetCollectionAllPropertiesEquals(AssetCollection expected, AssetCollection actual) {
        assertAssetCollectionAutoGeneratedPropertiesEquals(expected, actual);
        assertAssetCollectionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAssetCollectionAllUpdatablePropertiesEquals(AssetCollection expected, AssetCollection actual) {
        assertAssetCollectionUpdatableFieldsEquals(expected, actual);
        assertAssetCollectionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAssetCollectionAutoGeneratedPropertiesEquals(AssetCollection expected, AssetCollection actual) {
        assertThat(expected)
            .as("Verify AssetCollection auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAssetCollectionUpdatableFieldsEquals(AssetCollection expected, AssetCollection actual) {
        assertThat(expected)
            .as("Verify AssetCollection relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getFullFilenamePath()).as("check fullFilenamePath").isEqualTo(actual.getFullFilenamePath()))
            .satisfies(e -> assertThat(e.getActivationStatus()).as("check activationStatus").isEqualTo(actual.getActivationStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAssetCollectionUpdatableRelationshipsEquals(AssetCollection expected, AssetCollection actual) {
        assertThat(expected)
            .as("Verify AssetCollection relationships")
            .satisfies(e -> assertThat(e.getAssets()).as("check assets").isEqualTo(actual.getAssets()))
            .satisfies(e -> assertThat(e.getArticles()).as("check articles").isEqualTo(actual.getArticles()))
            .satisfies(e -> assertThat(e.getEvents()).as("check events").isEqualTo(actual.getEvents()))
            .satisfies(e -> assertThat(e.getActivities()).as("check activities").isEqualTo(actual.getActivities()))
            .satisfies(
                e -> assertThat(e.getScheduledActivities()).as("check scheduledActivities").isEqualTo(actual.getScheduledActivities())
            );
    }
}
