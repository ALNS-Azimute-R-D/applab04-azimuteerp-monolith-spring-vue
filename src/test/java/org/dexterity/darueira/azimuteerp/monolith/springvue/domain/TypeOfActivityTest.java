package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ActivityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivityTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfActivity.class);
        TypeOfActivity typeOfActivity1 = getTypeOfActivitySample1();
        TypeOfActivity typeOfActivity2 = new TypeOfActivity();
        assertThat(typeOfActivity1).isNotEqualTo(typeOfActivity2);

        typeOfActivity2.setId(typeOfActivity1.getId());
        assertThat(typeOfActivity1).isEqualTo(typeOfActivity2);

        typeOfActivity2 = getTypeOfActivitySample2();
        assertThat(typeOfActivity1).isNotEqualTo(typeOfActivity2);
    }

    @Test
    void activitiesListTest() {
        TypeOfActivity typeOfActivity = getTypeOfActivityRandomSampleGenerator();
        Activity activityBack = getActivityRandomSampleGenerator();

        typeOfActivity.addActivitiesList(activityBack);
        assertThat(typeOfActivity.getActivitiesLists()).containsOnly(activityBack);
        assertThat(activityBack.getTypeOfActivity()).isEqualTo(typeOfActivity);

        typeOfActivity.removeActivitiesList(activityBack);
        assertThat(typeOfActivity.getActivitiesLists()).doesNotContain(activityBack);
        assertThat(activityBack.getTypeOfActivity()).isNull();

        typeOfActivity.activitiesLists(new HashSet<>(Set.of(activityBack)));
        assertThat(typeOfActivity.getActivitiesLists()).containsOnly(activityBack);
        assertThat(activityBack.getTypeOfActivity()).isEqualTo(typeOfActivity);

        typeOfActivity.setActivitiesLists(new HashSet<>());
        assertThat(typeOfActivity.getActivitiesLists()).doesNotContain(activityBack);
        assertThat(activityBack.getTypeOfActivity()).isNull();
    }
}
