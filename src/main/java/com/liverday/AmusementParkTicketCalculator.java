package com.liverday;

public class AmusementParkTicketCalculator implements TicketCalculator {
    @Override
    public double calculate(long differenceInHours) {
        return 25.0;
    }
}
