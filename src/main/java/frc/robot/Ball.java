/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

//import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
  
/**
 * Add your docs here.
 */
public class Ball{
    public Solenoid IntakesOut = new Solenoid(2);
    public Solenoid IntakesIn = new Solenoid(3);

    public PowerDistributionPanel PDP = new PowerDistributionPanel();

    public final VictorSPX Shooter = new VictorSPX(11);
    public final VictorSPX Roller = new VictorSPX(9);

    public DigitalInput BallSensor =  new DigitalInput(1);
/**********************************************************************************
 ************          GLOBALS          *******************************************
 **********************************************************************************/

    int BallGrabToggle = 0;
    int BallShootToggle = 0;
    int counter = 0;
    int RollerState = 0;
    double intakeSpin = 0.0;
    boolean BallSense(){return BallSensor.get();}
    double BallShootCurr(){return PDP.getCurrent(11);}


/**********************************************************************************
 ************          Utilities          *****************************************
 **********************************************************************************/

void RollerSpin(double Speed){
    Roller.set(ControlMode.PercentOutput, -Speed);
}
void BallSpin(double Speed){
    Shooter.set(ControlMode.PercentOutput, Speed > 0 ? -1 : Speed < 0 ? 1 : .3);
}
double AntiDrop(){
    RollerState = 0;
    counter = 0;
    if(BallShootCurr() >= 25.0){
        counter++;
        switch (RollerState) {
            case 0:
                intakeSpin = .95;
                if (counter == 50){
                    RollerState = 1;
                }
                break;
            case 1:
                intakeSpin = 0;
            default:
                break;
        }
    }
    return intakeSpin;
}
    public void FloorShoot(){
        Roller.set(ControlMode.PercentOutput, -1);
        Shooter.set(ControlMode.PercentOutput, -1);
    }

    public void BallIntakeIO(Boolean toggle){
        IntakesIn.set(toggle);
        IntakesOut.set(!toggle);      
    }

}
