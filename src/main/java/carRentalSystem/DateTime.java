package carRentalSystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

public class DateTime {
    private long time;

    DateTime() {
        time = System.currentTimeMillis();
    }

    DateTime(DateTime startDate, int setClockForwardInDays) {
        long advance = ((setClockForwardInDays * 24L) * 60L) * 60000L;
        time = startDate.getTime() + advance;
    }

    public DateTime(int day, int month, int year) {
        setDate(day, month, year);
    }

    private long getTime() {
        return time;
    }

    // get the name of the day of thi DateTime object
    String getNameOfDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(time);
    }

    public String toString() {
        return getFormattedDate();
    }

    private String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long currentTime = getTime();
        Date gct = new Date(currentTime);

        return sdf.format(gct);
    }

    String getEightDigitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        long currentTime = getTime();
        Date gct = new Date(currentTime);

        return sdf.format(gct);
    }

    // returns difference in days
    static int diffDays(DateTime endDate, DateTime startDate) {
        final long HOURS_IN_DAY = 24L;
        final int MINUTES_IN_HOUR = 60;
        final int SECONDS_IN_MINUTES = 60;
        final int MILLISECONDS_IN_SECOND = 1000;
        long convertToDays = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLISECONDS_IN_SECOND;
        long hirePeriod = endDate.getTime() - startDate.getTime();
        double difference = (double) hirePeriod / (double) convertToDays;
        return (int) Math.round(difference);
    }

    private void setDate(int day, int month, int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0);

        java.util.Date date = calendar.getTime();

        time = date.getTime();
    }
}