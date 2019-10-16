package carRentalSystem;

import java.util.ArrayList;

/**
 * Class to save all the vehicle details
 * This class contains all the vehicle attributes required for this application
 * This is the data access layer and main logic for this application is written here
 */
public class Vehicle {
    String Vehicle_id;
    private int Year;
    private String Make;
    private String Model;
    int vehicleStatus;
    VehicleType vehicleType;
    //RentalRecord[] records = new RentalRecord[10];
    ArrayList<RentalRecord> records = new ArrayList<>();

    // Constructor to accept the details of a vehicle
    public Vehicle(String VehicleId, int Year, String Make, String Model, int status, VehicleType vehicleType) {
        this.Vehicle_id = VehicleId;
        this.Year = Year;
        this.Make = Make;
        this.Model = Model;
        this.vehicleStatus = status;
        this.vehicleType = vehicleType;
    }

    public int getVehicleStatus() {
        return this.vehicleStatus;
    }

    /**
     * Method to get vehicle ID
     */
    public String getVehicleId() {
        return this.Vehicle_id;
    }

    /**
     * Used to rent either available car or available van
     *
     * @param customerId,rentDate,numOfRentDay accepts customerID, date of rent, no of renting days
     * @return True or false as to vehicle is successfully rented or not
     */
    boolean rent(String customerId, DateTime rentDate, int numOfRentDay) {
        String typeOfVehicle;
        if (this.Vehicle_id.contains("C_"))
            typeOfVehicle = "car";
        else
            typeOfVehicle = "van";
        if (this.vehicleStatus != 0 || numOfRentDay < this.vehicleType.canBeRentedForMinimumDays(rentDate, typeOfVehicle) || numOfRentDay >= 14 || numOfRentDay < 2) {
            return false;
        } else if (typeOfVehicle.equals("van")) {
            if (this.vehicleStatus != 0 || this.vehicleType.IsUnderMaintenance(rentDate, typeOfVehicle, numOfRentDay))
                return false;
            else {
                return addRentalRecord(customerId, rentDate, numOfRentDay);
            }
        } else {
            return addRentalRecord(customerId, rentDate, numOfRentDay);
        }
    }

    private boolean addRentalRecord(String customerId, DateTime rentDate, int numOfRentDay) {
        String rentId = this.Vehicle_id + "_" + customerId + "_" + rentDate.getEightDigitDate();
        this.records.add(records.size(), new RentalRecord(rentId, rentDate, new DateTime(rentDate, numOfRentDay)));
        this.vehicleStatus = 1;
        return true;
    }

    /**
     * sets the vehicle status to available after maintenance
     *
     * @return Returns true if returned else false
     */
    boolean performMaintenance() {
        if (this.vehicleStatus == 1 || this.vehicleStatus == 2)
            return false;
        this.vehicleStatus = 2;
        return true;
    }

    /**
     * Method used to get details of vehicle
     */
    public String toString() {
        String repository = "";
        if (this.Vehicle_id.contains("V_")) {
            repository = this.Vehicle_id + ":" + this.Year + ":" + this.Make + ":" + this.Model + ":15" + ":";
        }
        if (this.Vehicle_id.contains("C_")) {
            repository = this.Vehicle_id + ":" + this.Year + ":" + this.Make + ":" + this.Model + ":" + this.vehicleType.getSeats("car") + ":";
        }
        repository = vehicleStatusSuffix(repository);
        return repository;
    }

    /**
     * Method used to get details of car or van with their rental history
     */
    public String getDetails() {
        String data = "Vehicle ID:\t" + this.Vehicle_id + "\n Year:\t " + this.Year + "\n Make:\t" + this.Make + "\n Model:\t" + this.Model + "\n Number of Seats:\t";
        if (this.Vehicle_id.contains("C_"))
            data += this.vehicleType.getSeats("car") + "\n Status:\t";
        else {
            data += this.vehicleType.getSeats("van") + "\n Status:\t";
        }
        data = vehicleStatusSuffix(data);
        return data;

    }

    /*
     * Add the vehicle status to the end of the string
     */
    private String vehicleStatusSuffix(String stringToAdd) {
        if (vehicleStatus == 0) {
            stringToAdd += "Available";
        } else if (vehicleStatus == 1) {
            stringToAdd += "Rented";
        } else if (vehicleStatus == 2) {
            stringToAdd += "Maintenance";
        }
        return stringToAdd;
    }

    /**
     * Method used to get last element index
     */
    int getLastElementIndex() {
        return records.size() - 1;
    }
}