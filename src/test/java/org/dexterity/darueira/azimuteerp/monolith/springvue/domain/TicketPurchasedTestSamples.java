package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TicketPurchasedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TicketPurchased getTicketPurchasedSample1() {
        return new TicketPurchased()
            .id(1L)
            .buyingCode("buyingCode1")
            .amountOfTickets(1)
            .acquiredSeatsNumbers("acquiredSeatsNumbers1")
            .description("description1");
    }

    public static TicketPurchased getTicketPurchasedSample2() {
        return new TicketPurchased()
            .id(2L)
            .buyingCode("buyingCode2")
            .amountOfTickets(2)
            .acquiredSeatsNumbers("acquiredSeatsNumbers2")
            .description("description2");
    }

    public static TicketPurchased getTicketPurchasedRandomSampleGenerator() {
        return new TicketPurchased()
            .id(longCount.incrementAndGet())
            .buyingCode(UUID.randomUUID().toString())
            .amountOfTickets(intCount.incrementAndGet())
            .acquiredSeatsNumbers(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
