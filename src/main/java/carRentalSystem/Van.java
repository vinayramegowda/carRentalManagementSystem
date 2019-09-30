
/**
 * Class to save all the van details
 * This class contains all the calculations and details of the van
 * It extends Vehicle so it has all the features of a vehicle
 */
class Van extends Vehicle{

    private double rate=235;
    private double lateFee=299;

    //Constructor to accept van details
    Van(String vehicleId,int year,String make,String model,int status,VehicleType vehicleType)
    {
        super(vehicleId,year,make,model,status,vehicleType);
        this.rate=rate;
    }

    /**
     * Used to add either cars or vans to the list
     * @param endDate,startDate accepts start date and end date
     * @return lateFee the late fee
     */
    public double getLateFee(DateTime endDate,DateTime startDate){
        if(DateTime.diffDays(endDate,startDate)>0)
            return this.lateFee* DateTime.diffDays(endDate,startDate);
        else
            return this.lateFee=0.0;
    }

    /**
     * Used to add either cars or vans to the list
     * @param returnDate accepts the date as ro when it has to be returned
     * @return Returns true if returned else false with appropriate messages
     */
    public  boolean returnVehicle(DateTime returnDate)
    {
        String vehicleType;
        if(this.Vehicle_id.contains("C_"))
            vehicleType="car";
        else
            vehicleType="van";
        if(this.vehicleStatus!=0)
        {
        DateTime estimatedDate= this.records[this.getLastElementIndex()].getEstimatedReturnDate();
        DateTime rentDate= this.records[this.getLastElementIndex()].getRentDate();

        if (vehicleType.equals("van") && DateTime.diffDays(returnDate,rentDate)<1){
            return false;
        }
        else{
            double rent=this.rate* DateTime.diffDays(returnDate,this.records[this.getLastElementIndex()].getRentDate())  ;
            this.records[this.getLastElementIndex()].setData(returnDate,rent,this.getLateFee(returnDate,estimatedDate));
            this.vehicleStatus=0;
            return true;
        }}else return false;
    }

    /**
     * Used to add either cars or vans to the list
     * @param completionDate accepts the date as ro when it has to be maintained
     * @return Returns true if returned else false with appropriate messages
     */
    public boolean completeMaintenance(DateTime completionDate)
    {
        if(this.vehicleStatus!=2 && DateTime.diffDays(completionDate,this.vehicleType.getLastMaintenance())<12)
            return false;
        this.vehicleType.setLastMaintenance(completionDate);
        this.vehicleStatus=0;
        return true;
    }

    /**
     * Method used to get details of van
     */


    public String toString()
    {
        String details = super.toString();
        DateTime lastMaintenanceDate= this.vehicleType.getLastMaintenance();
        details += ":"+ lastMaintenanceDate.toString();
        return details;
    }

    /**
     * Method used to get details of van with their rental history
     * Prints the rental record of van
     */

    public String getDetails()
    {
        String details =super.getDetails();
        details+= "\nLast maintenance date:\t"+(this.vehicleType.getLastMaintenance()).toString();
        if(this.records[0]==null)
            details+="\nRENTAL RECORD:\tempty";
        else{
            details+="\nRENTAL RECORD:\n";
			int count=0;
			for(int index=0;this.records[index]!=null;index++)
				count++;
            for(int index=count-1;index>=0;index--)
            {
                details+= this.records[index].getDetails()+"                     \n";
                for(int innerIndex=0;innerIndex<10;innerIndex++)
                    details+= "-";
                details+= "                     \n";
            }
        }
        return details;
    }
	
	
}