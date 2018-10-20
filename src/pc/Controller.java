package pc;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ProgressBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Controller {
  
  @FXML
  private Button connectControllerBtn;
  @FXML
  private Button connectBluetoothBtn;
  @FXML
  private SVGPath bluetoothStatusIcon;
  @FXML
  private Label bluetoothStatusLabel;
  @FXML
  private Label bluetoothLatencyLabel;
  @FXML
  private Label bluetoothInfoLabel;
  @FXML
  private Slider leftPowerSlider;
  @FXML
  private Slider rightPowerSlider;
  @FXML
  private ProgressBar leftPowerUpper;
  @FXML
  private ProgressBar leftPowerLower;
  @FXML
  private ProgressBar rightPowerUpper;
  @FXML
  private ProgressBar rightPowerLower;
  @FXML
  private ProgressBar distanceBar;
  @FXML
  private SVGPath controllerIcon;
  @FXML
  private SVGPath bluetoothIcon;
  @FXML
  private Circle leftThumbStick;
  @FXML
  private Circle rightThumbStick;
  @FXML
  private Circle btn1;
  @FXML
  private Circle btn2;
  @FXML
  private Circle btn3;
  @FXML
  private Circle btn4;
  @FXML
  private SVGPath btnUp;
  @FXML
  private SVGPath btnDown;
  @FXML
  private SVGPath btnLeft;
  @FXML
  private SVGPath btnRight;
  @FXML
  private Rectangle r1;
  @FXML
  private Rectangle l1;
  @FXML
  private ProgressBar r2;
  @FXML
  private ProgressBar l2;
  @FXML
  private Label autoDriveLabel;
  @FXML
  private Label pathLabel;
  
  public static final String SVG_CHECK_PATH = "m22,0c-12.2,0-22,9.8-22,22s9.8,22 22,22 22-9.8 22-22-9.8-22-22-22zm12.7,15.1l0,0-16,16.6c-0.2,0.2-0.4,0.3-0.7,0.3-0.3,0-0.6-0.1-0.7-0.3l-7.8-8.4-.2-.2c-0.2-0.2-0.3-0.5-0.3-0.7s0.1-0.5 0.3-0.7l1.4-1.4c0.4-0.4 1-0.4 1.4,0l.1,.1 5.5,5.9c0.2,0.2 0.5,0.2 0.7,0l13.4-13.9h0.1c0.4-0.4 1-0.4 1.4,0l1.4,1.4c0.4,0.3 0.4,0.9 0,1.3z";
  public static final String SVG_CLOSE_PATH = "m22,0c-12.2,0-22,9.8-22,22s9.8,22 22,22 22-9.8 22-22-9.8-22-22-22zm3.2,22.4l7.5,7.5c0.2,0.2 0.3,0.5 0.3,0.7s-0.1,0.5-0.3,0.7l-1.4,1.4c-0.2,0.2-0.5,0.3-0.7,0.3-0.3,0-0.5-0.1-0.7-0.3l-7.5-7.5c-0.2-0.2-0.5-0.2-0.7,0l-7.5,7.5c-0.2,0.2-0.5,0.3-0.7,0.3-0.3,0-0.5-0.1-0.7-0.3l-1.4-1.4c-0.2-0.2-0.3-0.5-0.3-0.7s0.1-0.5 0.3-0.7l7.5-7.5c0.2-0.2 0.2-0.5 0-0.7l-7.5-7.5c-0.2-0.2-0.3-0.5-0.3-0.7s0.1-0.5 0.3-0.7l1.4-1.4c0.2-0.2 0.5-0.3 0.7-0.3s0.5,0.1 0.7,0.3l7.5,7.5c0.2,0.2 0.5,0.2 0.7,0l7.5-7.5c0.2-0.2 0.5-0.3 0.7-0.3 0.3,0 0.5,0.1 0.7,0.3l1.4,1.4c0.2,0.2 0.3,0.5 0.3,0.7s-0.1,0.5-0.3,0.7l-7.5,7.5c-0.2,0.1-0.2,0.5 3.55271e-15,0.7z";
  public static final String SVG_LINE_PATH  = "M22,0C9.8,0,0,9.8,0,22s9.8,22,22,22s22-9.8,22-22S34.2,0,22,0z M34,23c0,0.6-0.4,1-1,1H11c-0.6,0-1-0.4-1-1v-2  c0-0.6,0.4-1,1-1h22c0.6,0,1,0.4,1,1V23z";
  public static final String SVG_BT_DISCONNECTED = "M280.5,96.9l48.45,48.45l-40.8,40.8l35.699,35.7l76.5-76.5L255,0h-25.5v127.5l51,51V96.9z M86.7,51L51,86.7L219.3,255\n" + "\t\t\tL76.5,397.8l35.7,35.7l117.3-117.3V510H255l109.65-109.65L423.3,459l35.7-35.7L86.7,51z M280.5,413.1V316.2l48.45,48.45\n" + "\t\t\tL280.5,413.1z";
  public static final String SVG_BT_CONNECTING   = "M311.1,255l58.65,58.65c7.65-17.851,10.2-38.25,10.2-58.65c0-20.4-5.101-40.8-10.2-58.65L311.1,255z M446.25,119.85L415.65,153c15.3,30.6,25.5,66.3,25.5,102s-10.2,71.4-25.5,102l30.6,30.6c25.5-38.25,38.25-86.699,38.25-135.149S471.75,158.1,446.25,119.85z M349.35,145.35L204,0h-25.5v193.8L61.2,76.5l-35.7,35.7L168.3,255L25.5,397.8l35.7,35.7l117.3-117.3V510H204l145.35-145.35L239.7,255L349.35,145.35z M229.5,96.9l48.45,48.45L229.5,193.8V96.9z M277.95,364.65L229.5,413.1V316.2L277.95,364.65z";
  public static final String SVG_BT_CONNECTED    = "M127.5,255l-51-51l-51,51l51,51L127.5,255z M400.35,145.35L255,0h-25.5v193.8L112.2,76.5l-35.7,35.7L219.3,255L76.5,397.8l35.7,35.7l117.3-117.3V510H255l145.35-145.35L290.7,255L400.35,145.35z M280.5,96.9l48.45,48.45L280.5,193.8V96.9zM328.95,364.65L280.5,413.1V316.2L328.95,364.65z M433.5,204l-51,51l51,51l51-51L433.5,204z";
  public static final String RED       = "#c63939";
  public static final String GREY      = "#d7d7d7";
  public static final String GREEN     = "#39c658";
  public static final String DARK_GREY = "#393939";
  
  private DeviceInput device;
  
  public void initialize() {
    bluetoothStatusLabel.setText("Disconnected");
    bluetoothInfoLabel.setText("no info");
    bluetoothLatencyLabel.setText("- ms");
    
    leftPowerUpper.setProgress(0.0);
    leftPowerLower.setProgress(0.0);
    rightPowerUpper.setProgress(0.0);
    rightPowerLower.setProgress(0.0);
    
    distanceBar.setProgress(0.0);
    
    controllerIcon.setContent(SVG_LINE_PATH);
    controllerIcon.setFill(Color.valueOf(GREY));
    
    bluetoothIcon.setContent(SVG_LINE_PATH);
    bluetoothIcon.setFill(Color.valueOf(GREY));
    
    bluetoothStatusIcon.setContent(SVG_BT_DISCONNECTED);
    startConnectController();
    
    autoDriveLabel.setText("Auto drive: off");
  }
  
  private void startConnectController() {
    connectControllerBtn.setDisable(true);
  
    //controllerStatusLabel.setText("Status: Connecting...");
    this.device = new DeviceInput();
    if(device.isConnected()) {
      System.out.println("Device connected!");
      controllerIcon.setContent(SVG_CHECK_PATH);
      controllerIcon.setFill(Color.valueOf(GREEN));
    } else {
      //controllerStatusLabel.setText("Status: Disconnected");
      //controllerInputLabel.setText("Raw input: -");
      System.out.println("No device connected.");
      return;
    }
    
    ConnectControllerTask task = new ConnectControllerTask(
            device, leftThumbStick, rightThumbStick,
            btn1, btn2, btn3, btn4,
            btnUp, btnDown, btnLeft, btnRight,
            l1, r1, l2, r2);
    task.runningProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> ov, Boolean wasRunning, Boolean isRunning) {
        if(isRunning) {
          connectBluetoothBtn.setDisable(false);
        }
        if(!isRunning) {
          connectControllerBtn.setDisable(false);
          System.out.println("Connect controller task not running");
        }
      }
    });
    
    final Thread thread = new Thread(task, "Controller connect");
    thread.setDaemon(true);
    thread.start();
    
  }
  @FXML
  public void handleConnectController() {
    startConnectController();
  }
  
  private void startConnectBluetooth() {
   
    connectBluetoothBtn.setDisable(true);
    //bluetoothStatusLabel.setText("Status: Connecting...");
    
    BluetoothConnectionTask task = new BluetoothConnectionTask(
            device, bluetoothStatusLabel, bluetoothInfoLabel,
            bluetoothLatencyLabel,
            distanceBar, bluetoothIcon, bluetoothStatusIcon,
            leftPowerUpper, leftPowerLower, rightPowerUpper, rightPowerLower,
            autoDriveLabel, pathLabel
    );
    task.runningProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> ov, Boolean wasRunning, Boolean isRunning) {
        if(!isRunning) {
          connectBluetoothBtn.setDisable(false);
          System.out.println("Bluetooth connection task not running");
        }
      }
    });
    
    Thread thread = new Thread(task, "Connect bluetooth");
    thread.setDaemon(true);
    thread.start();
    
  }
  @FXML
  public void connectBluetooth() {
    startConnectBluetooth();
  }
  
}
