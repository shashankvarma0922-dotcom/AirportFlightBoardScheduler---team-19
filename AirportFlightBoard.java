
import java.io.*;
import java.util.Scanner;
 
// Airport Flight Board & Scheduler (simple version)
public class AirportFlightBoard {
 
    static final int MAX = 5;   // Constant: number of flights
 
    // Interface
    interface Displayable {
        void display();
    }
 
    // Custom exception (Exception hierarchy)
    static class FlightNotFoundException extends Exception {
        FlightNotFoundException(String msg) {
            super(msg);
        }
    }
 
    // Abstract class
    static abstract class Flight implements Displayable {
        String number, city;
        int delay, status;   // status: 1 = On Time, 2 = Delayed, 3 = Cancelled
 
        Flight(String number, String city, int delay, int status) {
            this.number = number;
            this.city = city;
            this.delay = delay;
            this.status = status;
        }
 
        abstract String type();
 
        String statusText() {
            switch (status) {       // Switch-case
                case 1:  return "On Time";
                case 2:  return "Delayed";
                case 3:  return "Cancelled";
                default: return "Unknown";
            }
        }
 
        public void display() {
            System.out.println(type() + " | " + number + " | " + city + " | "
                    + statusText() + " | Delay: " + delay + " min");
        }
    }
 
    // Inheritance
    static class Departure extends Flight {
        Departure(String n, String c, int d, int s) { super(n, c, d, s); }
        String type() { return "Departure"; }
    }
 
    static class Arrival extends Flight {
        Arrival(String n, String c, int d, int s) { super(n, c, d, s); }
        String type() { return "Arrival  "; }
    }
 
    // Method with return value + if / else-if ladder
    static String delayCategory(int d) {
        if (d == 0)       return "No delay";
        else if (d <= 30) return "Minor delay";
        else if (d <= 60) return "Moderate delay";
        else              return "Major delay";
    }
 
    // Method overloading: search by flight number
    static int search(Flight[] f, String number) {
        for (int i = 0; i < f.length; i++) {
            if (f[i].number.equals(number)) return i;
        }
        return -1;
    }
 
    // Method overloading: search by delay time
    static int search(Flight[] f, int delay) {
        for (int i = 0; i < f.length; i++) {
            if (f[i].delay == delay) return i;
        }
        return -1;
    }
 
    // File handling: save
    static void saveToFile(Flight[] f) {
        try {
            PrintWriter out = new PrintWriter("flights.txt");
            for (int i = 0; i < f.length; i++) {
                out.println(f[i].number + "," + f[i].city + "," + f[i].delay);
            }
            out.close();
            System.out.println("Saved to flights.txt");
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
 
    // File handling: read
    static void readFromFile() {
        try {
            Scanner sc = new Scanner(new File("flights.txt"));
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
 
    public static void main(String[] args) {
 
        // 1D array of objects (Polymorphism: Flight holds Departure and Arrival)
        Flight[] flights = new Flight[MAX];
        flights[0] = new Departure("AI101", "Delhi", 0, 1);
        flights[1] = new Arrival("6E202", "Mumbai", 25, 2);
        flights[2] = new Departure("SG303", "Chennai", 70, 2);
        flights[3] = new Arrival("UK404", "Kolkata", 0, 1);
        flights[4] = new Departure("AI505", "Pune", 0, 3);
 
        // Display board
        System.out.println("===== FLIGHT BOARD =====");
        for (int i = 0; i < flights.length; i++) {
            flights[i].display();   // Polymorphism
            System.out.println("   -> " + delayCategory(flights[i].delay));
        }
 
        // Sum, average, count
        int total = 0, delayedCount = 0;
        for (int i = 0; i < flights.length; i++) {
            total += flights[i].delay;
            if (flights[i].delay > 0) delayedCount++;
        }
        double average = (double) total / MAX;
        System.out.println("\nTotal delay: " + total + " min");
        System.out.println("Average delay: " + average + " min");
        System.out.println("Delayed flights: " + delayedCount);
 
        // 2D array: gates (1 = occupied, 0 = free)
        int[][] gates = { {1, 0, 1}, {0, 1, 0} };
        int occupied = 0;
        System.out.println("\n===== GATES =====");
        for (int i = 0; i < gates.length; i++) {        // Nested loops
            for (int j = 0; j < gates[i].length; j++) {
                System.out.print("G" + (i + 1) + (j + 1) + ":"
                        + (gates[i][j] == 1 ? "Busy " : "Free "));
                if (gates[i][j] == 1) occupied++;
            }
            System.out.println();
        }
        System.out.println("Occupied gates: " + occupied);
 
        // Searching + Exception handling
        System.out.println("\n===== SEARCH =====");
        try {
            int index = search(flights, "SG303");
            if (index == -1) throw new FlightNotFoundException("Flight not found");
            flights[index].display();
 
            index = search(flights, "XX999");
            if (index == -1) throw new FlightNotFoundException("Flight XX999 not found");
            flights[index].display();
        } catch (FlightNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
 
        // File handling
        System.out.println("\n===== FILE =====");
        saveToFile(flights);
        readFromFile();
    }
}
 
