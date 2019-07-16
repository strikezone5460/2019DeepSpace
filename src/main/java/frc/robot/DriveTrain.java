/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SPI.Port;
  
/**
 * Add your docs here.
 */
public class DriveTrain {
  public Solenoid shiftLo = new Solenoid(0);
  public Solenoid shiftHi = new Solenoid(1);

  public final TalonSRX leftDriveM = new TalonSRX(1);
  public final VictorSPX leftDriveS1 = new VictorSPX(2); 
  public final VictorSPX leftDriveS2 = new VictorSPX(3);
  public final TalonSRX rightDriveM = new TalonSRX(4);
  public final VictorSPX rightDriveS1 = new VictorSPX(5); 
  public final VictorSPX rightDriveS2 = new VictorSPX(6);

  public AnalogInput DistanceSensor = new AnalogInput(0);



  NetworkTable home_table = NetworkTableInstance.getDefault().getTable("limelight-home");
  NetworkTable score_table = NetworkTableInstance.getDefault().getTable("limelight-score");


  public ADXRS450_Gyro theGyro = new ADXRS450_Gyro(Port.kOnboardCS0);
/**********************************************************************************
 ************          GLOBALS          *******************************************
 **********************************************************************************/

  public int leftEnc(){return leftDriveM.getSelectedSensorPosition();}
  public int rightEnc(){return rightDriveM.getSelectedSensorPosition();}
  public int leftVel(){return leftDriveM.getSelectedSensorVelocity();}
  public int rightVel(){return leftDriveM.getSelectedSensorVelocity();}

  public int ShiftToggle = 0;

  double correction = 0.0;
  double leftMCurr(){return leftDriveM.getOutputCurrent();}

  double rightMCurr(){return rightDriveM.getOutputCurrent();}
  public boolean shiftState = false;


  public double leftMtemp(){return leftDriveM.getTemperature();}
  public double left1temp(){return leftDriveS1.getTemperature();}
  public double left2temp(){return leftDriveS2.getTemperature();}
  public double rightMtemp(){return rightDriveM.getTemperature();}
  public double right1temp(){return rightDriveS1.getTemperature();}
  public double right2temp(){return rightDriveS2.getTemperature();}

  public double gyroValue(){return theGyro.getAngle();}
  public double gyroRate(){return theGyro.getRate();}
  public double DistSense(){return DistanceSensor.getVoltage();}

  public double llHomeTarget(){
    return NetworkTableInstance.getDefault().getTable("limelight-home").getEntry("tv").getDouble(0);
    //Returns if the limelight-home has a target
  }
  public double llHomeXOffset(){
    return NetworkTableInstance.getDefault().getTable("limelight-home").getEntry("tx").getDouble(0.0);
    //Returns the X axis offset
  }
  public double llHomeShort(){
    return NetworkTableInstance.getDefault().getTable("limelight-home").getEntry("tshort").getDouble(0.0);
    //Returns length of the shortest side of the target
  }
  public void llHomeLed(int ledMode){
    NetworkTableInstance.getDefault().getTable("limelight-home").getEntry("ledMode").setNumber(ledMode);
    //0: Pipeline Set, 1: Off, 2: Blink, 3: On
  }
  public void llHomeCamera(int cameraMode){
    NetworkTableInstance.getDefault().getTable("limelight-home").getEntry("camMode").setNumber(cameraMode);
  }
  //Shoot Side
  public double llShootTarget(){
    return NetworkTableInstance.getDefault().getTable("limelight-score ").getEntry("tv").getDouble(0);
 
    // return NetworkTableInstance.getDefault().getTable("limelight-score ").getEntry("tv").getDouble(0);
    //Returns if the limelight-score  has a target
  }
  public double llShootXOffset(){
    return NetworkTableInstance.getDefault().getTable("limelight-score").getEntry("tx").getDouble(0.0);
    //Returns the X axis offset
  }
  public double llShootShort(){
    return NetworkTableInstance.getDefault().getTable("limelight-score").getEntry("tshort").getDouble(0.0);
    //Returns length of the shortest side of the target
  }
  public void llShootLed(int ledMode){
    NetworkTableInstance.getDefault().getTable("limelight-score").getEntry("ledMode").setNumber(ledMode);

    // NetworkTableInstance.getDefault().getTable("limelight-score ").getEntry("ledMode").setNumber(ledMode);
    //0: Pipeline Set, 1: Off, 2: Blink, 3: On
  }
  public void llShootCamera(int cameraMode){
    NetworkTableInstance.getDefault().getTable("limelight-score").getEntry("camMode").setNumber(cameraMode);

    // NetworkTableInstance.getDefault().getTable("limelight-score ").getEntry("camMode").setNumber(cameraMode);
  }
  public void llShootPiP(int PiPMode){
    NetworkTableInstance.getDefault().getTable("limelight-score ").getEntry("stream").setNumber(PiPMode);
  }
  public void gyroReset(){theGyro.reset();}

  public void shiftHi(){
  shiftLo.set(false);
  shiftHi.set(true);
  }
  public void shiftLo(){
    shiftHi.set(false);  
    shiftLo.set(true);
  }
  public void ShiftToggle(boolean toggle){
  if (toggle){
      ShiftToggle += 1;
  }
  if(ShiftToggle == 1){
      shiftHi();
      shiftState = true;
  }else if (ShiftToggle == 2){
      ShiftToggle = 0;
      shiftLo();
      shiftState = false;
  }
}
public void ResetEnc(){
  leftDriveM.setSelectedSensorPosition(0,0,10);
  rightDriveM.setSelectedSensorPosition(0,0,10);
}

/**********************************************************************************
 ************          Utilities          *****************************************
 **********************************************************************************/

/**
 * No control Loop. Percentage of available battery power. Simply takes SpeedL and applies to Left Motors, Inverts SpeedR to Right Motors.
 */ 
  public void drivePercentTank(double powerL, double powerR){
    leftDriveM.set(ControlMode.PercentOutput, powerL);
    leftDriveS1.follow(leftDriveM);
    leftDriveS2.follow(leftDriveM);
    rightDriveM.set(ControlMode.PercentOutput, powerR);
    rightDriveS1.follow(rightDriveM);
    rightDriveS2.follow(rightDriveM);
  }
/**
 * No control Loop. Percentage of available battery power. Calculates the difference based on Rotation and applies result to Left/Right Motors.
 */
  public void drivePercentArcade(double speed, double rot){
    double valueL = rot - speed;
    double valueR = rot + speed;
    drivePercentTank(valueL, valueR);
  }
/**
 * Will apply Velocity Control Loop to Left and Right Side given (SpeedLeft, SpeedRight)
 */
  public void driveVelocityTank(double velocityL, double velocityR){
    leftDriveM.set(ControlMode.Velocity, velocityL);
    leftDriveS1.follow(leftDriveM);
    leftDriveS2.follow(leftDriveM);
    rightDriveM.set(ControlMode.Velocity, -velocityR);
    rightDriveS1.follow(rightDriveM);
    rightDriveS2.follow(rightDriveM);
  }
/**
 * Will apply Velocity Control Loop to Left and Right Side after calculating (SpeedLeft, SpeedRight)
 */
  public void driveVelocityArcade(double velocity, double rot){
    double valueL = rot - velocity;
    double valueR = rot + velocity;

    driveVelocityTank(valueL, -valueR);
  }
/**
 * Uses encoder positions to get the robot to an absolute position
 * only use when robot is very close to desired position
 * input in Int
 */
  public void drivePositionTank(int posL, int posR){
    leftDriveM.set(ControlMode.Velocity, posL);
    leftDriveS1.follow(leftDriveM);
    leftDriveS2.follow(leftDriveM);
    rightDriveM.set(ControlMode.Velocity, -posR);
    rightDriveS1.follow(rightDriveM);
    rightDriveS2.follow(rightDriveM);
  }
/**
 * Higher level of Drive percent with Gyro added for steering
 * Remember that the turn is additive to whatever the last turn was
 */
  public void driveGyro(double speed, double setpoint, Boolean power){
    double GyroSetpoint = setpoint;
    double GyrokP = 0.0325;// Changed from .03 7/1/19

    double GyroError = GyroSetpoint - gyroValue();

    double GyroCorrection = GyroError *GyrokP;

    // if(GyroCorrection > .75){
    //   GyroCorrection =  .75;
    // }
    // if(GyroCorrection < -.75){
    //   GyroCorrection = -.75;
    // }
    if(power){
      drivePercentArcade(speed, GyroCorrection);
    }else{
      driveVelocityArcade(speed, GyroCorrection);
    }
  }
  /**
   * Uses LimeLight to Align the robot with vision Targets
   */
  public void driveLime(double speed, int armGoal){
    if(armGoal == -2562){
      correction = (llShootXOffset()/60);

    }else{
      correction = (llHomeXOffset()/60);
    }
    leftDriveM.set(ControlMode.PercentOutput,-(speed - correction));
    leftDriveS1.follow(leftDriveM);
    leftDriveS2.follow(leftDriveM);
    rightDriveM.set(ControlMode.PercentOutput,(speed + correction));
    rightDriveS1.follow(rightDriveM);
    rightDriveS2.follow(rightDriveM);
}
  public void ScoreOn(){
    llShootCamera(0);//target
    llShootLed(3);//led on
    llHomeCamera(1);//regular 
    llHomeLed(1);// led off
  }
  public void HomeOn(){
    llHomeCamera(0);
    llHomeLed(3);
    llShootCamera(1);
    llShootLed(1);
  }
  // public void Pimps(int armGoal){
  //   if(armGoal == 50){
  //     //if(llHomeXOffset()/60 <= );
  //   }else if(armGoal == -2562){
  //     correction = !(llShootTarget() == 1) ? 0 : (llShootXOffset()/60);
  //   }
  // }
  // double align(boolean AButton, boolean targetFound, double targetOffsetXAngle, double targetShort, double DriverX){
  //   if(AButton){ 
  //       return !targetFound ? 0.0 :(targetOffsetXAngle/((targetShort/2)+10));
  //   }else{
  //       return DriverX;
  //   }

//}


/**********************************************************************************
 ************          Drive Init             *************************************
 **********************************************************************************/
public void Init(){
  gyroReset();
  leftDriveM.setSelectedSensorPosition(0);
  rightDriveM.setSelectedSensorPosition(0);
//   leftDriveM.configOpenloopRamp(0.15);
//   rightDriveM.configOpenloopRamp(0.15);
// //Slot 0 for Velocity
//   leftDriveM.config_kP(0, 0.0);
//   leftDriveM.config_kI(0, 0.0);
//   leftDriveM.config_kD(0, 0.0);
//   rightDriveM.config_kP(0, 0.0);
//   rightDriveM.config_kI(0, 0.0);
//   rightDriveM.config_kD(0, 0.0);
// //Slot 1 for position
//   leftDriveM.config_kP(1, 0.0);
//   leftDriveM.config_kI(1, 0.0);
//   leftDriveM.config_kD(1, 0.0);
//   rightDriveM.config_kP(1, 0.0);
//   rightDriveM.config_kI(1, 0.0);
//   rightDriveM.config_kD(1, 0.0);
}




/**********************************************************************************
 ************          Drive Update           *************************************
 **********************************************************************************/
public void Update(){

  }
}
