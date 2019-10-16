package carRentalSystem;

public class RentalRecord extends DateTime {
    private String RentId;
    private DateTime RentDate;
    private DateTime EstimatedReturnDate;
    private DateTime ActualReturnDate;
    private Double RentalFee;
    private Double LateFee;

    RentalRecord(String RentId, DateTime RentDate, DateTime EstimatedReturnDate) {
        this.RentId = RentId;
        this.RentDate = RentDate;
        this.EstimatedReturnDate = EstimatedReturnDate;
    }

    void setData(DateTime ActualDate, Double RentalFee, Double LateFee) {
        this.ActualReturnDate = ActualDate;
        this.RentalFee = RentalFee;
        this.LateFee = LateFee;
    }

    DateTime getEstimatedReturnDate() {
        return this.EstimatedReturnDate;
    }

    DateTime getRentDate() {
        return this.RentDate;
    }

    public String toString() {
        if (this.ActualReturnDate == null && this.RentalFee == null && this.LateFee == null) {
            return this.RentId + ":" + this.RentDate.toString() + ":" + this.EstimatedReturnDate.toString() + ":none:none:none";
        } else {
            return this.RentId + ":" + this.RentDate.toString() + ":" + this.EstimatedReturnDate.toString() + ":" + this.ActualReturnDate.toString() + ":" + this.RentalFee.toString() + ":" + this.LateFee.toString();
        }
    }

    String getDetails() {
        if (this.ActualReturnDate == null && this.RentalFee == null && this.LateFee == null) {
            return "Record ID:             " + this.RentId
                    + "\nRent Date:             " + this.RentDate.toString()
                    + "\nEstimated Return Date: " + this.EstimatedReturnDate.toString();
        } else {
            return "Record ID:             " + this.RentId
                    + "\nRent Date:             " + this.RentDate.toString()
                    + "\nEstimated Return Date: " + this.EstimatedReturnDate.toString()
                    + "\nActual Return Date:    " + this.ActualReturnDate.toString()
                    + "\nRental Fee:            " + String.format("%.2f", this.RentalFee) +
                    "\nLate Fee:              " + String.format("%.2f", this.LateFee);
        }
    }


}