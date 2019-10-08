package carRentalSystemTests;

import carRentalSystem.Car;
import carRentalSystem.VehicleType;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CarTest {
    private VehicleType vehicle = new VehicleType( 4);
    private Car car = new Car("C_01", 2000, "Honda", "Civic", 1, vehicle);

    @Test
    public void checkVehicleID(){
        assertEquals("C_01", car.getVehicleId());
    }

    @Test
    public void addCarTest(){

    }

// add new vehicle
    // test return date
    // rent and return, check expected dates
}