package Servlets.PMS.PMS1.DCB.methods;

import java.time.Month;

public class MonthConverter {
    public static String monthNumberToName(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("Month number must be between 1 and 12");
        }
        return Month.of(monthNumber).name();
    }

    public static void main(String[] args) {
        int monthNumber = 3; // Example month number
        String monthName = monthNumberToName(monthNumber);
        System.out.println("Month name: " + monthName); // Output: "Month name: MARCH"
    }
}
