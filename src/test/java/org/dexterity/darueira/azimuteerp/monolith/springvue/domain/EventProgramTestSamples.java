package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class EventProgramTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EventProgram getEventProgramSample1() {
        return new EventProgram().id(1L);
    }

    public static EventProgram getEventProgramSample2() {
        return new EventProgram().id(2L);
    }

    public static EventProgram getEventProgramRandomSampleGenerator() {
        return new EventProgram().id(longCount.incrementAndGet());
    }
}
