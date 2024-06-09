package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ActivityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollectionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProgramTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivityTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScheduledActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduledActivity.class);
        ScheduledActivity scheduledActivity1 = getScheduledActivitySample1();
        ScheduledActivity scheduledActivity2 = new ScheduledActivity();
        assertThat(scheduledActivity1).isNotEqualTo(scheduledActivity2);

        scheduledActivity2.setId(scheduledActivity1.getId());
        assertThat(scheduledActivity1).isEqualTo(scheduledActivity2);

        scheduledActivity2 = getScheduledActivitySample2();
        assertThat(scheduledActivity1).isNotEqualTo(scheduledActivity2);
    }

    @Test
    void programTest() {
        ScheduledActivity scheduledActivity = getScheduledActivityRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        scheduledActivity.setProgram(programBack);
        assertThat(scheduledActivity.getProgram()).isEqualTo(programBack);

        scheduledActivity.program(null);
        assertThat(scheduledActivity.getProgram()).isNull();
    }

    @Test
    void activityTest() {
        ScheduledActivity scheduledActivity = getScheduledActivityRandomSampleGenerator();
        Activity activityBack = getActivityRandomSampleGenerator();

        scheduledActivity.setActivity(activityBack);
        assertThat(scheduledActivity.getActivity()).isEqualTo(activityBack);

        scheduledActivity.activity(null);
        assertThat(scheduledActivity.getActivity()).isNull();
    }

    @Test
    void responsiblePersonTest() {
        ScheduledActivity scheduledActivity = getScheduledActivityRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        scheduledActivity.setResponsiblePerson(personBack);
        assertThat(scheduledActivity.getResponsiblePerson()).isEqualTo(personBack);

        scheduledActivity.responsiblePerson(null);
        assertThat(scheduledActivity.getResponsiblePerson()).isNull();
    }

    @Test
    void assetCollectionTest() {
        ScheduledActivity scheduledActivity = getScheduledActivityRandomSampleGenerator();
        AssetCollection assetCollectionBack = getAssetCollectionRandomSampleGenerator();

        scheduledActivity.addAssetCollection(assetCollectionBack);
        assertThat(scheduledActivity.getAssetCollections()).containsOnly(assetCollectionBack);

        scheduledActivity.removeAssetCollection(assetCollectionBack);
        assertThat(scheduledActivity.getAssetCollections()).doesNotContain(assetCollectionBack);

        scheduledActivity.assetCollections(new HashSet<>(Set.of(assetCollectionBack)));
        assertThat(scheduledActivity.getAssetCollections()).containsOnly(assetCollectionBack);

        scheduledActivity.setAssetCollections(new HashSet<>());
        assertThat(scheduledActivity.getAssetCollections()).doesNotContain(assetCollectionBack);
    }
}
