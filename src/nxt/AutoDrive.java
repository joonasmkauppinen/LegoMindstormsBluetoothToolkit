package nxt;

public class AutoDrive {
  
  private static boolean isTurning = false;
  
  public static void drive() {
    if (Robot.us.getDistance() < 35) {
      while (Robot.us.getDistance() < 40) {
        if (!isTurning) {
          Robot.turn();
          isTurning = true;
        }
      }
    } else {
        Robot.forward();
        if (isTurning) {
          isTurning = false;
        }
    }
  }
  
}
