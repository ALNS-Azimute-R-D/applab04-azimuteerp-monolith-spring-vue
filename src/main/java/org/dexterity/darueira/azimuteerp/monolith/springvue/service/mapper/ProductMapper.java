package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Brand;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Product;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Supplier;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.BrandDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProductDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.SupplierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandAcronym")
    @Mapping(target = "toSuppliers", source = "toSuppliers", qualifiedByName = "supplierIdSet")
    ProductDTO toDto(Product s);

    @Mapping(target = "removeToSupplier", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @Named("brandAcronym")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "acronym", source = "acronym")
    BrandDTO toDtoBrandAcronym(Brand brand);

    @Named("supplierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SupplierDTO toDtoSupplierId(Supplier supplier);

    @Named("supplierIdSet")
    default Set<SupplierDTO> toDtoSupplierIdSet(Set<Supplier> supplier) {
        return supplier.stream().map(this::toDtoSupplierId).collect(Collectors.toSet());
    }
}
