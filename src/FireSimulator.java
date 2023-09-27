import java.util.List;

public class FireSimulator {
    public static void simulation(int fireRoom, List<String> rooms, int currentRoom) {
        System.out.println("\n---Simulating a fire---");
        if (fireRoom == currentRoom)
            System.out.println("A fire starts in " + rooms.get(fireRoom) + ", you notice it and put out the fire with a fire extinguisher.");
        else
            System.out.println("A fire starts in " + rooms.get(fireRoom) + ", the sprinkler system is activated in the room and extinguishes the fire.");
        Main.pause();
    }
}