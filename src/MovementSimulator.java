import java.util.List;

public class MovementSimulator {
    public void simulateBackyardMovement(List<Room> rooms, int userRoom, boolean securityOn) {
        BreakInSimulator breakInSimulator = new BreakInSimulator();
        System.out.println("\n---Simulating movement in the backyard---");
        if (securityOn && userRoom == 9) {
            System.out.println("The security alarm rings, and you see the movement.");
        } else if (securityOn) {
            System.out.println("Whatever caused the movement is got scared off by the security alarm.");
        } else if (userRoom == 9) {
            System.out.println("You spot the movement yourself.");
        } else {
            System.out.println("The security is off, and burglars break into the house from the backyard.");
            breakInSimulator.simulateBreakIn(rooms, 1, securityOn, userRoom);
        }
    }
}
