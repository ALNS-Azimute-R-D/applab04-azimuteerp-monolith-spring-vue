package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SupplierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Supplier getSupplierSample1() {
        return new Supplier()
            .id(1L)
            .acronym("acronym1")
            .companyName("companyName1")
            .streetAddress("streetAddress1")
            .houseNumber("houseNumber1")
            .locationName("locationName1")
            .city("city1")
            .stateProvince("stateProvince1")
            .zipPostalCode("zipPostalCode1")
            .countryRegion("countryRegion1")
            .mainEmail("mainEmail1")
            .phoneNumber1("phoneNumber11")
            .phoneNumber2("phoneNumber21")
            .customAttributesDetailsJSON("customAttributesDetailsJSON1");
    }

    public static Supplier getSupplierSample2() {
        return new Supplier()
            .id(2L)
            .acronym("acronym2")
            .companyName("companyName2")
            .streetAddress("streetAddress2")
            .houseNumber("houseNumber2")
            .locationName("locationName2")
            .city("city2")
            .stateProvince("stateProvince2")
            .zipPostalCode("zipPostalCode2")
            .countryRegion("countryRegion2")
            .mainEmail("mainEmail2")
            .phoneNumber1("phoneNumber12")
            .phoneNumber2("phoneNumber22")
            .customAttributesDetailsJSON("customAttributesDetailsJSON2");
    }

    public static Supplier getSupplierRandomSampleGenerator() {
        return new Supplier()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .companyName(UUID.randomUUID().toString())
            .streetAddress(UUID.randomUUID().toString())
            .houseNumber(UUID.randomUUID().toString())
            .locationName(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .stateProvince(UUID.randomUUID().toString())
            .zipPostalCode(UUID.randomUUID().toString())
            .countryRegion(UUID.randomUUID().toString())
            .mainEmail(UUID.randomUUID().toString())
            .phoneNumber1(UUID.randomUUID().toString())
            .phoneNumber2(UUID.randomUUID().toString())
            .customAttributesDetailsJSON(UUID.randomUUID().toString());
    }
}
