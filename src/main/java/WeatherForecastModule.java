import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import obj.Forecast;
import obj.WeatherForecast;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class WeatherForecastModule {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private WeatherForecast wc = null;

    public void run(Scanner sc) {
        System.out.println("== Weather Forecast ==");
        while (true) {
            sc.nextLine();
            checkAndUpdateWeatherForecast();
            mainAppFlow(sc);
            System.out.println("Type 1 to continue, or any other number to go back to the main menu.");
            int option = sc.nextInt();
            if (option != 1) {
                break;
            }
        }
    }

    public void mainAppFlow(Scanner sc) {
        System.out.println("Type in a valid area in Singapore.");
        String area = capitaliseAllWords(sc.nextLine().toLowerCase());
        getWeather(area);
    }

    /**
     * Checks if data cached is expired. And if so resets the new object.
     */
    public void checkAndUpdateWeatherForecast() {
        if (wc == null || wc.getItems().get(0).getValidPeriod().getEnd().before(new Date())) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast"))
                    .build();

            try {
                String response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                        .create();

                wc = gson.fromJson(response, WeatherForecast.class);

            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            } catch (InterruptedException e2) {
                System.out.println("Error, Connection interrupted");
            }
        }
    }

    public boolean getWeather(String area) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast"))
                .build();

        try {
            String response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                    .create();

            List<Forecast> forecasts = gson.fromJson(response, WeatherForecast.class)
                    .getItems().get(0).getForecasts();
            boolean found = false;

            for (Forecast forecast : forecasts) {
                if (forecast.getArea().equals(area)) {
                    System.out.println("Weather Forecast for " + area + ": " + forecast.getForecast());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Area with the name \"" + area + "\" not found.");
            }
            return found;
        } catch (IOException e1) {
            System.out.println(e1.getMessage());
            return false;
        } catch (InterruptedException e2) {
            System.out.println("Error, Connection interrupted");
            return false;
        }
    }

    /**
     * Helper function to capitalise all first letters of the word
     * @param str string of statements to capitalise
     * @return the string of statements with all first letters of each word capitalised
     */
    private String capitaliseAllWords(String str) {
        String words[] = str.split("\\s");
        String capitalizeWord = "";
        for (String word : words) {
            String firstCharacter = word.substring(0,1);
            String otherCharacters = word.substring(1);
            capitalizeWord += firstCharacter.toUpperCase() + otherCharacters + " ";
        }
        return capitalizeWord.trim();
    }
}
