package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OrganizationAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrganizationAllPropertiesEquals(Organization expected, Organization actual) {
        assertOrganizationAutoGeneratedPropertiesEquals(expected, actual);
        assertOrganizationAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrganizationAllUpdatablePropertiesEquals(Organization expected, Organization actual) {
        assertOrganizationUpdatableFieldsEquals(expected, actual);
        assertOrganizationUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrganizationAutoGeneratedPropertiesEquals(Organization expected, Organization actual) {
        assertThat(expected)
            .as("Verify Organization auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrganizationUpdatableFieldsEquals(Organization expected, Organization actual) {
        assertThat(expected)
            .as("Verify Organization relevant properties")
            .satisfies(e -> assertThat(e.getAcronym()).as("check acronym").isEqualTo(actual.getAcronym()))
            .satisfies(e -> assertThat(e.getBusinessCode()).as("check businessCode").isEqualTo(actual.getBusinessCode()))
            .satisfies(e -> assertThat(e.getHierarchicalLevel()).as("check hierarchicalLevel").isEqualTo(actual.getHierarchicalLevel()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(
                e -> assertThat(e.getBusinessHandlerClazz()).as("check businessHandlerClazz").isEqualTo(actual.getBusinessHandlerClazz())
            )
            .satisfies(
                e ->
                    assertThat(e.getMainContactPersonDetailsJSON())
                        .as("check mainContactPersonDetailsJSON")
                        .isEqualTo(actual.getMainContactPersonDetailsJSON())
            )
            .satisfies(
                e ->
                    assertThat(e.getTechnicalEnvironmentsDetailsJSON())
                        .as("check technicalEnvironmentsDetailsJSON")
                        .isEqualTo(actual.getTechnicalEnvironmentsDetailsJSON())
            )
            .satisfies(
                e ->
                    assertThat(e.getCustomAttributesDetailsJSON())
                        .as("check customAttributesDetailsJSON")
                        .isEqualTo(actual.getCustomAttributesDetailsJSON())
            )
            .satisfies(e -> assertThat(e.getOrganizationStatus()).as("check organizationStatus").isEqualTo(actual.getOrganizationStatus()))
            .satisfies(e -> assertThat(e.getActivationStatus()).as("check activationStatus").isEqualTo(actual.getActivationStatus()))
            .satisfies(e -> assertThat(e.getLogoImg()).as("check logoImg").isEqualTo(actual.getLogoImg()))
            .satisfies(
                e -> assertThat(e.getLogoImgContentType()).as("check logoImg contenty type").isEqualTo(actual.getLogoImgContentType())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrganizationUpdatableRelationshipsEquals(Organization expected, Organization actual) {
        assertThat(expected)
            .as("Verify Organization relationships")
            .satisfies(e -> assertThat(e.getTenant()).as("check tenant").isEqualTo(actual.getTenant()))
            .satisfies(e -> assertThat(e.getTypeOfOrganization()).as("check typeOfOrganization").isEqualTo(actual.getTypeOfOrganization()))
            .satisfies(e -> assertThat(e.getOrganizationParent()).as("check organizationParent").isEqualTo(actual.getOrganizationParent()));
    }
}
