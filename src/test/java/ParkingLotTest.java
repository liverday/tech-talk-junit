import com.liverday.AmusementParkTicketCalculator;
import com.liverday.ParkedVehicle;
import com.liverday.ParkingLot;
import com.liverday.ShoppingTicketCalculator;
import com.liverday.StadiumTicketCalculator;
import com.liverday.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParkingLotTest {

    @Test
    public void shouldBeAbleToLimitCapacity() {
        //arrange (given)
        final int expectedCapacity = 1;
        final ParkingLot parkingLot = new ParkingLot(expectedCapacity);
        //act (when)
        final int result = parkingLot.availableSpaces();

        //assert (then)
        assertEquals(1, result);
    }

    @Test
    public void shouldBeAbleToCheckInAVehicle() {
        //arrange (given)
        final int expectedCapacity = 5;
        final ParkingLot parkingLot = new ParkingLot(expectedCapacity);
        final Vehicle vehicle = new Vehicle("AAA-1234", "Nissan March");

        //act (when)
        parkingLot.checkIn(vehicle, LocalDateTime.now());
        final List<ParkedVehicle> vehicles = parkingLot.getParkedVehicles();

        //assert (then)
        assertEquals("AAA-1234", vehicles.get(0).vehicle().plate());
        assertEquals("Nissan March", vehicles.get(0).vehicle().model());
        assertEquals(4, parkingLot.availableSpaces());
    }

    @Test
    public void shouldReturnExceptionWhenVehicleNotFoundOnCheckOut() {
        //arrange (given)
        final ParkingLot parkingLot = new ParkingLot();

        //act (when)
        final Executable test = () -> parkingLot.checkOut("AAA-1234", LocalDateTime.now(), new StadiumTicketCalculator());

        //assert (then)
        assertThrows(RuntimeException.class, test);
    }

    @Test
    public void shouldBeAbleToTicketAtStadium() {
        //arrange (given)
        final ParkingLot parkingLot = new ParkingLot();
        final LocalDateTime checkInDate = LocalDateTime.now();
        final LocalDateTime checkOutDate = checkInDate.plusHours(3L);
        final Vehicle vehicle = new Vehicle("AAA-1234", "Nissan March");
        parkingLot.checkIn(vehicle, checkInDate);

        //act (when)
        final double result = parkingLot.checkOut("AAA-1234", checkOutDate, new StadiumTicketCalculator());

        //assert (then)
        assertEquals(25, result);
        assertEquals(100, parkingLot.availableSpaces());
    }

    @Test
    public void shouldBeAbleToTicketAtShopping() {
        //arrange (given)
        final ParkingLot parkingLot = new ParkingLot();
        final LocalDateTime checkInDate = LocalDateTime.now();
        final LocalDateTime checkOutDate = checkInDate.plusHours(5L);
        final Vehicle vehicle = new Vehicle("AAA-1234", "Nissan March");
        parkingLot.checkIn(vehicle, checkInDate);

        //act (when)
        final double result = parkingLot.checkOut("AAA-1234", checkOutDate, new ShoppingTicketCalculator());

        //assert (then)
        // 8 * 5 = 40
        assertEquals(40, result);
        assertEquals(100, parkingLot.availableSpaces());
    }

    @Test
    public void shouldBeAbleToTicketAtAmusementPark() {
        //arrange (given)
        final ParkingLot parkingLot = new ParkingLot();
        final LocalDateTime checkInDate = LocalDateTime.now();
        final LocalDateTime checkOutDate = checkInDate.plusHours(10L);
        final Vehicle vehicle = new Vehicle("AAA-1234", "Nissan March");
        parkingLot.checkIn(vehicle, checkInDate);

        //act (when)
        final double result = parkingLot.checkOut("AAA-1234", checkOutDate, new AmusementParkTicketCalculator());

        //assert (then)
        assertEquals(25, result);
        assertEquals(100, parkingLot.availableSpaces());
    }

    @Test
    public void shouldReturnExceptionWhenCapacityExceeds() {
        //arrange (given)
        final int expectedCapacity = 1;
        final ParkingLot parkingLot = new ParkingLot(expectedCapacity);
        final Vehicle vehicle = new Vehicle("AAA-1234", "Nissan March");
        parkingLot.checkIn(vehicle, LocalDateTime.now());

        //act (when)
        final Executable test = () -> parkingLot.checkIn(vehicle, LocalDateTime.now());

        //assert (then)
        assertThrows(RuntimeException.class, test);
    }
}
