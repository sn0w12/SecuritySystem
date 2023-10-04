import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class BreakInSimulator {
    public void simulateBreakIn(List<Room> rooms, int breakInRoom, boolean securityOn, int userRoom) {
        System.out.println("\n---Simulating a break-in, current room: " + rooms.get(userRoom).getName() + "---");
        System.out.println("The robbers broke into: " + rooms.get(breakInRoom).getName());
        if (securityOn && userRoom == breakInRoom) {
            rooms.get(breakInRoom).activateDetectors(DetectorGroup.ROBBERY);
            System.out.println("The robbers also see you and run away.");
        } else if (securityOn) {
            rooms.get(breakInRoom).activateDetectors(DetectorGroup.ROBBERY);
            System.out.println("The robbers got scared by the alarm and ran away.");
        } else if (userRoom == breakInRoom) {
            System.out.println("You are in the room so the robbers get scared and run away.");
        } else {
            nextRoom(rooms, breakInRoom, userRoom);
        }
    }
    public int generateNextRoom(int min, int max, int breakInRoom, List<Integer> visitedRooms, Random random) {
        int nextRoom = random.nextInt(min, (max + 1));
        boolean containsAll = IntStream.rangeClosed(min, max).allMatch(visitedRooms::contains);
        // Keep generating a new room until an unvisited one is found, if every room is visited move to either living room or entrance
        if (!containsAll) {
            while (visitedRooms.contains(nextRoom))
                nextRoom = random.nextInt(min, max + 1);
        } else if (breakInRoom == 0)
            nextRoom = 1;
        else
            nextRoom = 0;

        return nextRoom;
    }
    public void nextRoomOutput(int breakInRoom, List<Room> rooms, int userRoom, List<Integer> visitedRooms) {
        if (userRoom == breakInRoom)
            System.out.println("The robbers move to " + rooms.get(breakInRoom).getName() + ", were seen by you and ran away.");
        else if (!visitedRooms.contains(breakInRoom)) {
            System.out.println("The robbers move to " + rooms.get(breakInRoom).getName() + " and steal everything in the room.");
            visitedRooms.add(breakInRoom);
        } else
            System.out.println("The robbers move back to " + rooms.get(breakInRoom).getName() + ".");
    }
    private void nextRoom(List<Room> rooms, int breakInRoom, int userRoom) {
        Random random = new Random();
        List<Integer> visitedRooms = new ArrayList<>();
        visitedRooms.add(breakInRoom);
        int chance = 10;
        boolean seen = false;
        boolean containsAll = IntStream.rangeClosed(0, 8).allMatch(visitedRooms::contains);

        // Decide the robbers next room depending on what room they are in
        while (chance > 0 && !seen) {
            chance = random.nextInt(10);
            switch (breakInRoom) {
                case 0 -> {
                    breakInRoom = generateNextRoom(2, 5, breakInRoom, visitedRooms, random);
                    nextRoomOutput(breakInRoom, rooms, userRoom, visitedRooms);
                }
                case 1 -> {
                    breakInRoom = generateNextRoom(6, 8, breakInRoom, visitedRooms, random);
                    nextRoomOutput(breakInRoom, rooms, userRoom, visitedRooms);
                }
                case 2, 3, 4, 5 -> {
                    breakInRoom = 0;
                    nextRoomOutput(breakInRoom, rooms, userRoom, visitedRooms);
                }
                case 6, 7, 8 -> {
                    breakInRoom = 1;
                    nextRoomOutput(breakInRoom, rooms, userRoom, visitedRooms);
                }
            }
            if (containsAll)
                chance = 0;
            if (userRoom == breakInRoom)
                seen = true;
        }
        if (containsAll)
            System.out.println("The robbers have stolen everything and left.");
        else if (!seen)
            System.out.println("The robbers have stolen enough and left.");
    }
}