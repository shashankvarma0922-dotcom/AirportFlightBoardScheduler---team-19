import java.util.Scanner;

public class AirportFlightBoard {

    static final String AIRPORT_CODE = "HYD";     // constant
    static Scanner sc = new Scanner(System.in);

    // ---------- CO1: Variables & Data Types ----------
    static void flightDetails() {
        int flightNo = 6202;
        String airline = "IndiGo";
        double fare = 4599.50;
        char status = 'D';
        boolean isIntl = false;
        int delay = 25 + 10;
        boolean isDelayed = delay > 0;
        boolean canBoard = isDelayed && !isIntl;

        long gateNo = (long) 7;            // typecasting
        int roundedFare = (int) fare;      // typecasting

        System.out.println(AIRPORT_CODE + " " + flightNo + " " + airline
                + " Fare:" + roundedFare + " Delay:" + delay
                + " Gate:" + gateNo + " Status:" + status + " CanBoard:" + canBoard);
    }

    // ---------- CO2: Conditional Statements ----------
    static void gateStatus() {
        System.out.print("How many minutes is the flight delayed: ");
        int delay = sc.nextInt();

        if (delay == 0) {
            System.out.println("Boarding as scheduled");
        } else if (delay > 30) {
            System.out.println("Major delay, recheck board");
        } else if (delay > 20) {
            System.out.println("Minor delay, proceed to gate");
        } else if (delay > 0) {
            System.out.println("Slight delay, boarding soon");
        } else {
            System.out.println("Invalid delay");
        }

        System.out.print("Enter flight status (O=On time, D=Delayed): ");
        char code = Character.toUpperCase(sc.next().charAt(0));
        switch (code) {
            case 'O': System.out.println("On Time"); break;
            case 'D': System.out.println("Delayed " + delay + "m"); break;
            default:  System.out.println("Status unknown");
        }
    }

    // ---------- CO2: Loops ----------
    static void gateLoops() {
        int[] gStart = {900, 930, 1000};
        int[] gEnd   = {945, 1010, 1030};
        int n = gStart.length;

        System.out.print("Enter minimum gap (minutes): ");
        int gap = sc.nextInt();

        for (int i = 0; i < n; i++) {                 // nested loops
            for (int j = i + 1; j < n; j++) {
                if (gStart[j] - gEnd[i] < gap) {
                    System.out.println("Conflict: F" + i + " & F" + j);
                }
            }
        }

        int count = 0;
        while (count < n) {
            System.out.println("Flight " + count + " verified");
            count++;
        }
    }

    // ---------- Module 3: Methods ----------
    static int calcDelay(int sched, int actual) {      // parameters + return value
        return actual - sched;
    }

    static void printFlight(String no) {               // overload 1
        System.out.println("Flight: " + no);
    }

    static void printFlight(String no, String gate) {  // overload 2
        System.out.println("Flight: " + no + " Gate: " + gate);
    }

    static void flightOps() {
        System.out.print("Enter flight number: ");
        String flightNo = sc.next();
        System.out.print("Enter scheduled time: ");
        int sched = sc.nextInt();
        System.out.print("Enter actual time: ");
        int actual = sc.nextInt();

        int delay = calcDelay(sched, actual);
        System.out.println("Delay: " + delay + " min");
        printFlight(flightNo);
        printFlight(flightNo, "G4");
    }

    // ---------- Module 3 (contd.): Arrays ----------
    static void fareArrays() {
        System.out.print("Enter number of flights: ");
        int n = sc.nextInt();

        double[] fares = new double[n];                // 1D array
        for (int i = 0; i < n; i++) {
            System.out.print("Fare " + i + ": ");
            fares[i] = sc.nextDouble();
        }

        int[][] schedule = {{900, 945}, {930, 1010}};  // 2D array

        double total = 0;
        for (double f : fares) total += f;             // summation
        double avg = total / n;                        // average

        int longSlots = 0;                             // counting
        for (int[] slot : schedule)
            if (slot[1] - slot[0] > 40) longSlots++;

        System.out.print("Enter fare to search: ");
        double target = sc.nextDouble();
        int index = -1;
        for (int i = 0; i < n; i++) {                  // linear search
            if (fares[i] == target) { index = i; break; }
        }

        System.out.println("Total:" + total + " Avg:" + avg);
        System.out.println("LongSlots:" + longSlots + " Index:" + index);
    }

    // ---------- Main menu ----------
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== AIRPORT FLIGHT BOARD & SCHEDULER =====");
            System.out.println("1. Flight details");
            System.out.println("2. Gate status");
            System.out.println("3. Gate conflicts");
            System.out.println("4. Flight delay");
            System.out.println("5. Fares");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: flightDetails(); break;
                case 2: gateStatus(); break;
                case 3: gateLoops(); break;
                case 4: flightOps(); break;
                case 5: fareArrays(); break;
                case 0: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice");
            }
        } while (choice != 0);

        sc.close();
    }
}