package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Customer;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Invoice;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Order;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CustomerDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.InvoiceDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "invoice", source = "invoice", qualifiedByName = "invoiceDescription")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerFullname")
    OrderDTO toDto(Order s);

    @Named("invoiceDescription")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    InvoiceDTO toDtoInvoiceDescription(Invoice invoice);

    @Named("customerFullname")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullname", source = "fullname")
    CustomerDTO toDtoCustomerFullname(Customer customer);
}
