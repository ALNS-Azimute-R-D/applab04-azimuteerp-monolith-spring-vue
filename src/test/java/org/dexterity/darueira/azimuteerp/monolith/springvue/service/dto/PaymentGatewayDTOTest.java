package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentGatewayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentGatewayDTO.class);
        PaymentGatewayDTO paymentGatewayDTO1 = new PaymentGatewayDTO();
        paymentGatewayDTO1.setId(1L);
        PaymentGatewayDTO paymentGatewayDTO2 = new PaymentGatewayDTO();
        assertThat(paymentGatewayDTO1).isNotEqualTo(paymentGatewayDTO2);
        paymentGatewayDTO2.setId(paymentGatewayDTO1.getId());
        assertThat(paymentGatewayDTO1).isEqualTo(paymentGatewayDTO2);
        paymentGatewayDTO2.setId(2L);
        assertThat(paymentGatewayDTO1).isNotEqualTo(paymentGatewayDTO2);
        paymentGatewayDTO1.setId(null);
        assertThat(paymentGatewayDTO1).isNotEqualTo(paymentGatewayDTO2);
    }
}
