import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;

public class UENValidationModule {
    //Valid entity codes for new-type UEN
    private final String[] validEntityCodes = {"LP", "LL", "FC", "PF", "RF", "MQ", "MM","NB", "CC", "CS", "MB","FM",
            "GS","DP","CP","NR", "CM", "CD", "MD", "HS", "VH", "CH", "MH", "CL", "XL", "CX", "HC", "RP",
            "TU", "TC", "FB", "FN", "PA", "PB", "SS", "MC", "SM", "GA", "GB"};

    public void run(Scanner sc) {
        System.out.println("==UEN Validation ==");

        while (true) {
            mainAppFlow(sc);
            System.out.println("Type 1 to continue, or any other number to go back to the main menu.");
            int option = sc.nextInt();
            if (option != 1) {
                break;
            }
        }
    }

    public void mainAppFlow(Scanner sc) {
        System.out.println("Type in a UEN to check if it is valid");
        String toCheck = sc.nextLine();
        if (validUEN(toCheck)) {
            System.out.println(toCheck + " is a correctly formatted UEN.");
        } else {
            System.out.println(toCheck + " is an incorrectly formatted UEN.");
        }
    }

    /**
     * A Valid UEN is a number fulfilled in the criteria listed in
     * https://www.uen.gov.sg/ueninternet/faces/pages/admin/aboutUEN.jspx
     * @param uen The string to test
     * @return true if uen is valid, false otherwise.
     */
    public boolean validUEN(String uen) {
        int year = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore")).get(Calendar.YEAR);
        if (uen.length() == 9) {
            //Check the first 8 characters and ensure it is a digit.
            for (int i = 0; i < 8; i++) {
                if(!Character.isDigit(uen.charAt(i))) {
                    return false;
                }
            }
            //Check Last character to ensure it is a letter.
            return Character.isLetter(uen.charAt(8));
        } else if (uen.length() == 10) {
            //Check if new or old
            if (uen.charAt(0) == 'R' || uen.charAt(0) == 'S' || uen.charAt(0) == 'T') {
                //Handle new UEN

                return Character.isLetter(uen.charAt(9));
            } else {
                //Handle old 10-digit format UEN
                //Step 1: Check if all except for last character is a digit.
                for (int i = 0; i < 9; i++) {
                    if (!Character.isDigit(uen.charAt(i))) {
                        return false;
                    }
                }
                //Step 2: Check if the year is correct

                return Character.isLetter(uen.charAt(9));
            }
        } else { // UEN must be 9 or 10 digits long.
            return false;
        }

    }
}
