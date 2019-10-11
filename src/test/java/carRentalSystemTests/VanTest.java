package carRentalSystemTests;

import carRentalSystem.DateTime;
import carRentalSystem.ThriftyRentSystem;
import carRentalSystem.Vehicle;
import carRentalSystem.VehicleType;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class VanTest {
    private DateTime lastMain = new DateTime(10, 8, 2019);
    private VehicleType vanType = new VehicleType(15, lastMain);
    private Vehicle testVehicle = new Vehicle("V_1", 2000, "Test Make", "Test Model", 0, vanType);
    private ByteArrayInputStream vanStream = new ByteArrayInputStream("van\n2000\nvan\nvan\n1\n10/10/2019\n".getBytes());
    private ByteArrayInputStream vanStreamDup = new ByteArrayInputStream("van\n2000\nvan\nvan\n1\n10/10/2019\n".getBytes());
    private ByteArrayInputStream rentVan = new ByteArrayInputStream("V_1\n1\n10/10/2019\n2".getBytes());
    private ByteArrayInputStream returnVan = new ByteArrayInputStream("V_1\n14/10/2019".getBytes());
    private ByteArrayInputStream startMaintenance = new ByteArrayInputStream("V_1".getBytes());

    @Test
    public void getVanID() {
        assertEquals("V_1", testVehicle.getVehicleId());
    }

    @Test
    public void addVanTest() {
        ThriftyRentSystem rentalSystem = new ThriftyRentSystem();

        rentalSystem.add(new Scanner(vanStream));
        rentalSystem.add(new Scanner(vanStreamDup));

        assertEquals(1, rentalSystem.getVans().size());
    }

    @Test
    public void rentVan() {
        ThriftyRentSystem rentalSystem = new ThriftyRentSystem();

        rentalSystem.add(new Scanner(vanStream));
        rentalSystem.rent(new Scanner(rentVan));

        assertEquals(1, rentalSystem.getVans().get(0).getVehicleStatus());
    }

    @Test
    public void returnVan() {
        ThriftyRentSystem rentalSystem = new ThriftyRentSystem();
        DateTime endTime = new DateTime(14, 10, 2019);
        DateTime startTime = new DateTime(12, 10, 2019);

        rentalSystem.add(new Scanner(vanStream));
        rentalSystem.rent(new Scanner(rentVan));
        rentalSystem.returnVehicle(new Scanner(returnVan));
        assertEquals(598.00, rentalSystem.getVans().get(0).getLateFee(endTime, startTime), .00);
        assertEquals(0, rentalSystem.getVans().get(0).getVehicleStatus());
    }

    @Test
    public void performMaintenanceVan() {
        ThriftyRentSystem rentalSystem = new ThriftyRentSystem();

        rentalSystem.add(new Scanner(vanStream));
        rentalSystem.startMaintenance(new Scanner(startMaintenance));

        assertEquals(2, rentalSystem.getVans().get(0).getVehicleStatus());
    }
}
