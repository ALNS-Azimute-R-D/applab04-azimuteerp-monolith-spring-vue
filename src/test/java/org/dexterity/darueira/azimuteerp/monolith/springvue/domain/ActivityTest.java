package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ActivityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollectionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivityTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = getActivitySample1();
        Activity activity2 = new Activity();
        assertThat(activity1).isNotEqualTo(activity2);

        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);

        activity2 = getActivitySample2();
        assertThat(activity1).isNotEqualTo(activity2);
    }

    @Test
    void typeOfActivityTest() {
        Activity activity = getActivityRandomSampleGenerator();
        TypeOfActivity typeOfActivityBack = getTypeOfActivityRandomSampleGenerator();

        activity.setTypeOfActivity(typeOfActivityBack);
        assertThat(activity.getTypeOfActivity()).isEqualTo(typeOfActivityBack);

        activity.typeOfActivity(null);
        assertThat(activity.getTypeOfActivity()).isNull();
    }

    @Test
    void createdByUserTest() {
        Activity activity = getActivityRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        activity.setCreatedByUser(personBack);
        assertThat(activity.getCreatedByUser()).isEqualTo(personBack);

        activity.createdByUser(null);
        assertThat(activity.getCreatedByUser()).isNull();
    }

    @Test
    void assetCollectionTest() {
        Activity activity = getActivityRandomSampleGenerator();
        AssetCollection assetCollectionBack = getAssetCollectionRandomSampleGenerator();

        activity.addAssetCollection(assetCollectionBack);
        assertThat(activity.getAssetCollections()).containsOnly(assetCollectionBack);

        activity.removeAssetCollection(assetCollectionBack);
        assertThat(activity.getAssetCollections()).doesNotContain(assetCollectionBack);

        activity.assetCollections(new HashSet<>(Set.of(assetCollectionBack)));
        assertThat(activity.getAssetCollections()).containsOnly(assetCollectionBack);

        activity.setAssetCollections(new HashSet<>());
        assertThat(activity.getAssetCollections()).doesNotContain(assetCollectionBack);
    }

    @Test
    void scheduledActivitiesListTest() {
        Activity activity = getActivityRandomSampleGenerator();
        ScheduledActivity scheduledActivityBack = getScheduledActivityRandomSampleGenerator();

        activity.addScheduledActivitiesList(scheduledActivityBack);
        assertThat(activity.getScheduledActivitiesLists()).containsOnly(scheduledActivityBack);
        assertThat(scheduledActivityBack.getActivity()).isEqualTo(activity);

        activity.removeScheduledActivitiesList(scheduledActivityBack);
        assertThat(activity.getScheduledActivitiesLists()).doesNotContain(scheduledActivityBack);
        assertThat(scheduledActivityBack.getActivity()).isNull();

        activity.scheduledActivitiesLists(new HashSet<>(Set.of(scheduledActivityBack)));
        assertThat(activity.getScheduledActivitiesLists()).containsOnly(scheduledActivityBack);
        assertThat(scheduledActivityBack.getActivity()).isEqualTo(activity);

        activity.setScheduledActivitiesLists(new HashSet<>());
        assertThat(activity.getScheduledActivitiesLists()).doesNotContain(scheduledActivityBack);
        assertThat(scheduledActivityBack.getActivity()).isNull();
    }
}
