package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Invoice;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.InvoiceDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PaymentGatewayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Invoice} and its DTO {@link InvoiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper extends EntityMapper<InvoiceDTO, Invoice> {
    @Mapping(target = "preferrablePaymentGateway", source = "preferrablePaymentGateway", qualifiedByName = "paymentGatewayAliasCode")
    InvoiceDTO toDto(Invoice s);

    @Named("paymentGatewayAliasCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "aliasCode", source = "aliasCode")
    PaymentGatewayDTO toDtoPaymentGatewayAliasCode(PaymentGateway paymentGateway);
}
