package com.liverday;

public class StadiumTicketCalculator implements TicketCalculator {
    @Override
    public double calculate(long differenceInHours) {
        final long extraHours = differenceInHours - 1;
        return 15.0 + (extraHours * 5.0);
    }
}
