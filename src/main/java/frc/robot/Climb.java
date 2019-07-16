/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
  
/**
 * Add your docs here.
 */
public class Climb {
    Solenoid ClimbFront = new Solenoid(5);
    Solenoid ClimbBack = new Solenoid(6);
    Solenoid ClimbBack2 = new Solenoid(7);

    VictorSPX ClimberDown = new VictorSPX(12);
    VictorSPX ClimberDownS = new VictorSPX(13);
    VictorSPX ClimbDrive = new VictorSPX(14);

    DigitalInput FrontLimitSwitch = new DigitalInput(2);
    DigitalInput BackLimitSwitch = new DigitalInput(3);
    DigitalInput FrontDetect = new DigitalInput(4);
    DigitalInput BackDetect = new DigitalInput(5);
    

    int count = 0;
    int ClimbState = 0;

    boolean FrontLock = false;
    boolean BackLock = false;
    
    
    // void ClimbTo3(boolean Nuke){
    //     if(Nuke){
    //         count++;
    //         switch (ClimbState) {
    //             case 0:
    //                 ClimberDown.set(ControlMode.PercentOutput, .9);
    //                 if(DownEnc >= kAllTheWayUp){
    //                     ClimberDown.set(ControlMode.PercentOutput, 0.0);
    //                     ClimberDownS.follow(ClimberDown);
    //                     ClimbState = 1;
    //                     count = 0;
    //                 }
    //                 break;
    //             case 1:
    //                 ClimbDrive.set(ControlMode.PercentOutput, .75);
    //                 if(DriveEnc == kFrontUp){
    //                     ClimbFront.set(true);
    //                 }
    //                 if(DriveEnc == kBackUp){
    //                     ClimbBack.set(true);
    //                 }
    //                 break;
    //             case 2:
    //                 ClimbState = 3;
    //                 count = 0;
    //                 break;
    //             default:
    //                 break;
    //         }

    //     }
    // }
    void ClimbDown(boolean One){
        if(One && FrontLimitSwitch.get()){
            ClimberDown.set(ControlMode.PercentOutput, 1);
            ClimberDownS.follow(ClimberDown);
        }else{
            ClimberDown.set(ControlMode.PercentOutput, 0);
            ClimberDownS.follow(ClimberDown);   
        }
    }
    void ClimbDriver(double speed){
        ClimbDrive.set(ControlMode.PercentOutput, speed);
    }
    void ClimbDrive(boolean Two){
        if(Two){
            ClimbDrive.set(ControlMode.PercentOutput, -1);
        }else{
            ClimbDrive.set(ControlMode.PercentOutput, 0);
        }
    }
    void FrontUp(boolean Three){
        if(Three){
            ClimbFront.set(true);
        }
    }
    void BackUp(boolean four){
        if(four){
            ClimbBack.set(true);
        }
    }
    void AutoClimb(boolean BBTwo8){
        count++;
        ClimbState = 0;
        if(BBTwo8){
            switch (ClimbState) {
                case 0:
                    ClimberDown.set(ControlMode.PercentOutput, 1);
                    ClimberDownS.follow(ClimberDown);
                    if(FrontLimitSwitch.get() != true){
                        ClimberDown.set(ControlMode.PercentOutput, 0);
                        ClimberDownS.follow(ClimberDown);
                        ClimbState = 1;
                        count = 0;
                    }
                    break;
                case 1:
                    if(FrontDetect.get() != true){
                        FrontLock = true;
                        ClimbFront.set(FrontLock);
                        ClimbState = 2;
                        count = 0;
                    }
                    break;
                case 2:
                    if(BackLimitSwitch.get() == true){
                        ClimberDown.set(ControlMode.PercentOutput, 1);
                        ClimberDownS.follow(ClimberDown); 
                    }else{
                        ClimberDown.set(ControlMode.PercentOutput, 0);
                        ClimberDownS.follow(ClimberDown); 
                    }
                    if(BackDetect.get() != true){
                        BackLock = true;                      
                        ClimbBack.set(BackLock);
                        ClimbState = 99;
                    }
                default:
                    break;
            }
        }
    }
    
}
