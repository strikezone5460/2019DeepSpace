/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
 
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
 
import edu.wpi.first.wpilibj.Solenoid;
/**
 * Add your docs here.
 */
public class Arm{
    public Solenoid Hatch = new Solenoid(4);
    // public Solenoid Pusher = new Solenoid(8);



    public final TalonSRX ArmRot = new TalonSRX(10);
    public boolean HatchSensor(){return ArmRot.getSensorCollection().isFwdLimitSwitchClosed()||ArmRot.getSensorCollection().isRevLimitSwitchClosed();}
 //   public boolean HatchSensor(){return ArmRot.getSensorCollection().isRevLimitSwitchClosed();}
    
/**********************************************************************************
 ************          GLOBALS          *******************************************
 **********************************************************************************/

    int ArmEnc(){return ArmRot.getSelectedSensorPosition();}
    double ArmCurr(){return ArmRot.getOutputCurrent();}

    int HatchGrabToggle = 0;  

    
/**********************************************************************************
 ************          Utilities          *****************************************
 **********************************************************************************/
    //=== Toggles ===//
    public void Hatch(boolean hatchOpen){
        Hatch.set(hatchOpen);
    }
    
    public void HatchToggle(Boolean toggle){
        if(toggle){
        HatchGrabToggle += 1;
    }
        if(HatchGrabToggle == 1){
        Hatch.set(true);
        }else if (HatchGrabToggle == 2){
        HatchGrabToggle = 0;
        Hatch.set(false);
        }                            
    }
    public void ResetArmEnc(){ArmRot.setSelectedSensorPosition(0,0,10);}

    //=== ArmRot Stuff ===//
    public void ArmManual(double Rot){ArmRot.set(ControlMode.PercentOutput, Rot);}
    public void ArmIntakePos(){ArmRot.set(ControlMode.Position, 0);}
    public void ArmDriveByPos(){ArmRot.set(ControlMode.Position, -1249);}
    public void ArmPlacePos(){ArmRot.set(ControlMode.Position, -1500);}

    // public void ArmHold(){
    //     if(ArmEnc() > -70){
    //         ArmRot.set(ControlMode.Position, 0);
    //     }else if(ArmEnc() < -430){
    //         ArmRot.set(ControlMode.Position, -512);
    //     }       
    // }

    void testArmMotors(int motorNumber,float value){
        if(motorNumber == 10) ArmRot.set(ControlMode.PercentOutput,value);
        else ArmRot.set(ControlMode.PercentOutput,0);
    }
    void testArmSolenoid(int solenoidNumber){
        if(solenoidNumber == 2) Hatch.set(true);
        else Hatch.set(false);
    }
    void HatchDetect(){
        if(HatchSensor() == true && Hatch.get() == false){
            Hatch.set(true);
            // Pusher.set(false);
        }else if(HatchSensor() == true){
            Hatch.set(false);
            // Pusher.set(true);
        }
        else{
            Hatch.set(true);
            // Pusher.set(false);
        }
    }
    


/**********************************************************************************
 ************             ArmRot Init            *************************************
 **********************************************************************************/


public void ArmInit(){
    ArmRot.config_kP(0,4 / 4.88,10);
    ArmRot.config_kI(0,0.05 / 4.88,10);
    ArmRot.config_kD(0,550 / 4.88,10);
    ArmRot.configMaxIntegralAccumulator(0,100,10);

    ArmRot.setSensorPhase(true);    
    ArmRot.selectProfileSlot(0,0);
    ArmRot.configClosedloopRamp(0.1);
    ArmRot.configPeakOutputForward(0.95);
    ArmRot.configPeakOutputReverse(-0.95);

    ArmRot.setInverted(InvertType.InvertMotorOutput);

    ArmRot.enableVoltageCompensation(true);
    ArmRot.configVoltageCompSaturation(10);

    ArmRot.enableCurrentLimit(true);
    ArmRot.configContinuousCurrentLimit(15);
    ArmRot.configPeakCurrentLimit(25);
    ArmRot.configPeakCurrentDuration(600);

    ArmRot.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    ArmRot.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);

    ArmRot.setSelectedSensorPosition(0,0,10);

}

}
