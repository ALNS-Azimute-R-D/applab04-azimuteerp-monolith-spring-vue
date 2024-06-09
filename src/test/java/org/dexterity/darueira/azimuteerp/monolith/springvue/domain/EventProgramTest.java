package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgramTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProgramTestSamples.*;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventProgramTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventProgram.class);
        EventProgram eventProgram1 = getEventProgramSample1();
        EventProgram eventProgram2 = new EventProgram();
        assertThat(eventProgram1).isNotEqualTo(eventProgram2);

        eventProgram2.setId(eventProgram1.getId());
        assertThat(eventProgram1).isEqualTo(eventProgram2);

        eventProgram2 = getEventProgramSample2();
        assertThat(eventProgram1).isNotEqualTo(eventProgram2);
    }

    @Test
    void eventTest() {
        EventProgram eventProgram = getEventProgramRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        eventProgram.setEvent(eventBack);
        assertThat(eventProgram.getEvent()).isEqualTo(eventBack);

        eventProgram.event(null);
        assertThat(eventProgram.getEvent()).isNull();
    }

    @Test
    void programTest() {
        EventProgram eventProgram = getEventProgramRandomSampleGenerator();
        Program programBack = getProgramRandomSampleGenerator();

        eventProgram.setProgram(programBack);
        assertThat(eventProgram.getProgram()).isEqualTo(programBack);

        eventProgram.program(null);
        assertThat(eventProgram.getProgram()).isNull();
    }

    @Test
    void responsiblePersonTest() {
        EventProgram eventProgram = getEventProgramRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        eventProgram.setResponsiblePerson(personBack);
        assertThat(eventProgram.getResponsiblePerson()).isEqualTo(personBack);

        eventProgram.responsiblePerson(null);
        assertThat(eventProgram.getResponsiblePerson()).isNull();
    }
}
