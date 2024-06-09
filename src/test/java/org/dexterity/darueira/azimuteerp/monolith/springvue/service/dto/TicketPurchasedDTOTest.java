package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketPurchasedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketPurchasedDTO.class);
        TicketPurchasedDTO ticketPurchasedDTO1 = new TicketPurchasedDTO();
        ticketPurchasedDTO1.setId(1L);
        TicketPurchasedDTO ticketPurchasedDTO2 = new TicketPurchasedDTO();
        assertThat(ticketPurchasedDTO1).isNotEqualTo(ticketPurchasedDTO2);
        ticketPurchasedDTO2.setId(ticketPurchasedDTO1.getId());
        assertThat(ticketPurchasedDTO1).isEqualTo(ticketPurchasedDTO2);
        ticketPurchasedDTO2.setId(2L);
        assertThat(ticketPurchasedDTO1).isNotEqualTo(ticketPurchasedDTO2);
        ticketPurchasedDTO1.setId(null);
        assertThat(ticketPurchasedDTO1).isNotEqualTo(ticketPurchasedDTO2);
    }
}
