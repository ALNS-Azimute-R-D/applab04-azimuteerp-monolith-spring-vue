package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfVenueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfVenueDTO.class);
        TypeOfVenueDTO typeOfVenueDTO1 = new TypeOfVenueDTO();
        typeOfVenueDTO1.setId(1L);
        TypeOfVenueDTO typeOfVenueDTO2 = new TypeOfVenueDTO();
        assertThat(typeOfVenueDTO1).isNotEqualTo(typeOfVenueDTO2);
        typeOfVenueDTO2.setId(typeOfVenueDTO1.getId());
        assertThat(typeOfVenueDTO1).isEqualTo(typeOfVenueDTO2);
        typeOfVenueDTO2.setId(2L);
        assertThat(typeOfVenueDTO1).isNotEqualTo(typeOfVenueDTO2);
        typeOfVenueDTO1.setId(null);
        assertThat(typeOfVenueDTO1).isNotEqualTo(typeOfVenueDTO2);
    }
}
