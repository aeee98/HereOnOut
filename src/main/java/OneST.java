import java.util.Scanner;

public class OneST {
    Scanner sc = new Scanner(System.in);
    public void run() {
        while (true) {
            System.out.println("Welcome to One ST, please select an option\n");
            System.out.println("1) Validation of UEN of company.");
            System.out.println("2) Weather Forecast of Singapore");
            System.out.println("-1) Exit");
            int option = sc.nextInt();

            if (option == -1) {
                break;
            }
            switch (option) {
                case 1:
                    UENValidationModule uen = new UENValidationModule();
                    uen.run(sc);
                    break;
                case 2:
                    WeatherForecastModule wf = new WeatherForecastModule();
                    wf.run(sc);
                    break;
            }
        }
        System.out.println("Thank you for using OneST.");
    }

}
