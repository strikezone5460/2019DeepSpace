/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
  
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.Controls;
  
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public double DeadBand(double Input){
    return Input = Math.abs(Input) < .2 ? 0 : (Input > 0 ? Input-.2 : Input+.2) *1.25;
  }
  private Auton CT = new Auton();//used as a passer class 
  private XboxController Driver = new XboxController(0);
  private XboxController Opp = new XboxController(1);
  private XboxController OppBox = new XboxController(2);
  private XboxController OppBox2 = new XboxController(3);
  
// NetworkTable home table = NetworkTableInstance.getDefault().getTable("limelight-home");
// NetworkTable score table = NetworkTableInstance.getDefault().getTable("limelight-score");


double speed = 0;
double rotate = 0;
double shoot = 0;
double intakeSpin = 0;
double testVal = 0;
double maxCurrentDifference = .5;
double elevator;
double targetShort = 0;
double targetOffsetXAngle = 0; 
double tv = 0.0;

int count = 0;
int dPov = 0;
int oPov = 0;
int armCount = 0;
boolean elevatorMode = false;
int motorTest = 0;
int AutoState = 0;
int AutoPrintCount = 0;
int SelectedAuton = 0;
int swung;
int AutoSelect = 0;
int armMode = 0;
boolean AutonBreak = false;

boolean sideSelector = false;
boolean shift = false;
boolean align = false;
boolean cargoUnlock = false;
boolean armReturn = false;
boolean targetFound = false;
boolean controlUnlock = false;
boolean HatchDetect = false;
boolean Intake = false;
boolean IntakeState = false;
boolean Shoot = false;
boolean once = false;

double  driveLX = 0;
double  driveLY = 0;
double  driveRX = 0;
double  driveRY = 0;
double  driveLT = 0;
double  driveRT = 0;
int drivePov = 0;
boolean driveA = false;
boolean driveB = false;
boolean driveX = false;
boolean driveY = false;
boolean driveLBumper = false;
boolean driveRBumper = false;
boolean driveBack = false;
boolean driveStart = false;
boolean driveLJoyIn = false;
boolean driveRJoyIn = false;

double  operateLX = 0;
double  operateLY = 0;
double  operateRX = 0;
double  operateRY = 0;
double  operateLT = 0;
double  operateRT = 0;
int operatePov = 0;
boolean operateA = false;
boolean operateB = false;
boolean operateX = false;
boolean operateY = false;
boolean operateLBumper = false;
boolean operateRBumper = false;
boolean operateBack = false;
boolean operateStart = false;
boolean operateLJoyIn = false;
boolean operateRJoyIn = false;

boolean boardLvOne = false;
boolean boardLvTwo = false;
boolean boardLvThree = false;
boolean boardLvFour = false;
boolean boardLvFive = false;
boolean boardLvSix = false;
boolean boardHatch = false;
boolean boardShoot = false;
boolean boardHome = false;
boolean boardArm = false;
boolean boardDriveBy = false;
boolean boardClimbLock = false;
boolean boardClimbWinch = false;
boolean boardClimbOne = false;
boolean boardClimbTwo = false;
boolean boardClimbDrive = false;
boolean print = false;
boolean G20 = false;
boolean autoInit = false;

AnalogInput AirPressureSensor = new AnalogInput(3);


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    CT.Init();// The General initialization
  }
  @Override
  public void robotPeriodic() {
    super.robotPeriodic();

    SmartDashboard.putBoolean("HatchSensor", CT.AR.HatchSensor());
    SmartDashboard.putNumber("ArmEnc", CT.AR.ArmEnc());
    SmartDashboard.putNumber("EleEmc", CT.EL.EleEnc());
    SmartDashboard.putString("Shifter", (CT.DT.shiftState? "High Gear" : "Low Gear"));
//    SmartDashboard.putBoolean("HatchSensor", true);



    count++;
    if(count %40 == 0){
      print = true;
    }
    else if(print == true){
      print = false;
    }
    // if(count % 40 == 1){
    //   System.out.print("Buttons Pressed: ");
    // }
  
    // //Driver Buttons Pressed
    // driveA = Driver.getRawButtonReleased(CT.AButton);
    // if(driveA){
    //     System.out.print("DrA ");
    // }
     driveB = Driver.getRawButtonReleased(CT.BButton);
    // if(driveB){
    //     System.out.print("DrB ");
    // }
     driveX = Driver.getRawButtonReleased(CT.XButton);
    // if(driveX){
    //     System.out.print("DrX ");
    // }
    // driveY = Driver.getRawButtonReleased(CT.YButton);
    // if(driveY){
    //     System.out.print("DrY ");
    // }
    // driveLBumper = Driver.getRawButtonReleased(CT.LeftBumper);
    // if(driveLBumper){
    //     System.out.print("DrLB ");
    // }
    // driveRBumper = Driver.getRawButtonReleased(CT.RightBumper);
    // if(driveRBumper){
    //     System.out.print("DrRB ");
    // }
    // driveBack = Driver.getRawButtonReleased(CT.BackButton);
    // if(driveBack){
    //     System.out.print("DrBack ");
    // }
    // driveStart = Driver.getRawButtonReleased(CT.StartButton);
    // if(driveStart){
    //     System.out.print("DrStart ");
    // }
    // driveLJoyIn = Driver.getRawButtonReleased(CT.LeftJoystickIn);
    // if(driveLJoyIn){
    //     System.out.print("DrLJoyIn ");
    // }
    // driveRJoyIn = Driver.getRawButtonReleased(CT.RightJoystickIn);
    // if(driveRJoyIn){
    //     System.out.print("DrRJoyIn ");
    // }
    // drivePov = Driver.getPOV(0);
    // if(drivePov != -1){
    //     System.out.print("DrPOV " + drivePov);
    // }
    // driveLX = Driver.getRawAxis(CT.LeftJoystickHorizontal);
    // driveLY = Driver.getRawAxis(CT.LeftJoystickVertical);
    // driveRX = Driver.getRawAxis(CT.RightJoystickHorizontal);
    // driveRY = Driver.getRawAxis(CT.RightJoystickVertical);
    // driveLT = Driver.getRawAxis(CT.LeftTrigger);
    // driveRT = Driver.getRawAxis(CT.RightTrigger);
  
    // //Operator Buttons Pressed
    // operateA = Opp.getRawButtonReleased(CT.AButton);
    // if(operateA){
    //     System.out.print("OpA ");
    // }
    // operateB = Opp.getRawButtonReleased(CT.BButton);
    // if(operateB){
    //     System.out.print("OpB ");
    // }
    // operateX = Opp.getRawButtonReleased(CT.XButton);
    // if(operateX){
    //     System.out.print("OpX ");
    // }
    // operateY = Opp.getRawButtonReleased(CT.YButton);
    // if(operateY){
    //     System.out.print("OpY ");
    // }
    // operateLBumper = Opp.getRawButtonReleased(CT.LeftBumper);
    // if(operateLBumper){
    //     System.out.print("OpLB ");
    // }
    // operateRBumper = Opp.getRawButtonReleased(CT.RightBumper);
    // if(operateRBumper){
    //     System.out.print("OpRB ");
    // }
    // operateBack = Opp.getRawButtonReleased(CT.BackButton);
    // if(operateBack){
    //     System.out.print("OpBack ");
    // }
    // operateStart = Opp.getRawButtonReleased(CT.StartButton);
    // if(operateStart){
    //     System.out.print("OpStart ");
    // }
    // operateLJoyIn = Opp.getRawButtonReleased(CT.LeftJoystickIn);
    // if(operateLJoyIn){
    //     System.out.print("OpLJoyIn ");
    // }
    // operateRJoyIn = Opp.getRawButtonReleased(CT.RightJoystickIn);
    // if(operateRJoyIn){
    //     System.out.print("OpRJoyIn ");
    // }
    // operatePov = Opp.getPOV(0);
    // if(operatePov != -1){
    //     System.out.print("OpPOV%i " + operatePov);
    // }
    // operateLX = Opp.getRawAxis(CT.LeftJoystickHorizontal);
    // operateLY = Opp.getRawAxis(CT.LeftJoystickVertical);
    // operateRX = Opp.getRawAxis(CT.RightJoystickHorizontal);
    // operateRY = Opp.getRawAxis(CT.RightJoystickVertical);
    // operateLT = Opp.getRawAxis(CT.LeftTrigger);
    // operateRT = Opp.getRawAxis(CT.RightTrigger);
  
    // //Button Board Buttons Pressed
    // boardLvOne = OppBox.getRawButtonPressed(CT.ElevatorOne);
    // if(boardLvOne){
    //   System.out.print("BBLv1 ");
    // }
    // boardLvTwo = OppBox.getRawButtonPressed(CT.ElevatorTwo);
    // if(boardLvTwo){
    //   System.out.print("BBLv2 ");
    // }
    // boardLvThree = OppBox.getRawButtonPressed(CT.ElevatorThree);
    // if(boardLvThree){
    //   System.out.print("BBLv3 ");
    // }
    // boardLvFour = OppBox.getRawButtonPressed(CT.ElevatorFour);
    // if(boardLvFour){
    //   System.out.print("BBLv4 ");
    // }
    // boardLvFive = OppBox.getRawButtonPressed(CT.ElevatorFive);
    // if(boardLvFive){
    //   System.out.print("BBLv5 ");
    // }
    // boardLvSix = OppBox.getRawButtonPressed(CT.ElevatorSix);
    // if(boardLvSix){
    //   System.out.print("BBLv6 ");
    // }
    // boardHatch = OppBox.getRawButtonPressed(CT.HatchButton);
    // if(boardHatch){
    //   System.out.print("BBHatch ");
    // }
    // boardHome = OppBox.getRawButtonPressed(CT.HomeButton);
    // if(boardHome){
    //   System.out.print("BBHome ");
    // }
    // boardShoot = OppBox2.getRawButtonPressed(CT.ShootButton);
    // if(boardShoot){
    //   System.out.print("BBShoot ");
    // }
    // boardArm = OppBox2.getRawButtonPressed(CT.ArmRotateButton);
    // if(boardArm){
    //   System.out.print("BBArm ");
    // }
    // boardDriveBy = OppBox.getRawButtonPressed(CT.DriveByButton);
    // if(boardDriveBy){
    //   System.out.print("BBDriveBy ");
    // }
    // boardClimbLock = OppBox2.getRawButtonPressed(CT.ClimberLock);
    // if(boardClimbLock){
    //   System.out.print("BBClimbUnlock ");
    // }
    // boardClimbWinch = OppBox2.getRawButtonPressed(CT.ClimberWinch);
    // if(boardClimbWinch){
    //   System.out.print("BBClimbWinch ");
    // }
    // boardClimbOne = OppBox2.getRawButtonPressed(CT.ClimberReleaseOne);
    // if(boardClimbOne){
    //   System.out.print("BBClimbOne ");
    // }
    // boardClimbTwo = OppBox2.getRawButtonPressed(CT.ClimberReleaseTwo);
    // if(boardClimbTwo){
    //   System.out.print("BBClimbTwo ");
    // }
    // boardClimbDrive = OppBox2.getRawButtonPressed(CT.ClimberDrive);
    // if(boardClimbDrive){
    //   System.out.print("BBClimbDrive");
    // }
    if (driveX) {
      sideSelector = false;
    }
    else if(driveB){
      sideSelector = true;
    }
  
    //Health Bar
    if(print){
      // System.out.print("\n");
      // System.out.println("Button Board " + (controlUnlock ? "DISABLED!!!" : "Enabled") );
      System.out.println("LimeLight Dist " + CT.DT.llShootShort() + "||" + CT.DT.llHomeShort());
      System.out.println("Gyro Value " + CT.DT.gyroValue());
      System.out.println("Encoder stuff " + CT.DT.leftEnc() + "||" + CT.DT.rightEnc());
      System.out.println("Arm Enc: "+ CT.AR.ArmEnc() +" Arm Goal: " + swung );
      // System.out.println("Ele Enc: "+ CT.EL.EleEnc());
      //System.out.println("Distance sensor: " + CT.DT.DistSense());
    }

  } 
  @Override
  public void disabledPeriodic() {
    super.disabledPeriodic();
    AutoPrintCount ++;
    if(Driver.getYButtonPressed()){
      CT.Init();
    }
    if(Driver.getStartButtonPressed()&& (SelectedAuton <= 10)){
      SelectedAuton += 1;
    }else if(Driver.getBackButtonPressed() &&(SelectedAuton >= 0)){
      SelectedAuton -= 1;
    }
    if(count% 40 == 0){
      System.out.println("Selected Auton " + SelectedAuton + " Side " + (sideSelector? "Right":"Left"));
    }

  }
  @Override
  public void autonomousInit() {
    autoInit = true;
     CT.autoInit();
  }

  @Override
  public void autonomousPeriodic() {
    if((Driver.getStickButton(Hand.kLeft)) || (CT.autoPeriodic(SelectedAuton, sideSelector))){
      AutonBreak = true;
    }
    if(AutonBreak == true){
      if(once == false){ 
        teleopInit();
      }else{
        teleopPeriodic();
      }
    }else{
      CT.autoPeriodic(SelectedAuton,sideSelector);
    }

  }

  @Override
  public void teleopInit() {
    if((once == false) && (autoInit == true)){
      swung = CT.ArmEnd;
      elevator = CT.EleEnd;
      once = true;
    }// if enabled in teleop before auto reset code
    // teleopPeriodic();
    // System.out.println("Your stuck in here");
  }

  @Override
  public void teleopPeriodic() {
    if ((AutonBreak == false) && (autoInit == true)){
      CT.autoPeriodic(SelectedAuton, sideSelector);

      if((CT.isFinished == true) || (Driver.getStickButton(Hand.kLeft))){
        AutonBreak = true;
      }
    }else{


      once = true;
      double DriverY = DeadBand(Driver.getY(Hand.kLeft));
      double DriverX = DeadBand(Driver.getX(Hand.kRight));

      dPov = Driver.getPOV(0);
      oPov = Opp.getPOV(0);


  /**********************************************************************************
   ************          DriverControl          *************************************
  **********************************************************************************/
      align = Driver.getAButton();

      intakeSpin = DeadBand(Driver.getRawAxis(3)) > 0 ? .95 : Driver.getRawButton(6) ? -.95 : 0;
      // if((DeadBand(Driver.getTriggerAxis(Hand.kLeft)) > 0) && (Intake == false)){
      //   if(CT.BA.BallShootCurr() < 20){
      //       IntakeState = !IntakeState;//intake toggle
      //       CT.IntakesIO(IntakeState);
      //       Intake = true;
      //     }else /*if(CT.BA.BallShootCurr() >= 20)*/ {
      //     CT.IntakesIO(true);
      // }
      //   }else if((DeadBand(Driver.getTriggerAxis(Hand.kLeft)) == 0)){
      //       Intake = false;  
      // }
    if(Driver.getYButtonPressed()){
      G20 = !G20;
    } 
    if(G20){
      //if(!OppBox.getRawButton(5)){
        if(CT.BA.BallShootCurr() < 20){
          if((DeadBand(Driver.getTriggerAxis(Hand.kLeft)) > 0) && (Intake == false)){
            IntakeState = !IntakeState;//intake toggle
            CT.IntakesIO(IntakeState);
            Intake = true;
          }else if((DeadBand(Driver.getTriggerAxis(Hand.kLeft)) == 0)){
            Intake = false;
          }
        } else /*if(CT.BA.BallShootCurr() >= 20)*/ {
            CT.IntakesIO(true);
        }
      //}
    }else{
      if(DeadBand(Driver.getTriggerAxis(Hand.kLeft)) > 0 && Intake == false){
        IntakeState = !IntakeState;//intake toggle
        CT.IntakesIO(IntakeState);
        Intake = true;
      }else if(DeadBand(Driver.getTriggerAxis(Hand.kLeft)) == 0){
        Intake = false;
      }
    }
      CT.Shift(Driver.getBumperPressed(Hand.kLeft));
  /**********************************************************************************
   ************          OpperatorControl          **********************************
  **********************************************************************************/
  if(OppBox2.getRawButtonReleased(5))controlUnlock = false;
  else if(Opp.getRawButtonPressed(4))controlUnlock = true;

  if(controlUnlock == false){
    elevatorMode = false;
    armMode = 0;
    if(OppBox.getRawButtonPressed(2)){
      //intakeSpin = CT.BA.AntiDrop();
      elevator = 0;
      swung = 50;

      // CT.DT.HomeOn();
    }
    else if(OppBox.getRawButtonPressed(10)){
      //intakeSpin = CT.BA.AntiDrop();
      elevator = 0;
      swung = -2562;
      // CT.DT.ScoreOn();
    }
    else if(OppBox.getRawButtonPressed(4)){
      //intakeSpin = CT.BA.AntiDrop();
      elevator = 2;
      swung = -2562;
      // CT.DT.ScoreOn();
    }
    else if(OppBox.getRawButtonPressed(5)){
      //intakeSpin = CT.BA.AntiDrop();
      intakeSpin = .95;
      elevator = 3;
      swung = -2562;
      intakeSpin = 0;
      // CT.DT.ScoreOn();
    }
    else if(OppBox.getRawButtonPressed(6)){
      //intakeSpin = CT.BA.AntiDrop();
      elevator = 4;
      swung = -2562;
      // CT.DT.ScoreOn();
    }
    else if(OppBox.getRawButtonPressed(8)){
      //intakeSpin = CT.BA.AntiDrop();
      elevator = 5;
      swung = -2562;
      // CT.DT.ScoreOn();
    }
    else if(OppBox.getRawButtonPressed(3)){
      //intakeSpin = CT.BA.AntiDrop();
      elevator = 6;
      swung = -2562;
      // CT.DT.ScoreOn();
    }
    if(OppBox2.getRawButtonPressed(2)){
      swung = (swung == -2562 ? 50 : -2562);

    }
    else if(OppBox.getRawButtonPressed(1)){
      swung = -1245;
      if(elevator <= 1 && elevatorMode == false){
        elevator = 2;
      }
    }

    if (OppBox.getRawButtonPressed(9)) {
      CT.AR.Hatch(true);

      if(CT.AR.HatchSensor()){
        HatchDetect = true;
      }else{
        HatchDetect = false;
      }
    }else if(OppBox.getRawButtonReleased(9) || (CT.AR.HatchSensor() && HatchDetect == false)){
      CT.AR.Hatch(false);
    }

    Shoot = (OppBox2.getRawButton(5));

    // CT.ClimbMode(OppBox2.getRawButton(6), OppBox2.getRawButton(8), OppBox2.getRawButton(7), OppBox2.getRawButton(4), Opp.getRawButton(1), DriverY);
    boolean NukeSwitch = OppBox2.getRawButton(6);
    if(NukeSwitch){
      CT.CL.ClimbDown(OppBox2.getRawButton(8));
      if(OppBox2.getRawButton(1)){
        CT.CL.ClimbDriver(-1);
      }else{
        CT.CL.ClimbDriver(-DriverY);
      }
      // CT.CL.ClimbDrive(OppBox2.getRawButton(1));
      CT.CL.FrontUp(OppBox2.getRawButtonPressed(4));
      CT.CL.BackUp(OppBox2.getRawButton(7));
    }
    if(Driver.getRawButtonPressed(8)){
      CT.DT.gyroReset();
    }

  }
  else if(controlUnlock == true){
      boolean cargoUnlock = Opp.getBumper(Hand.kLeft);// || !CT.BA.BallSense();
      if(Opp.getTriggerAxis(Hand.kRight) > .5 || OppBox.getRawButton(7)){
        // CT.BA.BallShoot();
      }else{
      //  CT.BA.BallShootOff();
      }
    if(elevatorMode == false){
      if(Opp.getBButtonPressed()|| OppBox2.getRawButtonPressed(13)){
        elevator = 0;
        swung = 10;
      }
      else if(oPov == 180) elevator = (cargoUnlock ? 2 : 0);
      else if(oPov == 90) elevator = (cargoUnlock ? 4 : 3);
      else if(oPov == 0) elevator = (cargoUnlock ? 6 : 5);
    }
    else if(elevatorMode == true) elevator = DeadBand(Opp.getRawAxis(1));

    if(Opp.getRawButtonPressed(6)){
      swung = (swung == -2562 ? 10 : -2562);
      if(elevator == 0 || elevator == 1) elevator = 2;
    }
    else if(Opp.getRawButtonPressed(3)){
      swung = -1244;
      if(elevator == 0 || elevator == 1) elevator = 2;
    }
    CT.ClimbMode(OppBox2.getRawButton(7), OppBox2.getRawButton(8), OppBox2.getRawButton(5), OppBox2.getRawButtonPressed(6), OppBox2.getRawButtonPressed(11), DriverY);
    CT.AR.HatchToggle(Opp.getAButtonPressed()|| OppBox2.getRawButtonPressed(14));



      if(Math.abs(DeadBand(Opp.getRawAxis(1))) > 0) elevatorMode = true;
      CT.EL.ElevatorMove(elevator,elevatorMode);
      // swong(swung);
    }
  //
    CT.EL.ElevatorMove(elevator,elevatorMode);
    CT.armRotate(swung,armMode);
    CT.BA.RollerSpin(intakeSpin);
    CT.BA.BallSpin(Shoot == true ? .75 : intakeSpin > 0 ? -1 : 0);
    if(align){
      CT.DT.driveLime(DriverY, swung);
    }else/* if(CT.DT.DistSense() <= 0.5)*/{
      CT.drivePercentArcade(DriverY, DriverX);
    }/*else{
      CT.drivePercentArcade(0, 0);
    }*/

    if(swung >= 0) CT.DT.HomeOn();
    else CT.DT.ScoreOn();
      if(count++ %20 == 0){

        // System.out.println("Drive Pos Left: " + CT.DT.leftDriveM.getSelectedSensorPosition());
        // System.out.println("Drive Pos right: " + CT.DT.rightDriveM.getSelectedSensorPosition());
        // System.out.println("Cargo: " + cargoUnlock);
        // System.out.println("Elevator Enc: " + CT.EL.EleEnc());
        // System.out.println("Elevator Current: " + CT.EL.ElevatorMCurr());
        // System.out.println("Elevator Ask: " + CT.EL.ElevatorM.getMotorOutputPercent() + "||" + CT.EL.ElevatorS.getMotorOutputPercent());
        // System.out.println("Elevator Setpoint: " + elevatorMode);
        // System.out.println("Swung: " + swung);
        // System.out.println("Target? " + CT.DT.llShootTarget());

      }
      if(count %20 == 10){ 
        // System.out.println("Arm Current: " + CT.AR.ArmCurr());
        // System.out.println("Arm Ask: " + CT.AR.ArmRot.getMotorOutputPercent());
        // System.out.println("Arm setpoint: " +swung);
        // System.out.println("ArmEnc: " + CT.AR.ArmEnc());
        // System.out.println("Correction: " + CT.DT.correction);

      }
    }
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}