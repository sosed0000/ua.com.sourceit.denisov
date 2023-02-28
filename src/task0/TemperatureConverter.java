package task0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TemperatureConverter {


    public static void main(String[] args) {

        double celsius;
        System.out.print("Please enter the temperature in Celsius: ");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            celsius = Double.parseDouble(reader.readLine());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format.\nPlease enter a number!");
            throw new RuntimeException(e);
        }

        double fahrenheit = convertCelsiusToFahrenheit(celsius);
        System.out.printf("%.2f ºC = %.2f ºF", celsius, fahrenheit);


    }

    public static double convertCelsiusToFahrenheit(double celsius) {
        double fahrenheit = celsius * 9/5 + 32;
//        або так
//        double fahrenheit = (double) 9/5 * celsius + 32;
        return fahrenheit;
    }
}