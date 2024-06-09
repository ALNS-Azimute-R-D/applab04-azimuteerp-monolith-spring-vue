package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventProgramDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventProgramDTO.class);
        EventProgramDTO eventProgramDTO1 = new EventProgramDTO();
        eventProgramDTO1.setId(1L);
        EventProgramDTO eventProgramDTO2 = new EventProgramDTO();
        assertThat(eventProgramDTO1).isNotEqualTo(eventProgramDTO2);
        eventProgramDTO2.setId(eventProgramDTO1.getId());
        assertThat(eventProgramDTO1).isEqualTo(eventProgramDTO2);
        eventProgramDTO2.setId(2L);
        assertThat(eventProgramDTO1).isNotEqualTo(eventProgramDTO2);
        eventProgramDTO1.setId(null);
        assertThat(eventProgramDTO1).isNotEqualTo(eventProgramDTO2);
    }
}
