package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgramTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProgramTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivityTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Program.class);
        Program program1 = getProgramSample1();
        Program program2 = new Program();
        assertThat(program1).isNotEqualTo(program2);

        program2.setId(program1.getId());
        assertThat(program1).isEqualTo(program2);

        program2 = getProgramSample2();
        assertThat(program1).isNotEqualTo(program2);
    }

    @Test
    void programsListTest() {
        Program program = getProgramRandomSampleGenerator();
        EventProgram eventProgramBack = getEventProgramRandomSampleGenerator();

        program.addProgramsList(eventProgramBack);
        assertThat(program.getProgramsLists()).containsOnly(eventProgramBack);
        assertThat(eventProgramBack.getProgram()).isEqualTo(program);

        program.removeProgramsList(eventProgramBack);
        assertThat(program.getProgramsLists()).doesNotContain(eventProgramBack);
        assertThat(eventProgramBack.getProgram()).isNull();

        program.programsLists(new HashSet<>(Set.of(eventProgramBack)));
        assertThat(program.getProgramsLists()).containsOnly(eventProgramBack);
        assertThat(eventProgramBack.getProgram()).isEqualTo(program);

        program.setProgramsLists(new HashSet<>());
        assertThat(program.getProgramsLists()).doesNotContain(eventProgramBack);
        assertThat(eventProgramBack.getProgram()).isNull();
    }

    @Test
    void scheduledActivitiesListTest() {
        Program program = getProgramRandomSampleGenerator();
        ScheduledActivity scheduledActivityBack = getScheduledActivityRandomSampleGenerator();

        program.addScheduledActivitiesList(scheduledActivityBack);
        assertThat(program.getScheduledActivitiesLists()).containsOnly(scheduledActivityBack);
        assertThat(scheduledActivityBack.getProgram()).isEqualTo(program);

        program.removeScheduledActivitiesList(scheduledActivityBack);
        assertThat(program.getScheduledActivitiesLists()).doesNotContain(scheduledActivityBack);
        assertThat(scheduledActivityBack.getProgram()).isNull();

        program.scheduledActivitiesLists(new HashSet<>(Set.of(scheduledActivityBack)));
        assertThat(program.getScheduledActivitiesLists()).containsOnly(scheduledActivityBack);
        assertThat(scheduledActivityBack.getProgram()).isEqualTo(program);

        program.setScheduledActivitiesLists(new HashSet<>());
        assertThat(program.getScheduledActivitiesLists()).doesNotContain(scheduledActivityBack);
        assertThat(scheduledActivityBack.getProgram()).isNull();
    }
}
