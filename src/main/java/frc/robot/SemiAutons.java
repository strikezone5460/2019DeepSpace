/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import frc.robot.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
 
import frc.robot.Arm;
import frc.robot.Ball;
/**
 * Add your docs here.
 */
public class SemiAutons {
    public Elevator EL = new Elevator();
    public Arm AR = new Arm();
    public Ball BA = new Ball();
    public DriveTrain DT = new DriveTrain();
    public Climb CL = new Climb();
    
    double direction = 0;
    int armCount = 0;


    void Init(){
        EL.EleInit();
        AR.ArmInit();
        DT.Init();
        direction = 0;
    }


// public boolean ArmRotate(int Swing){
//     if(EL.EleEnc() < -840) direction = Swing;
//     AR.ArmRot.set(ControlMode.Position,direction);
//     return (((Swing < -425) && (AR.ArmEnc() < -425)) || ((Swing > -90) && (AR.ArmEnc() > -90)) ? false : true);
//     }
public void armRotate(double swang,int mode){
    if(mode == 0){
        if(EL.EleEnc() < -910){
            direction = swang;
        }

        if(!(((swang < -2075) && (AR.ArmEnc() < -2075)) || ((swang > -469) && (AR.ArmEnc() > -469))) && EL.getElevatorMode() == false){
            EL.elevatorSafety(true);
            //If arm isnt in the same zone as the target and the elevator pos is 0, the lowest the elevator can go is level 1
        }
        else if(EL.getElevatorMode() == false && EL.getElevator() == 1){
            EL.elevatorSafety(false);
        }

        AR.ArmRot.set(ControlMode.Position,direction);
    }
    else if(mode == 1){
        AR.ArmRot.set(ControlMode.PercentOutput,swang);
    }
    //Mode 0: Position, Mode 1: Manual
} 

void ClimbDrive(double Speed){
    DT.drivePercentArcade(Speed, 0);
    CL.ClimbDrive.set(ControlMode.PercentOutput, Speed);
  }
}

