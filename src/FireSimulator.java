import java.util.List;

public class FireSimulator {
    public void simulation(int fireRoom, List<Room> rooms, int userRoom) {
        System.out.println("\n---Simulating a fire---");
        if (fireRoom == userRoom)
            System.out.println("A fire starts in " + rooms.get(fireRoom).getName() + ", you notice it and put out the fire with a fire extinguisher.");
        else {
            System.out.print("A fire starts in " + rooms.get(fireRoom).getName() + ", the ");
            rooms.get(fireRoom).activateDetectors(DetectorGroup.FIRE);
            System.out.println("The sprinklers are activated and put out the fire.");
        }
    }
}