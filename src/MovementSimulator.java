import java.util.List;
import java.util.Random;

public class MovementSimulator {
    public static void simulateBackyardMovement(List<String> rooms, int currentRoom, boolean securityOn, Random random) {
        System.out.println("\n---Simulating movement in the backyard---");
        if (securityOn && currentRoom == 9) {
            System.out.println("The security alarm rings, and you see the movement.");
        } else if (securityOn) {
            System.out.println("Whatever caused the movement is got scared off by the security alarm.");
        } else if (currentRoom == 9) {
            System.out.println("You spot the movement yourself.");
        } else {
            System.out.println("The security is off, and burglars break into the house from the backyard.");
            BreakInSimulator.simulateBreakIn(1, rooms, currentRoom, securityOn, random);
        }
        Main.pause();
    }
}