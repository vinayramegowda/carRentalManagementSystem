package carRentalSystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This the main class used to get details from the user and display the menu driven options
 * This class acts as the business layer in our applications
 */

public class ThriftyRentSystem {
    //cars and vans are two collections used to store cars of type Cars and vans of type Vans respectively
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<Van> vans = new ArrayList<>();

    private static DateFormat format = new SimpleDateFormat("dd/MM/yyyy"); //Basic format expected from the User

    /**
     * This the method called from main method
     * this contains the menu driven interface to communicate with the user
     */
    void run() {
        while (true) {
            printMenu();
            Scanner sc = new Scanner(System.in);
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1){
                this.add(sc);           //Method used to add either cars or vans
            } else if (choice == 2) {
                this.rent(sc);          //Method used to rent either cars or vans
            } else if (choice == 3) {
                this.returnCar(sc);     //Method used to return a car after being rented
            } else if (choice == 4) {
                this.vanMaintenance(sc); //Method used to set either car or van to maintenance
            } else if (choice == 5) {
                this.completeMaintenance(sc);  //Method used to complete the maintenance
            } else if (choice == 6) {
                this.getDetails();             //Method used to get the details of cars or vans if present
            } else if (choice == 7) {
                sc.close();             //Closing the scanner if 7 is selected by the user
                return;
            }
        }
    }

    private void printMenu() {
        System.out.println("\n**** ThriftyRent SYSTEM MENU ****\n");
        System.out.println("Add vehicle:            1");
        System.out.println("Rent vehicle:           2");
        System.out.println("Return vehicle:         3");
        System.out.println("Vehicle Maintenance:    4");
        System.out.println("Complete Maintenance:   5");
        System.out.println("Display All Vehicles:   6");
        System.out.println("Exit Program:           7");
        System.out.println("Enter your choice:");
    }

    /**
     * Used to add either cars or vans to the list
     *
     * @param scan variable
     */
    public void add(Scanner scan) {
        int i = 0;

        String vehicleType = inputVehicleType(scan);
        int year = inputVehicleYear(scan);

        System.out.print("Make: ");
        String make = scan.nextLine();
        System.out.print("Model: ");
        String model = scan.nextLine();

        if (vehicleType.equalsIgnoreCase("car")) {
            addCar(scan, i, year, make, model);
        }
        else if (vehicleType.equalsIgnoreCase("van")) {
            addVan(scan, i, year, make, model);
        }
    }

    /**
     * Used to rent either available car or available van
     *
     * @param sc variable
     */
    private void rent(Scanner sc) {
        System.out.print("Vehicle id: ");
        String id = sc.nextLine();
        String type = "";

        if (checkValidID(id)) return;

        if (id.contains("C_") && cars.isEmpty()) {
            System.out.println("There are no cars currently at the moment.");
            return;
        }
        else if (id.contains("C_")){
            type = checkCarStatus(id, type);
            if (type == null) return;
        }
        if (id.contains("V_") && vans.isEmpty()) {
            System.out.println("There are no vans currently at the moment.");
            return;
        }
        else if (id.contains("V_")) {
            type = checkVanStatus(id, type);
            if (type == null) return;
        }

        System.out.print("Customer ID: ");
        String cusId = sc.next();
        String date = inputRentalDate(sc);
        DateTime rentDate = parseDate(date);
        System.out.print("How many days?: ");
        int days = sc.nextInt();

        if (rentCar(id, type, cusId, rentDate, days)) return;
        rentVan(id, type, cusId, rentDate, days);
    }

    /**
     * Used to return a rented car or van
     *
     * @param sc variable
     */
    private void returnCar(Scanner sc) {
        System.out.print("VehicleId: ");
        String id = sc.next();
        if (checkValidID(id)) return;
        if (checkVehiclesExist()) return;

        if (id.contains("C_")) {
            returnCar(sc, id);
        }
        else if (id.contains("V_")) {
            returnVan(sc, id);
        }
    }

    /**
     * Method used to set either car or van to maintenance
     *
     * @param sc variable
     */

    private void vanMaintenance(Scanner sc) {
        System.out.print("Vehicle id: ");
        String id = sc.next();

        if (id.contains("C_") && !cars.isEmpty()) {
            carMaintenance(id);
        }
        else if (id.contains("V_") && !vans.isEmpty()) {
            vanMaintenance(id);
        }
    }

    /**
     * Method used to complete maintenance of either car or van
     *
     * @param sc variable
     */
    private void completeMaintenance(Scanner sc) {
        System.out.print("Enter vehicle ID: ");
        String id = sc.next();

        if (id.contains("C_") && !cars.isEmpty()) {
            completeCarMaintenance(sc, id);
        }
        else if (id.contains("V_") && !vans.isEmpty()) {
            completeVanMaintenance(sc, id);
        }
    }

    private boolean checkMaintenanceStatus(String id, Van van, DateTime maintenanceDate) {
        if (van.completeMaintenance(maintenanceDate))
            System.out.println("Vehicle " + id + " has all maintenance completed and ready for rent");
        else {
            System.out.println("Vehicle " + id + " could not complete maintenance");
            return true;
        }
        return false;
    }

    /*
     * If there are vehicles in the system it will return false.
     */
    private boolean checkVehiclesExist() {
        if (cars.isEmpty()) {
            System.out.println("There are no cars, please add cars.");
            return true;
        }
        if (vans.isEmpty()) {
            System.out.println("There are no vans, please add cars.");
            return true;
        }
        return false;
    }

    /**
     * Method used to get details of car or van with their rental history
     */


    // Helpers for Add function
    private void getDetails() {
        if (cars.isEmpty() && vans.isEmpty()) {
            System.out.println("There are no cars or vans to display, please enter some vehicles and try again");
            return;
        }
        if (!cars.isEmpty()) {
            System.out.println("***********Cars**********");
            for (Car car : cars) System.out.println(car.getDetails());
            System.out.print("\n");
        }
        if (!vans.isEmpty()) {
            System.out.println("***********Vans**********");
            for (Van van : vans) System.out.println(van.getDetails());
            System.out.print("\n");
        }
    }

    private DateTime parseDate(String maintenanceDate) {
        String[] dateSplit = maintenanceDate.split("/");
        return new DateTime(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
    }

    private void addCar(Scanner scan, int i, int year, String make, String model) {
        String vehicleID;
        int seats;
        System.out.print("Vehicle ID: C_");
        vehicleID = scan.nextLine();
        vehicleID = "C_" + vehicleID;
        if (!cars.isEmpty() && vehicleID.contains("C_")) {
            for (i = 0; i < cars.size() ; i++) {
                if (checkIDExists(vehicleID, cars.get(i).getVehicleId())) return;
            }
        }
        seats = inputNumSeats(scan);
        createCar(i, vehicleID, seats, year, make, model);
    }

    private void addVan(Scanner scan, int i, int year, String make, String model) {
        String vehicleID;
        int seats;
        String maintenanceDate;
        System.out.print("Vehicle ID: V_");
        vehicleID = scan.nextLine();
        vehicleID = "V_" + vehicleID;
        if (!vans.isEmpty() && vehicleID.contains("V_")) {
            for (i = 0; i < vans.size(); i++) {
                if (checkIDExists(vehicleID, vans.get(i).getVehicleId())) return;
            }
        }
        seats = 15;
        maintenanceDate = inputLastMaintenanceDate(scan);
        DateTime lastMain = parseDate(maintenanceDate);
        createVan(i, vehicleID, seats, year, make, model, lastMain);
    }

    private void createVan(int i, String vehicleID, int seats, int year, String make, String model, DateTime lastMain) {
        if (i < 50) {
            vans.add(new Van(vehicleID, year, make, model, 0, new VehicleType(seats, lastMain)));
            System.out.println(vans.get(i).toString());
        }
    }

    private void createCar(int i, String vehicleID, int seats, int year, String make, String model) {
        if (i < 50) {
            cars.add(new Car(vehicleID, year, make, model, 0, new VehicleType(seats)));
            System.out.println(cars.get(i).toString());
        }
    }

    private int inputVehicleYear(Scanner scan) {
        System.out.print("Year: ");
        int year = Integer.parseInt(scan.nextLine());
        while (year < 0 || year > 2019) {
            System.out.println("Please enter a valid year");
            year = Integer.parseInt(scan.nextLine());
        }
        return year;
    }

    private String inputVehicleType(Scanner scan) {
        System.out.print("Vehicle Type(Van or Car): ");
        String vehicleType = scan.nextLine();
        while (!(vehicleType.equalsIgnoreCase("car") || vehicleType.equalsIgnoreCase("van"))) {
            System.out.print("Please enter either van or car: ");
            vehicleType = scan.nextLine();
        }
        return vehicleType;
    }

    private String inputLastMaintenanceDate(Scanner scan) {
        String maintenanceDate;
        System.out.print("Last Maintenance (dd/mm/yyyy): ");
        maintenanceDate = scan.next();
        format.setLenient(false);
        while (maintenanceDate.trim().length() != ((SimpleDateFormat) format).toPattern().length()) {
            System.out.println("Please enter a valid date in the format dd/mm/yyyy: ");
            maintenanceDate = scan.nextLine();
        }
        return maintenanceDate;
    }

    private int inputNumSeats(Scanner scan) {
        int seats;
        System.out.print("Number of seats: ");
        seats = Integer.parseInt(scan.nextLine());
        while ((seats != 4 && seats != 7)) {
            System.out.println("Please enter seats as either 4 or 7");
            seats = Integer.parseInt(scan.nextLine());
        }
        return seats;
    }

    private boolean checkIDExists(String vehicleID, String vehicleId) {
        if ((vehicleId).equals(vehicleID)) {
            System.out.println("ID Already used, Please add a new vehicle");
            return true;
        }
        return false;
    }

    private String checkVanStatus(String id, String type) {
        boolean flag = false;
        for (Van van : vans) {
            if ((van.getVehicleId()).equals(id)) {
                if (van.vehicleStatus != 0) {
                    System.out.println("The van with ID : " + id + " is already either rented or under maintenance. \nPlease choose another van.");
                    return null;
                }
                type = "van";
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Id is incorrect, please try again!");
            return null;
        }
        return type;
    }

    private String checkCarStatus(String id, String type) {
        boolean flag = false;
        for (Car car : cars) {
            if ((car.getVehicleId()).equals(id)) {
                if (car.vehicleStatus != 0) {
                    System.out.println("The car with ID : " + id + " is already either rented or under maintenance, please choose another car.");
                    return null;
                }
                type = "car";
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("ID is incorrect, please try again!");
            return null;
        }
        return type;
    }

    private void rentVan(String id, String type, String cusId, DateTime rentDate, int days) {
        if (type.equals("van")) {
            for (Van van : vans) {
                if ((van.getVehicleId()).equals(id)) {
                    if (van.rent(cusId, rentDate, days))
                        break;
                    else {
                        System.out.println("Vehicle " + id + " could not be rented");
                        return;
                    }
                }
            }
            System.out.println("Vehicle " + id + " is now rented by customer " + cusId);
        }
    }

    private boolean rentCar(String id, String type, String cusId, DateTime rentDate, int days) {
        if (type.equals("car")) {
            for (Car car : cars) {
                if ((car.getVehicleId()).equals(id)) {
                    if (car.rent(cusId, rentDate, days))
                        break;
                    else {
                        System.out.println("Vehicle " + id + " could not be rented.");
                        return true;
                    }
                }
            }
            System.out.println("Vehicle " + id + " is now rented by customer " + cusId);
        }
        return false;
    }

    private String inputRentalDate(Scanner sc) {
        System.out.print("Rent date( dd/mm/yyyy): ");
        String date = sc.next();
        format.setLenient(false);
        while (date.trim().length() != ((SimpleDateFormat) format).toPattern().length()) {
            System.out.println("Please enter a valid date in the format dd/mm/yyyy: ");
            date = sc.nextLine();
        }
        return date;
    }

    private boolean checkValidID(String id) {
        if (!(id.contains("V_") || id.contains("C_"))) {
            System.out.println("Please Enter a Valid ID either starting from 'V_' or 'C_'.");
            return true;
        }
        return false;
    }

    private void returnVan(Scanner sc, String id) {
        boolean flag = false;
        for (Van van : vans) {
            if ((van.getVehicleId()).equals(id)) {
                System.out.print("Return date( dd/mm/yyyy): ");
                String date = sc.next();
                DateTime returnDate = parseDate(date);
                if (van.returnVehicle(returnDate)) {
                    System.out.println(van.records.get(van.getLastElementIndex()).getDetails());
                } else {
                    System.out.println("Vehicle cannot be returned");
                    return;
                }
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Id is incorrect");
        }
    }

    private void returnCar(Scanner sc, String id) {
        boolean flag = false;
        for (Car car : cars) {
            if ((car.getVehicleId()).equals(id)) {
                System.out.print("Return date( dd/mm/yyyy): ");
                String date = sc.next();
                DateTime returnDate = parseDate(date);
                if (car.returnVehicle(returnDate)) {
                    System.out.println(car.records.get(car.getLastElementIndex()).getDetails());
                } else {
                    System.out.println("Vehicle cannot be returned as it may have been never rented");
                    return;
                }
                flag = true;
                break;
            }

        }
        if (!flag) {
            System.out.println("Id is incorrect");
        }
    }

    private void vanMaintenance(String id) {
        boolean flag = false;
        for (Van van : vans) {
            if ((van.getVehicleId()).equals(id)) {
                if (van.performMaintenance())
                    System.out.println("Vehicle " + id + " is now under maintenance");
                else {
                    System.out.println("Vehicle " + id + " could not be sent for maintenance");
                    return;
                }
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Id is incorrect");
        }
    }

    private void carMaintenance(String id) {
        boolean flag = false;
        for (Car car : cars) {
            if ((car.getVehicleId()).equals(id)) {
                if (car.performMaintenance())
                    System.out.println("Vehicle " + id + " is now under maintenance");
                else {
                    System.out.println("Vehicle " + id + " could not be sent for maintenance");
                    return;
                }
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Id is incorrect");
        }
    }

    private void completeVanMaintenance(Scanner sc, String id) {
        boolean flag = false;
        for (Van van : vans) {
            if ((van.getVehicleId()).equals(id)) {
                System.out.print("Maintenance completion date (dd/mm/yyyy) :");
                String date = sc.next();
                DateTime maintenanceDate = parseDate(date);
                if (checkMaintenanceStatus(id, van, maintenanceDate)) return;
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Id is incorrect");
        }
    }

    private void completeCarMaintenance(Scanner sc, String id) {
        boolean flag = false;
        for (Car car : cars) {
            if ((car.getVehicleId()).equals(id)) {
                System.out.print("Maintenance completion date (dd/mm/yyyy): ");
                sc.next();
                if (car.completeMaintenance())
                    System.out.println("Vehicle " + id + " has all maintenance completed and ready for rent");
                else {
                    System.out.println("Vehicle " + id + " could not complete maintenance");
                    return;
                }
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("ID is incorrect, Please try again");
        }
    }
}