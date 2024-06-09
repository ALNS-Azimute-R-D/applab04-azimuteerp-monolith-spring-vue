package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WarehouseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Warehouse getWarehouseSample1() {
        return new Warehouse()
            .id(1L)
            .acronym("acronym1")
            .name("name1")
            .description("description1")
            .streetAddress("streetAddress1")
            .houseNumber("houseNumber1")
            .locationName("locationName1")
            .postalCode("postalCode1")
            .mainEmail("mainEmail1")
            .landPhoneNumber("landPhoneNumber1")
            .mobilePhoneNumber("mobilePhoneNumber1")
            .faxNumber("faxNumber1")
            .customAttributesDetailsJSON("customAttributesDetailsJSON1");
    }

    public static Warehouse getWarehouseSample2() {
        return new Warehouse()
            .id(2L)
            .acronym("acronym2")
            .name("name2")
            .description("description2")
            .streetAddress("streetAddress2")
            .houseNumber("houseNumber2")
            .locationName("locationName2")
            .postalCode("postalCode2")
            .mainEmail("mainEmail2")
            .landPhoneNumber("landPhoneNumber2")
            .mobilePhoneNumber("mobilePhoneNumber2")
            .faxNumber("faxNumber2")
            .customAttributesDetailsJSON("customAttributesDetailsJSON2");
    }

    public static Warehouse getWarehouseRandomSampleGenerator() {
        return new Warehouse()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .streetAddress(UUID.randomUUID().toString())
            .houseNumber(UUID.randomUUID().toString())
            .locationName(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString())
            .mainEmail(UUID.randomUUID().toString())
            .landPhoneNumber(UUID.randomUUID().toString())
            .mobilePhoneNumber(UUID.randomUUID().toString())
            .faxNumber(UUID.randomUUID().toString())
            .customAttributesDetailsJSON(UUID.randomUUID().toString());
    }
}
