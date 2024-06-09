package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScheduledActivityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduledActivityDTO.class);
        ScheduledActivityDTO scheduledActivityDTO1 = new ScheduledActivityDTO();
        scheduledActivityDTO1.setId(1L);
        ScheduledActivityDTO scheduledActivityDTO2 = new ScheduledActivityDTO();
        assertThat(scheduledActivityDTO1).isNotEqualTo(scheduledActivityDTO2);
        scheduledActivityDTO2.setId(scheduledActivityDTO1.getId());
        assertThat(scheduledActivityDTO1).isEqualTo(scheduledActivityDTO2);
        scheduledActivityDTO2.setId(2L);
        assertThat(scheduledActivityDTO1).isNotEqualTo(scheduledActivityDTO2);
        scheduledActivityDTO1.setId(null);
        assertThat(scheduledActivityDTO1).isNotEqualTo(scheduledActivityDTO2);
    }
}
