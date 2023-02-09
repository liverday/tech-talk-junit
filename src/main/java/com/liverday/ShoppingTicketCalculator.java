package com.liverday;

public class ShoppingTicketCalculator implements TicketCalculator {

    @Override
    public double calculate(long differenceInHours) {
        return differenceInHours * 8.0;
    }
}
