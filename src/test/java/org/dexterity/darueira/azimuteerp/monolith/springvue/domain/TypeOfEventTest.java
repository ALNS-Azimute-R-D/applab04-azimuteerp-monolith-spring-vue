package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEventTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfEventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfEvent.class);
        TypeOfEvent typeOfEvent1 = getTypeOfEventSample1();
        TypeOfEvent typeOfEvent2 = new TypeOfEvent();
        assertThat(typeOfEvent1).isNotEqualTo(typeOfEvent2);

        typeOfEvent2.setId(typeOfEvent1.getId());
        assertThat(typeOfEvent1).isEqualTo(typeOfEvent2);

        typeOfEvent2 = getTypeOfEventSample2();
        assertThat(typeOfEvent1).isNotEqualTo(typeOfEvent2);
    }

    @Test
    void eventsListTest() {
        TypeOfEvent typeOfEvent = getTypeOfEventRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        typeOfEvent.addEventsList(eventBack);
        assertThat(typeOfEvent.getEventsLists()).containsOnly(eventBack);
        assertThat(eventBack.getTypeOfEvent()).isEqualTo(typeOfEvent);

        typeOfEvent.removeEventsList(eventBack);
        assertThat(typeOfEvent.getEventsLists()).doesNotContain(eventBack);
        assertThat(eventBack.getTypeOfEvent()).isNull();

        typeOfEvent.eventsLists(new HashSet<>(Set.of(eventBack)));
        assertThat(typeOfEvent.getEventsLists()).containsOnly(eventBack);
        assertThat(eventBack.getTypeOfEvent()).isEqualTo(typeOfEvent);

        typeOfEvent.setEventsLists(new HashSet<>());
        assertThat(typeOfEvent.getEventsLists()).doesNotContain(eventBack);
        assertThat(eventBack.getTypeOfEvent()).isNull();
    }
}
