package pc;

import com.ivan.xinput.*;
import com.ivan.xinput.enums.XInputButton;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class ConnectControllerTask extends Task<Void> {
  
  private final Circle leftThumbStick;
  private final Circle rightThumbStick;
  private final Circle btn1;
  private final Circle btn2;
  private final Circle btn3;
  private final Circle btn4;
  private final SVGPath btnUp;
  private final SVGPath btnDown;
  private final SVGPath btnLeft;
  private final SVGPath btnRight;
  private final Rectangle r1;
  private final Rectangle l1;
  private final ProgressBar r2;
  private final ProgressBar l2;
  private DeviceInput device;
  
  
  ConnectControllerTask(DeviceInput device, Circle leftThumbStick, Circle rightThumbStick,
                        Circle btn1, Circle btn2, Circle btn3, Circle btn4,
                        SVGPath btnUp, SVGPath btnDown, SVGPath btnLeft, SVGPath btnRight,
                        Rectangle l1, Rectangle r1, ProgressBar l2, ProgressBar r2) {
    
    this.device = device;
    this.leftThumbStick = leftThumbStick;
    this.rightThumbStick = rightThumbStick;
    this.btn1 = btn1;
    this.btn2 = btn2;
    this.btn3 = btn3;
    this.btn4 = btn4;
    this.btnUp = btnUp;
    this.btnDown = btnDown;
    this.btnLeft = btnLeft;
    this.btnRight = btnRight;
    this.l1 = l1;
    this.r1 = r1;
    this.l2 = l2;
    this.r2 = r2;
  }
  
  @Override
  protected Void call() throws Exception {
    //Thread.sleep(500);
    //updateLabelLater(statusLabel, "Status: Connected!");
    
    //DeviceInput device = new DeviceInput();
    
    while (device.isConnected()) {
      device.pollDevice();
      //updateLabelLater(inputLabel, "Raw input: LT:" + device.getDeviceAxes().lt + "  RT:" + device.getDeviceAxes().rt);
      updateThumbStickLater(leftThumbStick, (double) device.getDeviceAxes().lx, (double) device.getDeviceAxes().ly);
      updateThumbStickLater(rightThumbStick, (double) device.getDeviceAxes().rx, (double) device.getDeviceAxes().ry);
      updateTriggerLater(l2, (double) device.getDeviceAxes().lt);
      updateTriggerLater(r2, (double) device.getDeviceAxes().rt);
      
      XInputButtonsDelta buttons = device.getButtonDeltas();
      
      if (buttons.isPressed(XInputButton.A)) {
        updateBtnLater(btn1, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.A)) {
        updateBtnLater(btn1, Controller.GREY);
      }
  
      if (buttons.isPressed(XInputButton.B)) {
        updateBtnLater(btn2, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.B)){
        updateBtnLater(btn2, Controller.GREY);
      }
  
      if (buttons.isPressed(XInputButton.X)) {
        updateBtnLater(btn3, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.X)){
        updateBtnLater(btn3, Controller.GREY);
      }
      
      if (buttons.isPressed(XInputButton.Y)) {
        updateBtnLater(btn4, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.Y)){
        updateBtnLater(btn4, Controller.GREY);
      }
      
      if (buttons.isPressed(XInputButton.DPAD_UP)) {
        updateBtnLater(btnUp, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.DPAD_UP)){
        updateBtnLater(btnUp, Controller.GREY);
      }
  
      if (buttons.isPressed(XInputButton.DPAD_DOWN)) {
        updateBtnLater(btnDown, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.DPAD_DOWN)){
        updateBtnLater(btnDown, Controller.GREY);
      }
  
      if (buttons.isPressed(XInputButton.DPAD_LEFT)) {
        updateBtnLater(btnLeft, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.DPAD_LEFT)){
        updateBtnLater(btnLeft, Controller.GREY);
      }
  
      if (buttons.isPressed(XInputButton.DPAD_RIGHT)) {
        updateBtnLater(btnRight, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.DPAD_RIGHT)){
        updateBtnLater(btnRight, Controller.GREY);
      }
      
      if(buttons.isPressed(XInputButton.RIGHT_SHOULDER)) {
        updateBtnLater(r1, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.RIGHT_SHOULDER)){
        updateBtnLater(r1, Controller.GREY);
      }
  
      if(buttons.isPressed(XInputButton.LEFT_SHOULDER)) {
        updateBtnLater(l1, Controller.DARK_GREY);
      } else if (buttons.isReleased(XInputButton.LEFT_SHOULDER)){
        updateBtnLater(l1, Controller.GREY);
      }
      
      Thread.sleep(10);
    }
    
    //updateLabelLater(statusLabel, "Status: Disconnected");
    return null;
  }
  
  private void updateThumbStickLater(Circle thumbStick, Double x, Double y) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        thumbStick.setCenterX(x * 20);
        thumbStick.setCenterY(y * -20);
      }
    });
  }
  
  private void updateBtnLater(Shape btn, String color) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        btn.setFill(Color.valueOf(color));
      }
    });
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
  
  private void updateTriggerLater(ProgressBar trigger, double value) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        trigger.setProgress(value);
      }
    });
  }
  
}
