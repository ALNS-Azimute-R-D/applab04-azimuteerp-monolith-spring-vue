package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RawAssetProcTmpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RawAssetProcTmp getRawAssetProcTmpSample1() {
        return new RawAssetProcTmp()
            .id(1L)
            .name("name1")
            .fullFilenamePath("fullFilenamePath1")
            .customAttributesDetailsJSON("customAttributesDetailsJSON1");
    }

    public static RawAssetProcTmp getRawAssetProcTmpSample2() {
        return new RawAssetProcTmp()
            .id(2L)
            .name("name2")
            .fullFilenamePath("fullFilenamePath2")
            .customAttributesDetailsJSON("customAttributesDetailsJSON2");
    }

    public static RawAssetProcTmp getRawAssetProcTmpRandomSampleGenerator() {
        return new RawAssetProcTmp()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .fullFilenamePath(UUID.randomUUID().toString())
            .customAttributesDetailsJSON(UUID.randomUUID().toString());
    }
}
