package com.liverday;

import java.time.LocalDateTime;

public record ParkedVehicle(
        Vehicle vehicle,
        LocalDateTime checkInDate) {
}
