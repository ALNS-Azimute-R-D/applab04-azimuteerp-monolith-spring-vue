package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssertUtils.bigDecimalCompareTo;

public class OrderAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderAllPropertiesEquals(Order expected, Order actual) {
        assertOrderAutoGeneratedPropertiesEquals(expected, actual);
        assertOrderAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderAllUpdatablePropertiesEquals(Order expected, Order actual) {
        assertOrderUpdatableFieldsEquals(expected, actual);
        assertOrderUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderAutoGeneratedPropertiesEquals(Order expected, Order actual) {
        assertThat(expected)
            .as("Verify Order auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderUpdatableFieldsEquals(Order expected, Order actual) {
        assertThat(expected)
            .as("Verify Order relevant properties")
            .satisfies(e -> assertThat(e.getBusinessCode()).as("check businessCode").isEqualTo(actual.getBusinessCode()))
            .satisfies(e -> assertThat(e.getPlacedDate()).as("check placedDate").isEqualTo(actual.getPlacedDate()))
            .satisfies(
                e ->
                    assertThat(e.getTotalTaxValue())
                        .as("check totalTaxValue")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getTotalTaxValue())
            )
            .satisfies(
                e ->
                    assertThat(e.getTotalDueValue())
                        .as("check totalDueValue")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getTotalDueValue())
            )
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(
                e -> assertThat(e.getEstimatedDeliveryDate()).as("check estimatedDeliveryDate").isEqualTo(actual.getEstimatedDeliveryDate())
            )
            .satisfies(e -> assertThat(e.getActivationStatus()).as("check activationStatus").isEqualTo(actual.getActivationStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderUpdatableRelationshipsEquals(Order expected, Order actual) {
        assertThat(expected)
            .as("Verify Order relationships")
            .satisfies(e -> assertThat(e.getInvoice()).as("check invoice").isEqualTo(actual.getInvoice()))
            .satisfies(e -> assertThat(e.getCustomer()).as("check customer").isEqualTo(actual.getCustomer()));
    }
}
