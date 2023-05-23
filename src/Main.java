import model.*;
import service.*;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
public class Main {
    public static void main(String[] args) throws ParseException, IOException {


        Airline airline =new Airline();

        airline.setup();
        while(true) {
            System.out.println("\n1: Actions on flights and aircrafts.✈️");
            System.out.println("2: Actions on persons.☺");
            System.out.println("3: Exit.❌");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Pick an option : ");
            int optiune = scanner.nextInt();
            switch (optiune) {
                case 1: {
                    airline.menuFligthsAircrafts();
                    break;
                }

                case 2: {
                    airline.menuPersons();
                    break;
                }

                case 3: {
                    System.exit(0);
                }
                default: {
                    System.out.println("⟲Select an available option!\n");
                }
            }



        }

    }

}