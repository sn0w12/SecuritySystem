import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<String> rooms = new ArrayList<>();
    private static int currentRoom = 10;
    private static int code = 1234;
    private static boolean securityOn = true;
    private static boolean running = true;
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static int getCurrentRoom() {
        return currentRoom;
    }

    public static void setCurrentRoom(int currentRoom) {
        Main.currentRoom = currentRoom;
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

    public static void securityToggle() {
        int tries = 3;
        while (tries > 0) {
            System.out.println("You have " + tries + " tries left to put in the code to the security system.");
            System.out.print("Enter the code: ");
            String userInput = scanner.nextLine();
            if (userInput.equals(String.valueOf(getCode()))) {
                if (isSecurityOn()) {
                    System.out.println("Correct! You Turned off the security system.");
                    setSecurityOn(false);
                } else {
                    System.out.println("Correct! You Turned on the security system.");
                    setSecurityOn(true);
                }
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

    public static void printRoomOptions(List<String> rooms, boolean isSecurityRoom, int... roomNumbers) {
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

    public static void handleUserInput(String userInput, boolean isSecurityRoom, int... roomNumbers) {
        boolean isPresent = false;
        for (int i = 1; i < roomNumbers.length + 1; i++) {
            if (i == Integer.parseInt(userInput)) {
                System.out.println(i);
                isPresent = true;
                break;
            }
        }
        if (isPresent)
            setCurrentRoom(roomNumbers[Integer.parseInt(userInput) - 1]);

        int optionCounter = roomNumbers.length + 1;
        if (isSecurityRoom) {
            if (Integer.parseInt(userInput) == optionCounter)
                securityToggle();
        } else
            optionCounter -= 1;

        if (Integer.parseInt(userInput) == optionCounter + 1) {
            int breakInRoom = random.nextInt(1, 9);
            BreakInSimulator.simulateBreakIn(breakInRoom, rooms, getCurrentRoom(), isSecurityOn(), random);
        } else if (Integer.parseInt(userInput) == optionCounter + 2) {
            int fireRoom = random.nextInt(1, 9);
            FireSimulator.simulation(fireRoom, rooms, currentRoom);
        } else if (Integer.parseInt(userInput) == optionCounter + 3)
            MovementSimulator.simulateBackyardMovement(rooms, getCurrentRoom(), isSecurityOn(), random);
        else if (Integer.parseInt(userInput) == 0)
            exit();
        else
            return;
    }

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
            System.out.println("Current room: " + rooms.get(getCurrentRoom()));
            System.out.println("What would you like to do?");
            switch (getCurrentRoom()) {
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