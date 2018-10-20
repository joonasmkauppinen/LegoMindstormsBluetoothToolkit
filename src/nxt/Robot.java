package nxt;

import lejos.nxt.*;

import java.util.Random;

public class Robot {
  
  private static final int SPEED = 100;
  private static final int TURN_SPEED = 100;
  
  public static NXTMotor leftMotor = new NXTMotor(MotorPort.C);
  public static NXTMotor rightMotor = new NXTMotor(MotorPort.A);
  public static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
  
  public static void initialize() {
    leftMotor.setPower(0);
    leftMotor.backward();
    rightMotor.setPower(0);
    rightMotor.backward();
  }
  
  public static void forward() {
    leftMotor.setPower(SPEED);
    rightMotor.setPower(SPEED);
  }
  
  public static void backward() {
    leftMotor.setPower(-SPEED);
    rightMotor.setPower(-SPEED);
  }
  
  public static void turn() {
    if (turnDirection() == 0) {
      leftMotor.setPower(-TURN_SPEED);
      rightMotor.setPower(TURN_SPEED);
    } else {
      leftMotor.setPower(TURN_SPEED);
      rightMotor.setPower(-TURN_SPEED);
    }
  }
  
  public static void setMotorPowers(int leftPower, int rightPower) {
    leftMotor.setPower(leftPower);
    rightMotor.setPower(rightPower);
  }
  
  private static int turnDirection() {
    return new Random().nextInt(2);
  }
  
}
