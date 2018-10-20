package pc;

import com.ivan.xinput.*;

public class DeviceInput {
  
  private XInputDevice14 device;
  
  DeviceInput() {
  
    try {
      if(XInputDevice14.isAvailable()) {
        System.out.println("XInput 1.4 is available on this platform.");
        try {
          this.device = XInputDevice14.getDeviceFor(0);
        } catch(Exception e) {
          System.out.println("Controller not found.");
        }
      } else {
        System.out.println("XInput 1.4 is not available on this platform.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  private XInputComponents getDeviceComponents() {
    return device.getComponents();
  }
  
  public XInputAxes getDeviceAxes() {
    return getDeviceComponents().getAxes();
  }
  
  public XInputButtons getButton() {
    return getDeviceComponents().getButtons();
  }
  
  public XInputButtonsDelta getButtonDeltas() {
    XInputComponentsDelta delta = device.getDelta();
    return delta.getButtons();
  }
  
  public void pollDevice() {
    device.poll();
  }
  
  public boolean isConnected() {
    return device.isConnected();
  }
  
  public int[] inputToMotorValues(float x, float y) {
    float deadZone = 0.2f;
    int leftMotor = 0;
    int rightMotor = 0;
  
    if (x > -deadZone && x < deadZone && y > -deadZone && y < deadZone) {
      leftMotor = 0;
      rightMotor = 0;
    } else {
    
      // forward and backward
      if (x > -deadZone && x < deadZone) {
        leftMotor = (int) (y * 100);
        rightMotor = (int) (y * 100);
        if (leftMotor == -99 && rightMotor == -99) {
          leftMotor = -100;
          rightMotor = -100;
        }
      }
    
      // top left
      if (x < -deadZone && y > deadZone) {
        leftMotor = (int) ((y - Math.abs(x)) * 100);
        rightMotor = (int) ((y + Math.abs(x)) * 100);
        if (rightMotor > 100)
          rightMotor = 100;
      }
    
      // top right
      if (x > deadZone && y > deadZone) {
        leftMotor = (int) ((y + x) * 100);
        rightMotor = (int) ((y - x) * 100);
        if (leftMotor > 100)
          leftMotor = 100;
      }
    
      // bottom right
      if (x > deadZone && y < -deadZone) {
        leftMotor = (int) ((x + y) * 100);
        rightMotor = (int) ((x - y) * -100);
        if (rightMotor < -100)
          rightMotor = -100;
      }
    
      // bottom left
      if (x < -deadZone && y < -deadZone) {
        leftMotor = (int) ((y + x) * 100);
        rightMotor = (int) ((Math.abs(x) + y) * 100);
        if (leftMotor < -100)
          leftMotor = -100;
      }
    
      // spin left and right
      if (y > -deadZone && y < deadZone) {
        if (x < 0) {
          leftMotor = (int) (x * 100);
          rightMotor = (int) (x * -100);
        } else if (x > 0) {
          leftMotor = (int) (x * 100);
          rightMotor = (int) (x * -100);
        }
      }
    }
    
    return new int[] {leftMotor, rightMotor};
    
  }
  
}
