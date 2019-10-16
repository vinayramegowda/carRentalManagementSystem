package carRentalSystemTests;

import carRentalSystem.Car;
import carRentalSystem.DateTime;
import carRentalSystem.ThriftyRentSystem;
import carRentalSystem.VehicleType;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class CarTest {
    private VehicleType vehicle = new VehicleType(4);
    private Car car = new Car("C_01", 2000, "Honda", "Civic", 1, vehicle);
    private ByteArrayInputStream carStream = new ByteArrayInputStream("car\n2000\ncar\ncar\n1\n4\n".getBytes());
    private ByteArrayInputStream carStreamDup = new ByteArrayInputStream("car\n2000\ncar\ncar\n1\n4\n".getBytes());
    private ByteArrayInputStream rentCar = new ByteArrayInputStream("C_1\n1\n10/10/2019\n2".getBytes());
    private ByteArrayInputStream returnCar = new ByteArrayInputStream("C_1\n14/10/2019".getBytes());
    private ByteArrayInputStream startMaintenance = new ByteArrayInputStream("C_1".getBytes());

    @Test
    public void checkCarID() {
        assertEquals("C_01", car.getVehicleId());

    }

    @Test
    public void addCarTest() {
        ThriftyRentSystem rentalSystem = new ThriftyRentSystem();

        rentalSystem.add(new Scanner(carStream));
        rentalSystem.add(new Scanner(carStreamDup));    // shouldn't be added as id is a duplicate
        assertEquals(1, rentalSystem.getCars().size());
    }

    @Test
    public void rentCarTest() {
        ThriftyRentSystem rentalSystem = new ThriftyRentSystem();

        rentalSystem.add(new Scanner(carStream));
        rentalSystem.rent(new Scanner(rentCar));

        assertEquals(1, rentalSystem.getCars().get(0).getVehicleStatus());
    }

    @Test
    public void returnCarTest() {
        ThriftyRentSystem rentalSystem = new ThriftyRentSystem();
        DateTime endTime = new DateTime(14, 10, 2019);
        DateTime startTime = new DateTime(12, 10, 2019);

        rentalSystem.add(new Scanner(carStream));
        rentalSystem.rent(new Scanner(rentCar));
        rentalSystem.returnVehicle(new Scanner(returnCar));

        assertEquals(195.00, rentalSystem.getCars().get(0).getLateFee(endTime, startTime), .00);
        assertEquals(0, rentalSystem.getCars().get(0).getVehicleStatus());
    }

    @Test
    public void performMaintenanceCar() {
        ThriftyRentSystem rentalSystem = new ThriftyRentSystem();

        rentalSystem.add(new Scanner(carStream));
        rentalSystem.startMaintenance(new Scanner(startMaintenance));

        assertEquals(2, rentalSystem.getCars().get(0).getVehicleStatus());
    }
}