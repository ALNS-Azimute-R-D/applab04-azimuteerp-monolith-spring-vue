package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CommonLocalityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CommonLocality getCommonLocalitySample1() {
        return new CommonLocality()
            .id(1L)
            .acronym("acronym1")
            .name("name1")
            .description("description1")
            .streetAddress("streetAddress1")
            .houseNumber("houseNumber1")
            .locationName("locationName1")
            .postalCode("postalCode1");
    }

    public static CommonLocality getCommonLocalitySample2() {
        return new CommonLocality()
            .id(2L)
            .acronym("acronym2")
            .name("name2")
            .description("description2")
            .streetAddress("streetAddress2")
            .houseNumber("houseNumber2")
            .locationName("locationName2")
            .postalCode("postalCode2");
    }

    public static CommonLocality getCommonLocalityRandomSampleGenerator() {
        return new CommonLocality()
            .id(longCount.incrementAndGet())
            .acronym(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .streetAddress(UUID.randomUUID().toString())
            .houseNumber(UUID.randomUUID().toString())
            .locationName(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString());
    }
}
