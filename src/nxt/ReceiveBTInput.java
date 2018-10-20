package nxt;

import lejos.nxt.*;
import lejos.util.Delay;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.BTConnection;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ReceiveBTInput {
  
  public static void main(String[] args) throws IOException {
    
    String inputValues;
    int autoDrive;
    int pathRecording;
    int left;
    int right;
    
    List<int[]> path = new ArrayList<>();
    
    Robot.initialize();
    Display.drawWaiting();
    
    BTConnection btc = Bluetooth.waitForConnection();
    DataInputStream dis = btc.openDataInputStream();
    DataOutputStream dos = btc.openDataOutputStream();
    
    Display.drawConnected();
    Display.drawInfoTexts();
    
    while (!Button.ESCAPE.isDown()) {
      
      try {
        
        inputValues = dis.readUTF();
        autoDrive = Integer.parseInt(inputValues.substring(0, 1));
        pathRecording = Integer.parseInt(inputValues.substring(1, 2));
        left  = Integer.parseInt(inputValues.substring(2, inputValues.indexOf(' ')));
        right = Integer.parseInt(inputValues.substring( (String.valueOf(left).length() + 3) ));
        
        LCD.drawString("AI: "   + autoDrive,     0, 5);
        LCD.drawString("Path: " + pathRecording, 0, 6);
        
        if (pathRecording == 1) {
          if (left != 0 || right != 0) {
            path.add(0, new int[]{left, right});
          }
        }
        
        if (autoDrive == 1) {
          AutoDrive.drive();
        } else if (pathRecording == 0 && !path.isEmpty()) {
          for (int[] motorValues : path) {
            Robot.setMotorPowers(-motorValues[0], -motorValues[1]);
            Delay.msDelay(200);
          }
          path.clear();
        } else {
          Robot.setMotorPowers(left, right);
          Display.drawValues(left, right);
        }
        
        
        
        Delay.msDelay(100); // wait for data to drain
        dos.writeUTF(Robot.leftMotor.getPower() + " " + Robot.rightMotor.getPower() + " " + Robot.us.getDistance());
        dos.flush();
        
      } catch (IOException e) {
        
        dis.close();
        dos.close();
        btc.close();
        break;
        
      }
    }
    Display.drawClosing();
  }
  
}