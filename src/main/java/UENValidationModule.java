import java.util.*;

public class UENValidationModule {
    //Valid entity codes for new-type UEN
    private final Set<String> validEntityCodes = new HashSet<>(Arrays.asList("LP", "LL", "FC", "PF", "RF", "MQ",
            "MM", "NB", "CC", "CS", "MB","FM", "GS","DP","CP", "NR", "CM", "CD", "MD", "HS", "VH", "CH", "MH", "CL",
            "XL", "CX", "HC", "RP", "TU", "TC", "FB", "FN", "PA", "PB", "SS", "MC", "SM", "GA", "GB"));

    private final Set<Character> validNewUENYearLetter = new HashSet<>(Arrays.asList('R', 'S', 'T'));
    private final char LATEST_YEAR_CHARACTER = 'T';
    private final int TWO_DIGIT_YEAR_OFFSET = 2000;

    public void run(Scanner sc) {
        System.out.println("==UEN Validation ==");

        while (true) {
            sc.nextLine();
            mainAppFlow(sc);
            System.out.println("Type 1 to continue, or any other number to go back to the main menu.");
            int option = sc.nextInt();
            if (option != 1) {
                break;
            }
        }
    }

    public void mainAppFlow(Scanner sc) {
        System.out.println("Type in a UEN to check if it is valid.");
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
     * @param input The string to test
     * @return true if uen is valid, false otherwise
     */
    public boolean validUEN(String input) {
        String uen = input.toUpperCase();
        int currentYear = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore")).get(Calendar.YEAR);
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
            if (validNewUENYearLetter.contains(uen.charAt(0))) {
                //Step 1: Check if characters 2, 3, 6, 7, 8, 9 are digits
                if (!Character.isDigit(uen.charAt(1)) || !Character.isDigit(uen.charAt(2)) ||
                        !Character.isDigit(uen.charAt(5)) || !Character.isDigit(uen.charAt(6)) ||
                        !Character.isDigit(uen.charAt(7)) || !Character.isDigit(uen.charAt(8))) {
                    return false;
                }
                //Step 2: Check if year indicated is before today.
                if (uen.charAt(0) == LATEST_YEAR_CHARACTER) {
                    int uenYear = Integer.parseInt(uen.substring(1, 3)) + TWO_DIGIT_YEAR_OFFSET;
                    if (uenYear > currentYear) {
                        return false;
                    }
                }
                //Step 3: Check if the "entity code" is in the list.
                String codeToCheck = uen.substring(3,5);
                if (!validEntityCodes.contains(codeToCheck)) {
                    return false;
                }

                //Step 4: Check if check letter is at the end
                return Character.isLetter(uen.charAt(9));
            } else {
                //Handle old 10-digit format UEN
                //Step 1: Check if all except for last character is a digit.
                for (int i = 0; i < 9; i++) {
                    if (!Character.isDigit(uen.charAt(i))) {
                        return false;
                    }
                }
                //Step 2: Check if the year is correct. Since first 4 characters are confirmed to be a digit in step 1,
                //there is no need for a try-catch block.
                int uenYearCheck = Integer.parseInt(uen.substring(0,4));
                if (uenYearCheck > currentYear) {
                    return false;
                }
                //Step 3: Check last character is a letter
                return Character.isLetter(uen.charAt(9));
            }
        } else { // UEN must be 9 or 10 digits long.
            return false;
        }
    }
}
