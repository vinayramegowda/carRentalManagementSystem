package carRentalSystemTests;

import carRentalSystem.Car;
import carRentalSystem.ThriftyRentSystem;
import carRentalSystem.VehicleType;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class CarTest {
    private VehicleType vehicle = new VehicleType( 4);
    private Car car = new Car("C_01", 2000, "Honda", "Civic", 1, vehicle);
    ByteArrayInputStream carStream = new ByteArrayInputStream("car\n2000\ncar\ncar\n1\n4\n".getBytes());

    @Test
    public void checkVehicleID(){
        assertEquals("C_01", car.getVehicleId());
    }

    @Test
    public void addCarTest(){
        ThriftyRentSystem rentSystem = new ThriftyRentSystem();

        rentSystem.add(new Scanner(carStream));
    }

// add new vehicle
    // test return date
    // rent and return, check expected dates
}