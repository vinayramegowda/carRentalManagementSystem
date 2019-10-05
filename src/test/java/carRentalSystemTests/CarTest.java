package carRentalSystemTests;

import carRentalSystem.Car;
import carRentalSystem.VehicleType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CarTest {
    private VehicleType vehicle = new VehicleType( 4);
    private Car car = new Car("C_01", 2000, "Honda", "Civic", 1, vehicle);
    @Test
    public void testCarProperties(){

        assertEquals("C_01", car.getVehicleId());
        assertEquals(2000, car.Year);
        assertEquals(2000, car.Year);

    }
    @Test
    public void testVehicle(){
        assertEquals(4, vehicle.getCarSeats());
    }

// add new vehicle
    // test return date
    // rent and return, check xxcepeced dates
}