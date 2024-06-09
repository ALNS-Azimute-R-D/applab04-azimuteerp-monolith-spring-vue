package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivityAllPropertiesEquals(Activity expected, Activity actual) {
        assertActivityAutoGeneratedPropertiesEquals(expected, actual);
        assertActivityAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivityAllUpdatablePropertiesEquals(Activity expected, Activity actual) {
        assertActivityUpdatableFieldsEquals(expected, actual);
        assertActivityUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivityAutoGeneratedPropertiesEquals(Activity expected, Activity actual) {
        assertThat(expected)
            .as("Verify Activity auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivityUpdatableFieldsEquals(Activity expected, Activity actual) {
        assertThat(expected)
            .as("Verify Activity relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getShortDescription()).as("check shortDescription").isEqualTo(actual.getShortDescription()))
            .satisfies(e -> assertThat(e.getFullDescription()).as("check fullDescription").isEqualTo(actual.getFullDescription()))
            .satisfies(e -> assertThat(e.getMainGoals()).as("check mainGoals").isEqualTo(actual.getMainGoals()))
            .satisfies(
                e -> assertThat(e.getEstimatedDurationTime()).as("check estimatedDurationTime").isEqualTo(actual.getEstimatedDurationTime())
            )
            .satisfies(e -> assertThat(e.getLastPerformedDate()).as("check lastPerformedDate").isEqualTo(actual.getLastPerformedDate()))
            .satisfies(e -> assertThat(e.getCreatedAt()).as("check createdAt").isEqualTo(actual.getCreatedAt()))
            .satisfies(e -> assertThat(e.getActivationStatus()).as("check activationStatus").isEqualTo(actual.getActivationStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivityUpdatableRelationshipsEquals(Activity expected, Activity actual) {
        assertThat(expected)
            .as("Verify Activity relationships")
            .satisfies(e -> assertThat(e.getTypeOfActivity()).as("check typeOfActivity").isEqualTo(actual.getTypeOfActivity()))
            .satisfies(e -> assertThat(e.getCreatedByUser()).as("check createdByUser").isEqualTo(actual.getCreatedByUser()))
            .satisfies(e -> assertThat(e.getAssetCollections()).as("check assetCollections").isEqualTo(actual.getAssetCollections()));
    }
}