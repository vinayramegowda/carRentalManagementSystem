package carRentalSystemTests;

import carRentalSystem.DateTime;
import carRentalSystem.Vehicle;
import carRentalSystem.VehicleType;
import org.junit.Test;



public class VehicleTest {
    private DateTime lastMain = new DateTime(10, 8, 2019);
    private VehicleType vanType = new VehicleType(15, lastMain);
    private Vehicle testVehicle = new Vehicle("V_1", 2000, "Test Make", "Test Model", 1, vanType);

    // 0 - Available, 1 - Rented, 2 - Maintenance
    @Test
    public void getRecord(){

    }
}
