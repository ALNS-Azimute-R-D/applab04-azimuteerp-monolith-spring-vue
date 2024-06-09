package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssertUtils.bigDecimalCompareTo;

public class OrderItemAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderItemAllPropertiesEquals(OrderItem expected, OrderItem actual) {
        assertOrderItemAutoGeneratedPropertiesEquals(expected, actual);
        assertOrderItemAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderItemAllUpdatablePropertiesEquals(OrderItem expected, OrderItem actual) {
        assertOrderItemUpdatableFieldsEquals(expected, actual);
        assertOrderItemUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderItemAutoGeneratedPropertiesEquals(OrderItem expected, OrderItem actual) {
        assertThat(expected)
            .as("Verify OrderItem auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderItemUpdatableFieldsEquals(OrderItem expected, OrderItem actual) {
        assertThat(expected)
            .as("Verify OrderItem relevant properties")
            .satisfies(e -> assertThat(e.getQuantity()).as("check quantity").isEqualTo(actual.getQuantity()))
            .satisfies(
                e ->
                    assertThat(e.getTotalPrice())
                        .as("check totalPrice")
                        .usingComparator(bigDecimalCompareTo)
                        .isEqualTo(actual.getTotalPrice())
            )
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrderItemUpdatableRelationshipsEquals(OrderItem expected, OrderItem actual) {
        assertThat(expected)
            .as("Verify OrderItem relationships")
            .satisfies(e -> assertThat(e.getArticle()).as("check article").isEqualTo(actual.getArticle()))
            .satisfies(e -> assertThat(e.getOrder()).as("check order").isEqualTo(actual.getOrder()));
    }
}
