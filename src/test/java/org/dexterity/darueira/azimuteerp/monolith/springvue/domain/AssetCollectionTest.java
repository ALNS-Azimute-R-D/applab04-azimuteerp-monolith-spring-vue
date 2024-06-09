package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ActivityTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArticleTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollectionTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivityTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetCollectionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetCollection.class);
        AssetCollection assetCollection1 = getAssetCollectionSample1();
        AssetCollection assetCollection2 = new AssetCollection();
        assertThat(assetCollection1).isNotEqualTo(assetCollection2);

        assetCollection2.setId(assetCollection1.getId());
        assertThat(assetCollection1).isEqualTo(assetCollection2);

        assetCollection2 = getAssetCollectionSample2();
        assertThat(assetCollection1).isNotEqualTo(assetCollection2);
    }

    @Test
    void assetTest() {
        AssetCollection assetCollection = getAssetCollectionRandomSampleGenerator();
        Asset assetBack = getAssetRandomSampleGenerator();

        assetCollection.addAsset(assetBack);
        assertThat(assetCollection.getAssets()).containsOnly(assetBack);

        assetCollection.removeAsset(assetBack);
        assertThat(assetCollection.getAssets()).doesNotContain(assetBack);

        assetCollection.assets(new HashSet<>(Set.of(assetBack)));
        assertThat(assetCollection.getAssets()).containsOnly(assetBack);

        assetCollection.setAssets(new HashSet<>());
        assertThat(assetCollection.getAssets()).doesNotContain(assetBack);
    }

    @Test
    void articleTest() {
        AssetCollection assetCollection = getAssetCollectionRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        assetCollection.addArticle(articleBack);
        assertThat(assetCollection.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getAssetCollections()).containsOnly(assetCollection);

        assetCollection.removeArticle(articleBack);
        assertThat(assetCollection.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getAssetCollections()).doesNotContain(assetCollection);

        assetCollection.articles(new HashSet<>(Set.of(articleBack)));
        assertThat(assetCollection.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getAssetCollections()).containsOnly(assetCollection);

        assetCollection.setArticles(new HashSet<>());
        assertThat(assetCollection.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getAssetCollections()).doesNotContain(assetCollection);
    }

    @Test
    void eventTest() {
        AssetCollection assetCollection = getAssetCollectionRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        assetCollection.addEvent(eventBack);
        assertThat(assetCollection.getEvents()).containsOnly(eventBack);
        assertThat(eventBack.getAssetCollections()).containsOnly(assetCollection);

        assetCollection.removeEvent(eventBack);
        assertThat(assetCollection.getEvents()).doesNotContain(eventBack);
        assertThat(eventBack.getAssetCollections()).doesNotContain(assetCollection);

        assetCollection.events(new HashSet<>(Set.of(eventBack)));
        assertThat(assetCollection.getEvents()).containsOnly(eventBack);
        assertThat(eventBack.getAssetCollections()).containsOnly(assetCollection);

        assetCollection.setEvents(new HashSet<>());
        assertThat(assetCollection.getEvents()).doesNotContain(eventBack);
        assertThat(eventBack.getAssetCollections()).doesNotContain(assetCollection);
    }

    @Test
    void activityTest() {
        AssetCollection assetCollection = getAssetCollectionRandomSampleGenerator();
        Activity activityBack = getActivityRandomSampleGenerator();

        assetCollection.addActivity(activityBack);
        assertThat(assetCollection.getActivities()).containsOnly(activityBack);
        assertThat(activityBack.getAssetCollections()).containsOnly(assetCollection);

        assetCollection.removeActivity(activityBack);
        assertThat(assetCollection.getActivities()).doesNotContain(activityBack);
        assertThat(activityBack.getAssetCollections()).doesNotContain(assetCollection);

        assetCollection.activities(new HashSet<>(Set.of(activityBack)));
        assertThat(assetCollection.getActivities()).containsOnly(activityBack);
        assertThat(activityBack.getAssetCollections()).containsOnly(assetCollection);

        assetCollection.setActivities(new HashSet<>());
        assertThat(assetCollection.getActivities()).doesNotContain(activityBack);
        assertThat(activityBack.getAssetCollections()).doesNotContain(assetCollection);
    }

    @Test
    void scheduledActivityTest() {
        AssetCollection assetCollection = getAssetCollectionRandomSampleGenerator();
        ScheduledActivity scheduledActivityBack = getScheduledActivityRandomSampleGenerator();

        assetCollection.addScheduledActivity(scheduledActivityBack);
        assertThat(assetCollection.getScheduledActivities()).containsOnly(scheduledActivityBack);
        assertThat(scheduledActivityBack.getAssetCollections()).containsOnly(assetCollection);

        assetCollection.removeScheduledActivity(scheduledActivityBack);
        assertThat(assetCollection.getScheduledActivities()).doesNotContain(scheduledActivityBack);
        assertThat(scheduledActivityBack.getAssetCollections()).doesNotContain(assetCollection);

        assetCollection.scheduledActivities(new HashSet<>(Set.of(scheduledActivityBack)));
        assertThat(assetCollection.getScheduledActivities()).containsOnly(scheduledActivityBack);
        assertThat(scheduledActivityBack.getAssetCollections()).containsOnly(assetCollection);

        assetCollection.setScheduledActivities(new HashSet<>());
        assertThat(assetCollection.getScheduledActivities()).doesNotContain(scheduledActivityBack);
        assertThat(scheduledActivityBack.getAssetCollections()).doesNotContain(assetCollection);
    }
}
