import java.util.*;

class Room {
    private final String name;
    private final List<Detector> detectors;

    public Room(String name) {
        this.name = name;
        this.detectors = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addDetector(Detector detector) {
        detectors.add(detector);
    }

    public void addDefaultDetectors() {
        addDetector(new SmokeDetector(DetectorGroup.FIRE));
        addDetector(new WindowDetector(DetectorGroup.ROBBERY));
        addDetector(new DoorDetector(DetectorGroup.ROBBERY));
    }

    // Activates detectors based on the specified event type
    public void activateDetectors(DetectorGroup eventType) {
        for (Detector detector : detectors) {
            if (detector.detect() && (detector.getGroup() == eventType)) {
                System.out.println("Alarm activated in " + name + " due to " + detector.getType());
                break;
            }
        }
    }
}

abstract class Detector {
    private final String type;
    private final DetectorGroup group;

    public Detector(String type, DetectorGroup group) {
        this.type = type;
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public DetectorGroup getGroup() {
        return group;
    }

    public boolean detect() {
        return true;
    }
}

class WindowDetector extends Detector {
    public WindowDetector(DetectorGroup group) {
        super("Window Detector", group);
    }
}

class DoorDetector extends Detector {
    public DoorDetector(DetectorGroup group) {
        super("Door Detector", group);
    }
}

class MotionDetector extends Detector {
    public MotionDetector(DetectorGroup group) {
        super("Motion Detector", group);
    }
}

class SmokeDetector extends Detector {
    public SmokeDetector(DetectorGroup group) {
        super("Smoke Detector", group);
    }
}

enum DetectorGroup {
    ROBBERY, FIRE
}

public class Main {
    private final Scanner scanner;
    private final int code;
    private boolean securityOn;
    private int userRoom;
    private final Random random;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.code = 1234;
        this.securityOn = true;
        this.userRoom = 10;
        this.random = new Random();
    }

    public int getCode() {
        return code;
    }

    public boolean isSecurityOn() {
        return securityOn;
    }

    public void setSecurityOn(boolean securityOn) {
        this.securityOn = securityOn;
    }

    public int getUserRoom() {
        return userRoom;
    }

    public void setUserRoom(int userRoom) {
        this.userRoom = userRoom;
    }

    public void pause() {
        System.out.print("\nPress enter to continue...");
        scanner.nextLine();
    }

    public void exit() {
        System.out.println("Do you want to exit? y/n");
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("y"))
            System.exit(0);
    }

    public void securityToggle() {
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

    public void printRoomOptions(List<Room> rooms, boolean isSecurityRoom, int... roomNumbers) {
        for (int i = 0; i < roomNumbers.length; i++) {
            int roomNumber = roomNumbers[i];
            System.out.println((i + 1) + ". Move to " + rooms.get(roomNumber).getName());
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

    public void handleUserInput(List<Room> rooms, String userInput, boolean isSecurityRoom, int... roomNumbers) {
        boolean isPresent = false;
        BreakInSimulator breakInSimulator = new BreakInSimulator();
        FireSimulator fireSimulator = new FireSimulator();
        MovementSimulator movementSimulator = new MovementSimulator();

        // Checking if the selected option corresponds to moving to a room.
        for (int i = 1; i < roomNumbers.length + 1; i++) {
            if (i == Integer.parseInt(userInput)) {
                isPresent = true;
                break;
            }
        }
        // If the selected option is valid, set the user's room to the chosen room.
        if (isPresent)
            setUserRoom(roomNumbers[Integer.parseInt(userInput) - 1]);

        int optionCounter = roomNumbers.length + 1;
        if (isSecurityRoom) {
            if (Integer.parseInt(userInput) == optionCounter)
                securityToggle();
        } else
            optionCounter -= 1;

        // Check and perform actions for simulation options.
        if (Integer.parseInt(userInput) == optionCounter + 1) {
            int breakInRoom = random.nextInt(0, 9);
            breakInSimulator.simulateBreakIn(rooms, breakInRoom, isSecurityOn(), getUserRoom());
            pause();
        } else if (Integer.parseInt(userInput) == optionCounter + 2) {
            int fireRoom = random.nextInt(0, 9);
            fireSimulator.simulation(fireRoom, rooms, getUserRoom());
            pause();
        } else if (Integer.parseInt(userInput) == optionCounter + 3) {
            movementSimulator.simulateBackyardMovement(rooms, getUserRoom(), isSecurityOn());
            pause();
        } else if (Integer.parseInt(userInput) == 0)
            exit();
    }

    public void userInterface(List<Room> rooms, boolean isSecurityRoom, int... roomNumbers) {
        printRoomOptions(rooms, isSecurityRoom, roomNumbers);
        String userInput = scanner.nextLine();
        handleUserInput(rooms, userInput, isSecurityRoom, roomNumbers);
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Entrance"));
        rooms.add(new Room("Living Room"));
        rooms.add(new Room("Garage"));
        rooms.add(new Room("Bedroom 1"));
        rooms.add(new Room("Bedroom 2"));
        rooms.add(new Room("Bedroom 5"));
        rooms.add(new Room("Bedroom 3"));
        rooms.add(new Room("Bedroom 4"));
        rooms.add(new Room("Kitchen"));
        rooms.add(new Room("Backyard"));
        rooms.add(new Room("Porch"));

        rooms.get(0).addDefaultDetectors();
        rooms.get(0).addDetector(new MotionDetector(DetectorGroup.ROBBERY));
        rooms.get(1).addDefaultDetectors();
        rooms.get(1).addDetector(new MotionDetector(DetectorGroup.ROBBERY));
        rooms.get(2).addDefaultDetectors();
        rooms.get(3).addDefaultDetectors();
        rooms.get(4).addDefaultDetectors();
        rooms.get(5).addDefaultDetectors();
        rooms.get(6).addDefaultDetectors();
        rooms.get(7).addDefaultDetectors();
        rooms.get(8).addDetector(new WindowDetector(DetectorGroup.ROBBERY));
        rooms.get(9).addDetector(new MotionDetector(DetectorGroup.ROBBERY));

        while (true) {
            System.out.println("\n---Security System:" + (main.isSecurityOn() ? "ON" : "OFF") + "---");
            System.out.println("Current room: " + rooms.get(main.getUserRoom()).getName());
            System.out.println("What would you like to do?");
            switch (main.getUserRoom()) {
                case 0 -> main.userInterface(rooms, true, 10, 1, 2, 3, 4, 5);
                case 1 -> main.userInterface(rooms, true, 0, 6, 7, 8, 9);
                case 2 -> main.userInterface(rooms, true, 0, 10);
                case 3, 4, 5 -> main.userInterface(rooms, false, 0);
                case 6, 7, 8, 9 -> main.userInterface(rooms, false, 1);
                case 10 -> main.userInterface(rooms, false, 0, 2);
                default -> {
                }
            }
        }
    }
}