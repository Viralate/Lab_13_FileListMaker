import java.util.Scanner;

public class SafeInput {

    //Part A
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ":");
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;
    }
    //part b
    public static int getInt(Scanner pipe, String prompt) {
        System.out.print(prompt + ": ");
        while (!pipe.hasNextInt()) {
            pipe.next(); // Consume the invalid input
            System.out.println("Invalid input. Please enter an integer.");
            System.out.print(prompt + ": ");
        }
        int value = pipe.nextInt();
        pipe.nextLine(); // clears line
        return value;
    }

    //part c
    public static double getDouble(Scanner pipe, String prompt) {
        System.out.print(prompt + ": ");
        while (!pipe.hasNextDouble()) {
            pipe.next(); //consumes invalid input
            System.out.println("Invalid input. Please enter a double.");
            System.out.print(prompt + ": ");
        }
        double value = pipe.nextDouble();
        pipe.nextLine(); //clears line
        return value;
    }

    //part D
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retInt = 0;
        do {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            while (!pipe.hasNextInt()) {
                pipe.next(); //consumes invalid input
                System.out.println("Invalid input. Please enter an integer.");
                System.out.print(prompt + " [" + low + " - " + high + "]: ");
            }
            retInt = pipe.nextInt();
            if (retInt < low || retInt > high) {
                System.out.println("Error: Input must be between " + low + " and " + high + ".");   
            }
        } while (retInt < low || retInt > high);
        pipe.nextLine(); //clears line
        return retInt;
    }

    //part E
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retDouble = 0;
        do {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            while (!pipe.hasNextDouble()) {
                pipe.next(); //consumes invalid input
                System.out.println("Invalid input. Please enter a double.");
                System.out.print(prompt + " [" + low + " - " + high + "] :");
            }
            retDouble = pipe.nextDouble();
            if (retDouble < low || retDouble > high) {
                System.out.println("Error: Input must be between " + low + " and " + high + ".");
            }
        } while (retDouble < low || retDouble > high);
        pipe.nextLine(); //clears line
        return retDouble;
    }

    //part F
    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String input = "";
        do {
            System.out.print(prompt + " [Y/N]: ");
            input = pipe.nextLine().trim().toUpperCase();
            if (!input.equals("Y") && !input.equals("N")) {
                System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            }
        } while (!input.equals("Y") && !input.equals("N"));

        return input.equals("Y");
    }

    //part G
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String input = "";
        System.out.print(prompt + ": ");
        while (!pipe.hasNextLine()) {
            System.out.println("Invalid input.");
            System.out.print(prompt + ": ");
            pipe.next(); // clears line
        }
        input = pipe.nextLine();
        while (!input.matches(regEx)) {
            System.out.println("Invalid input. Please match the required pattern.");
            System.out.print(prompt + ": ");
            input = pipe.nextLine(); //clear line
        }
        return input;
    }

    // Program 6: Pretty Header
    public static void prettyHeader(String msg) {
        int padding = (60 - msg.length() - 6) / 2; // 6 for the stars on each side
        String line = "*".repeat(60);
        String paddedMsg = "*".repeat(3) + " ".repeat(padding) + msg + " ".repeat(padding);
        if (paddedMsg.length() < 60) paddedMsg += " ".repeat(60 - paddedMsg.length());
        paddedMsg += "*".repeat(3);
    
        System.out.println(line);
        System.out.println(paddedMsg);
        System.out.println(line);
    }
}