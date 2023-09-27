import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<String> rooms = new ArrayList<>();
    private static int userRoom = 10;
    private static int code = 1234;
    private static boolean securityOn = true;
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static int getUserRoom() {
        return userRoom;
    }

    public static void setUserRoom(int userRoom) {
        Main.userRoom = userRoom;
    }

    public static int getCode() {
        return code;
    }

    public static boolean isSecurityOn() {
        return securityOn;
    }

    public static void setSecurityOn(boolean securityOn) {
        Main.securityOn = securityOn;
    }

    public static void pause() {
        System.out.print("\nPress enter to continue...");
        scanner.nextLine();
    }

    public static void exit() {
        System.out.println("Do you want to exit? y/n");
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("y"))
            System.exit(0);
    }

    // Toggle the security system on/off based on user input
    public static void securityToggle() {
        int tries = 3;
        while (tries > 0) {
            System.out.println("You have " + tries + " tries left to put in the code to the security system.");
            System.out.print("Enter the code: ");
            String userInput = scanner.nextLine();
            if (userInput.equals(String.valueOf(getCode()))) {
                System.out.println("Correct! You Turned " + (isSecurityOn() ? "off" : "on") + " the security system.");
                setSecurityOn(!isSecurityOn());
                break;
            } else {
                tries--;
                if (tries > 0)
                    System.out.println("Wrong code, try again.");
            }
        }
        if (tries == 0) {
            System.out.println("Wrong code, the security alarm will now start.");
            setSecurityOn(true);
        }
    }

    // Print options for the user to choose from based on their location
    public static void printRoomOptions(List<String> rooms, boolean isSecurityRoom, int... roomNumbers) {
        // Print every room the user can move to
        for (int i = 0; i < roomNumbers.length; i++) {
            int roomNumber = roomNumbers[i];
            System.out.println((i + 1) + ". Move to " + rooms.get(roomNumber));
        }
        int optionCounter = roomNumbers.length + 1;

        if (isSecurityRoom)
            System.out.println(optionCounter++ + ". Turn " + (isSecurityOn() ? "off" : "on") + " the security");
        System.out.println(optionCounter++ + ". Simulate break-in");
        System.out.println(optionCounter++ + ". Simulate fire");
        System.out.println(optionCounter + ". Simulate movement in the backyard");
        System.out.println("0. Exit");
        System.out.print("Enter a number: ");
    }

    // Handle user input based on their selection
    public static void handleUserInput(String userInput, boolean isSecurityRoom, int... roomNumbers) {
        boolean isPresent = false;
        // Check if the user input is a valid room number
        for (int i = 1; i < roomNumbers.length + 1; i++) {
            if (i == Integer.parseInt(userInput)) {
                System.out.println(i);
                isPresent = true;
                break;
            }
        }
        if (isPresent)
            setUserRoom(roomNumbers[Integer.parseInt(userInput) - 1]);

        int optionCounter = roomNumbers.length + 1;
        // If the room can toggle security, handle security-related options
        if (isSecurityRoom) {
            if (Integer.parseInt(userInput) == optionCounter)
                securityToggle();
        } else
            optionCounter -= 1;
        // Handle other general options
        if (Integer.parseInt(userInput) == optionCounter + 1) {
            int breakInRoom = random.nextInt(0, 9);
            BreakInSimulator.simulateBreakIn(breakInRoom, rooms, getUserRoom(), isSecurityOn(), random);
        } else if (Integer.parseInt(userInput) == optionCounter + 2) {
            int fireRoom = random.nextInt(0, 9);
            FireSimulator.simulation(fireRoom, rooms, userRoom);
        } else if (Integer.parseInt(userInput) == optionCounter + 3)
            MovementSimulator.simulateBackyardMovement(rooms, getUserRoom(), isSecurityOn(), random);
        else if (Integer.parseInt(userInput) == 0)
            exit();
        else
            return;
    }

    // Print the entire user interface in one line per unique room
    public static void userInterface(List<String> rooms, boolean isSecurityRoom, int... roomNumbers) {
        printRoomOptions(rooms, isSecurityRoom, roomNumbers);
        String userInput = scanner.nextLine();
        handleUserInput(userInput, isSecurityRoom, roomNumbers);
    }

    public static void main(String[] args) {
        rooms.add("Entrance");
        rooms.add("Living Room");
        rooms.add("Garage");
        rooms.add("Bedroom 1");
        rooms.add("Bedroom 2");
        rooms.add("Bedroom 5");
        rooms.add("Bedroom 3");
        rooms.add("Bedroom 4");
        rooms.add("Kitchen");
        rooms.add("Backyard");
        rooms.add("Porch");

        while (true) {
            System.out.println("\n---Security System:" + (isSecurityOn() ? "ON" : "OFF") + "---");
            System.out.println("Current room: " + rooms.get(getUserRoom()));
            System.out.println("What would you like to do?");
            switch (getUserRoom()) {
                case 0 -> userInterface(rooms, true, 10, 1, 2, 3, 4, 5);
                case 1 -> userInterface(rooms, true, 0, 6, 7, 8, 9);
                case 2 -> userInterface(rooms, true, 0, 10);
                case 3, 4, 5 -> userInterface(rooms, false, 0);
                case 6, 7, 8, 9 -> userInterface(rooms, false, 1);
                case 10 -> userInterface(rooms, false, 0, 2);
                default -> {
                    break;
                }
            }
        }
    }
}
