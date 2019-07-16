/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
 
/**
 * Add your docs here.
 */
public class Controls extends SemiAutons{
    void drivePercentArcade(double DriverY, double DriverX){
        DT.drivePercentArcade(DriverY, DriverX);
    }
    // void BallStuff(double RightTrigg, boolean RightBumper, boolean XButton){
    //     if((RightTrigg) > .25){
    //         BA.BallGrab();
    //     }else if(RightBumper) {
    //         BA.BallSpit();
    //     }else if(XButton){
    //         BA.FloorShoot();
    //     }else{
    //         BA.BallGrabOff();
    //     }
 
    // }
    void IntakesIO(boolean LeftTrigg){
        BA.BallIntakeIO(LeftTrigg);
    }
    void Shift(boolean LeftBumper){
        DT.ShiftToggle(LeftBumper);
    }
    // void Climb(boolean NukeSwitch){
    //     CL.ClimbTo3(NukeSwitch);
    // }
    void ClimbMode(boolean NukeSwitch, boolean One, boolean Two, boolean Three, boolean four, double DriverY){
        if(NukeSwitch){
            CL.ClimbDriver(-DriverY);
            CL.ClimbDown(One);
            CL.ClimbDrive(Two);
            CL.FrontUp(Three);
            CL.BackUp(four);
        }
    }
    // void Spit(){
    //     if(RightBumper){
    //     }else{
    //         BA.BallGrabOff();
    //     }
    // }
        int AButton = 1;
        int BButton = 2;
        int XButton = 3;
        int YButton = 4;
        int LeftBumper = 5;
        int RightBumper = 6;
        int BackButton = 7;
        int StartButton = 8;
        int LeftJoystickIn = 9;
        int RightJoystickIn = 10;

        int LeftJoystickVertical = 1;
        int LeftJoystickHorizontal = 0;
        int LeftTrigger = 2;
        int RightJoystickVertical = 5;
        int RightJoystickHorizontal = 4;
        int RightTrigger = 3;

        //Button Board
        int ElevatorOne = 10;
        int ElevatorTwo = 4;
        int ElevatorThree = 5;
        int ElevatorFour = 6;
        int ElevatorFive = 8;
        int ElevatorSix = 3;

        int HatchButton = 9;
        int ShootButton = 5;
        int HomeButton = 2;
        int ArmRotateButton = 2;
        int DriveByButton = 1;

        int ClimberLock = 6;
        int ClimberWinch = 8;
        int ClimberReleaseOne = 7;
        int ClimberReleaseTwo = 4;
        int ClimberDrive = 1;

    
}
