package nxt;

import lejos.nxt.LCD;
import lejos.util.Delay;

public class Display {
  
  public static void drawWaiting() {
    LCD.drawString("Waiting...", 2, 2);
  }
  
  public static void drawConnected() {
    LCD.clear(2);
    LCD.drawString("Connected!", 2, 2);
    Delay.msDelay(500);
    LCD.clear(2);
  }
  
  public static void drawInfoTexts() {
    LCD.drawString("DRIVE", 5, 1);
    LCD.drawString("Left: ", 2, 3);
    LCD.drawString("Right: ", 2, 4);
  }
  
  public static void drawValues(int left, int right) {
    LCD.refresh();
    LCD.drawString(formatForLCD(String.valueOf(left)),  8, 3);
    LCD.drawString(formatForLCD(String.valueOf(right)), 9, 4);
  }
  
  public static void drawClosing() {
    LCD.refresh();
    LCD.clear();
    LCD.drawString("Closing...", 2, 2);
  }
  
  private static String formatForLCD(String str) {
    StringBuilder sb = new StringBuilder(str);
    while (sb.length() < 4) {
      sb.append(" ");
    }
    return sb.toString();
  }
  
}
