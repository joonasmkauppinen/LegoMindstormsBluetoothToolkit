package pc;

import lejos.pc.comm.NXTInfo;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import lejos.pc.comm.NXTConnector;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;
import javafx.scene.control.Slider;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BluetoothConnectionTask extends Task<Void> {
  
  private final Label statusLabel;
  private final Label latencyLabel;
  private final Label infoLabel;
  private final ProgressBar leftPowerUpper;
  private final ProgressBar leftPowerLower;
  private final ProgressBar rightPowerUpper;
  private final ProgressBar rightPowerLower;
  private final ProgressBar distance;
  private final SVGPath icon;
  private final SVGPath statusIcon;
  private final Label autoDriveLabel;
  private final Label pathRecordingLabel;
  
  private boolean autoDriveBtnDown;
  private String autoDrive;
  private boolean pathRecordingBtnDown;
  private String pathRecording;
  private NXTConnector conn;
  private DeviceInput device;
  private boolean lastYBtnState;
  private boolean lastABtnState;
  private long timeAtDos;
  private long timeAtDis;
  
  BluetoothConnectionTask(DeviceInput device,
                          Label status, Label info, Label latency,
                          ProgressBar distance, SVGPath icon, SVGPath statusIcon,
                          ProgressBar leftPowerUpper, ProgressBar leftPowerLower,
                          ProgressBar rightPowerUpper, ProgressBar rightPowerLower,
                          Label autoDriveLabel, Label pathRecordingLabel
  ) {
    this.device = device;
    this.statusLabel = status;
    this.infoLabel = info;
    this.latencyLabel = latency;
    this.distance = distance;
    this.icon = icon;
    this.statusIcon = statusIcon;
    this.leftPowerUpper = leftPowerUpper;
    this.leftPowerLower = leftPowerLower;
    this.rightPowerUpper = rightPowerUpper;
    this.rightPowerLower = rightPowerLower;
    
    this.autoDrive = "0";
    this.autoDriveBtnDown = false;
    this.autoDriveLabel = autoDriveLabel;
    this.pathRecording = "0";
    this.pathRecordingBtnDown = false;
    this.pathRecordingLabel = pathRecordingLabel;
    this.lastYBtnState = false;
    this.lastABtnState = false;
    
    this.conn = new NXTConnector();
  }
  
  @Override
  protected Void call() throws Exception {
    updateLabelLater(statusLabel, "Connecting...");
    updateIconLater(statusIcon, Controller.SVG_BT_CONNECTING, Controller.DARK_GREY);
    
    // Connect to any nxt over bluetooth
    if (conn.connectTo("btspp://")) {
      
      updateIconLater(icon, Controller.SVG_CHECK_PATH, Controller.GREEN);
      updateIconLater(statusIcon, Controller.SVG_BT_CONNECTED, Controller.DARK_GREY);
      updateLabelLater(statusLabel, "Connected!");
      NXTInfo info = conn.getNXTInfo();
      updateLabelLater(infoLabel, info.name + " " + info.deviceAddress);
      
      DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
      DataInputStream dis  = new DataInputStream(conn.getInputStream());
      
      while (true) {
        
        int[] motorValues = device.inputToMotorValues(
                roundNumTo(device.getDeviceAxes().lx, 2),
                roundNumTo(device.getDeviceAxes().ly, 2)
        );
        
        
        if (device.getButton().y != lastYBtnState) {
          
          lastYBtnState = device.getButton().y;
          
          if (device.getButton().y && autoDrive.equals("0")) {
            
            System.out.println("Auto drive ON");
            autoDrive = "1";
            updateLabelLater(autoDriveLabel, "Auto drive: on");
            
          } else if (device.getButton().y && autoDrive.equals("1")) {
            
            System.out.println("Auto drive OFF");
            autoDrive = "0";
            updateLabelLater(autoDriveLabel, "Auto drive: off");
            
          }
          
        }
  
        if (device.getButton().a != lastABtnState) {
    
          lastABtnState = device.getButton().a;
    
          if (device.getButton().a && pathRecording.equals("0")) {
      
            System.out.println("Path recording ON");
            pathRecording = "1";
            updateLabelLater(pathRecordingLabel, "Path: rec...");
      
          } else if (device.getButton().a && pathRecording.equals("1")) {
      
            System.out.println("Path recording OFF");
            pathRecording = "0";
            updateLabelLater(pathRecordingLabel, "Path: off");
      
          }
    
        }
        
        String thumbStickInput = autoDrive + pathRecording + motorValues[0] + " " + motorValues[1];
        
        try {
          
          timeAtDos = System.currentTimeMillis();
          
          dos.writeUTF(thumbStickInput);
          dos.flush();
          
        } catch (IOException ioe) {
          
          dis.close();
          dos.close();
          System.out.println("Data output stream closed.");
          break;
          
        }
        
        try {
          
          String[] robotValues = dis.readUTF().split(" ");
          
          timeAtDis = System.currentTimeMillis();
          long latency = timeAtDis - timeAtDos;
          
          updateLabelLater(latencyLabel, latency + "ms");
          
          float leftPower  = Float.parseFloat(robotValues[0]) / 100;
          float rightPower = Float.parseFloat(robotValues[1]) / 100;
          
          if (leftPower >= 0) {
            updateProgressBarLater(leftPowerUpper, leftPower);
          } else {
            updateProgressBarLater(leftPowerUpper, 0.0f);
          }
          if(leftPower <= 0) {
            updateProgressBarLater(leftPowerLower, -leftPower);
          } else {
            updateProgressBarLater(leftPowerLower, 0.0f);
          }
          if (rightPower >= 0) {
            updateProgressBarLater(rightPowerUpper, rightPower);
          } else {
            updateProgressBarLater(rightPowerUpper, 0.0f);
          }
          if (rightPower <= 0) {
            updateProgressBarLater(rightPowerLower, -rightPower);
          } else {
            updateProgressBarLater(rightPowerLower, 0.0f);
          }
          
          updateProgressBarLater(distance, (Float.parseFloat(robotValues[2]) / 255));
          
        } catch (IOException ioe) {
          
          dis.close();
          dos.close();
          System.out.println("Data input stream closed.");
          break;
          
        }
      }
    }
    
    updateIconLater(icon, Controller.SVG_CLOSE_PATH, Controller.RED);
    updateIconLater(statusIcon, Controller.SVG_BT_DISCONNECTED, Controller.DARK_GREY);
    updateLabelLater(statusLabel, "Disconnected");
    updateLabelLater(infoLabel, "no info");
    updateLabelLater(latencyLabel, "- ms");
    
    return null;
    
  }
  
  
  private void updateLabelLater(Label label, final String text) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        label.setGraphic(null);
        label.setText(text);
      }
    });
  }
  
  private void updateSliderLater(Slider slider, final float value) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        slider.setValue(value);
      }
    });
  }

  private void updateProgressBarLater(ProgressBar bar, final float value) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        bar.setProgress(value);
      }
    });
  }
  
  private void updateIconLater(SVGPath icon, String path, String color) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        icon.setContent(path);
        icon.setFill(Color.valueOf(color));
      }
    });
  }
  
  private float roundNumTo(float number, int scale) {
    int pow = 10;
    for (int i = 1; i < scale; i++)
      pow *= 10;
    float tmp = number * pow;
    return ((float) ((int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp))) / pow;
  }
}
