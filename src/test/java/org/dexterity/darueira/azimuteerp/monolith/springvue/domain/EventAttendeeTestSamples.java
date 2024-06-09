package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EventAttendeeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EventAttendee getEventAttendeeSample1() {
        return new EventAttendee().id(1L).attendedAsRole("attendedAsRole1").ticketNumber("ticketNumber1").seatNumber("seatNumber1");
    }

    public static EventAttendee getEventAttendeeSample2() {
        return new EventAttendee().id(2L).attendedAsRole("attendedAsRole2").ticketNumber("ticketNumber2").seatNumber("seatNumber2");
    }

    public static EventAttendee getEventAttendeeRandomSampleGenerator() {
        return new EventAttendee()
            .id(longCount.incrementAndGet())
            .attendedAsRole(UUID.randomUUID().toString())
            .ticketNumber(UUID.randomUUID().toString())
            .seatNumber(UUID.randomUUID().toString());
    }
}
