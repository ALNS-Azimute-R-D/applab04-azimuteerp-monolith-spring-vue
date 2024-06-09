package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PersonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Person getPersonSample1() {
        return new Person()
            .id(1L)
            .firstname("firstname1")
            .lastname("lastname1")
            .fullname("fullname1")
            .codeBI("codeBI1")
            .codeNIF("codeNIF1")
            .streetAddress("streetAddress1")
            .houseNumber("houseNumber1")
            .locationName("locationName1")
            .postalCode("postalCode1")
            .mainEmail("mainEmail1")
            .landPhoneNumber("landPhoneNumber1")
            .mobilePhoneNumber("mobilePhoneNumber1")
            .occupation("occupation1")
            .preferredLanguage("preferredLanguage1")
            .usernameInOAuth2("usernameInOAuth21")
            .userIdInOAuth2("userIdInOAuth21")
            .customAttributesDetailsJSON("customAttributesDetailsJSON1");
    }

    public static Person getPersonSample2() {
        return new Person()
            .id(2L)
            .firstname("firstname2")
            .lastname("lastname2")
            .fullname("fullname2")
            .codeBI("codeBI2")
            .codeNIF("codeNIF2")
            .streetAddress("streetAddress2")
            .houseNumber("houseNumber2")
            .locationName("locationName2")
            .postalCode("postalCode2")
            .mainEmail("mainEmail2")
            .landPhoneNumber("landPhoneNumber2")
            .mobilePhoneNumber("mobilePhoneNumber2")
            .occupation("occupation2")
            .preferredLanguage("preferredLanguage2")
            .usernameInOAuth2("usernameInOAuth22")
            .userIdInOAuth2("userIdInOAuth22")
            .customAttributesDetailsJSON("customAttributesDetailsJSON2");
    }

    public static Person getPersonRandomSampleGenerator() {
        return new Person()
            .id(longCount.incrementAndGet())
            .firstname(UUID.randomUUID().toString())
            .lastname(UUID.randomUUID().toString())
            .fullname(UUID.randomUUID().toString())
            .codeBI(UUID.randomUUID().toString())
            .codeNIF(UUID.randomUUID().toString())
            .streetAddress(UUID.randomUUID().toString())
            .houseNumber(UUID.randomUUID().toString())
            .locationName(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString())
            .mainEmail(UUID.randomUUID().toString())
            .landPhoneNumber(UUID.randomUUID().toString())
            .mobilePhoneNumber(UUID.randomUUID().toString())
            .occupation(UUID.randomUUID().toString())
            .preferredLanguage(UUID.randomUUID().toString())
            .usernameInOAuth2(UUID.randomUUID().toString())
            .userIdInOAuth2(UUID.randomUUID().toString())
            .customAttributesDetailsJSON(UUID.randomUUID().toString());
    }
}
