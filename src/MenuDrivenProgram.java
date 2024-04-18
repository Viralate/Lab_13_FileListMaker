import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class MenuDrivenProgram {
    // declare universal variables
    private static ArrayList<String> itemList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        // initiate boolean
        boolean running = true;
        while (running) {
            // show menu
            SafeInput.prettyHeader("Menu");
            displayList();
            displayMenu();
            // prompt the user to choose an option
            String choice = SafeInput.getRegExString(scanner, "Choose an option (A, D, V, O, S, C, Q)", "[AaDdVvOoSsCcQq]");
            switch (choice.toUpperCase()) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "V":
                    viewList();
                    break;
                case "O":
                    openList();
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    running = quitProgram();
                    break;
            }
        }
    }
    //display list to user
    private static void displayList() {
        System.out.println("Current List:");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println((i + 1) + ". " + itemList.get(i));
        }
    }
    //display menu options to user
    private static void displayMenu() {
        System.out.println("\nMenu Options:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit");
    }
    //logic to add item to the list
    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(scanner, "Enter item to add:");
        itemList.add(item);
        System.out.println("\"" + item + "\" has been added to the list.");
        needsToBeSaved = true;
    }
    //logic to delete item from list
    private static void deleteItem() {
        if (itemList.isEmpty()) {
            System.out.println("The list is currently empty.");
            return;
        }
        int itemNumber = SafeInput.getRangedInt(scanner, "Enter the number of the item to delete:", 1, itemList.size()) - 1;
        String removedItem = itemList.remove(itemNumber);
        System.out.println("\"" + removedItem + "\" has been removed from the list.");
        needsToBeSaved = true;
    }
    // view list
    private static void viewList() {
        displayList();
    }
    //logic to open list
    private static void openList() {
        if (checkSave()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                itemList.clear();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        itemList.add(line);
                    }
                    needsToBeSaved = false;
                } catch (IOException e) {
                    System.out.println("Error reading the file: " + e.getMessage());
                }
            }
        }
    }
    //logic to save list to file directory
    private static void saveList() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getParentFile(), file.getName() + ".txt");
            }
            try (PrintWriter out = new PrintWriter(file)) {
                for (String item : itemList) {
                    out.println(item);
                }
                needsToBeSaved = false;
            } catch (IOException e) {
                System.out.println("Error writing to the file: " + e.getMessage());
            }
        }
    }
    //logic to clear list
    private static void clearList() {
        itemList.clear();
        needsToBeSaved = true;
        System.out.println("The list has been cleared.");
    }
    //logic to close program
    private static boolean quitProgram() {
        if (!needsToBeSaved || checkSave()) {
            return !SafeInput.getYNConfirm(scanner, "Are you sure you want to quit?");
        }
        return false;
    }
    //logic to prompt user to save change or to not save changes
    private static boolean checkSave() {
        if (needsToBeSaved && !itemList.isEmpty()) {
            if (SafeInput.getYNConfirm(scanner, "You have unsaved changes. Would you like to save them now? (Y/N)")) {
                saveList();
            }
        }
        return true;
    }
}
