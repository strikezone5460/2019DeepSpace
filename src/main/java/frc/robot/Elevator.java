/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
 
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

// import edu.wpi.first.wpilibj.PowerDistributionPanel;
 
/**
 * Add your docs here.
 */
public class Elevator{
    public final TalonSRX ElevatorM = new TalonSRX(7);
    public final VictorSPX ElevatorS = new VictorSPX(8);

    //public PowerDistributionPanel PDP = new PowerDistributionPanel();

    static double kUSDigital = 4.88;

static public double EleLevel0 = 0;    
static public double EleSafe   = Math.rint(-1230 * kUSDigital);
static public double EleLevel2 = Math.rint(-1538 * kUSDigital);
static public double EleLevel3 = Math.rint(-2500 * kUSDigital);
static public double EleLevel4 = Math.rint(-3770 * kUSDigital);
static public double EleLevel5 = Math.rint(-4930 * kUSDigital);
static public double EleLevel6 = Math.rint(-6200 * kUSDigital);
/**********************************************************************************
 ************          GLOBALS          *******************************************
 **********************************************************************************/

    int EleEnc(){return ElevatorM.getSelectedSensorPosition();}
    int EleVelocity(){return ElevatorM.getSelectedSensorVelocity();}

    double ElevatorMCurr(){return ElevatorM.getOutputCurrent();}
    // double ElevatorSCurr(){return PDP.getCurrent(12);}
    double ElevatorMTemp(){return ElevatorM.getTemperature();}
    double ElevatorSTemp(){return ElevatorS.getTemperature();}

    double VelError = 0.0;
    double VelSpeed = 0.0;
    double level = 0;
    double setH = 0;
    boolean mode = false;
    boolean safety = false;



    

/**********************************************************************************
 ************          Utilities          *****************************************
 **********************************************************************************/
    boolean getElevatorMode(){
        return mode;
    }
    public void ResetEleEnc(){ElevatorM.setSelectedSensorPosition(0,0,10);}
    double getElevator(){
        return level;
      }
      

    void elevatorSafety(boolean on){
        safety = on;
    }
    
    public void EleMoveManual(double speed){
        ElevatorM.set(ControlMode.PercentOutput, speed);
        ElevatorS.set(ControlMode.PercentOutput, speed);
    }

    public void EleReset(){ElevatorM.setSelectedSensorPosition(0);}

    //===Positions in negitive because it really do be like that===
 
    /**
     * Natural Level of Elevator, also hatch lvl1
     */
    public void EleLvl0(){
        ElevatorM.set(ControlMode.Position, 0);
        ElevatorS.follow(ElevatorM);
    }
    /**
     * Raises elevator to a safe position to turn Arm
     */
    public void EleSafe(){
        ElevatorM.set(ControlMode.Position, EleSafe);
        ElevatorS.follow(ElevatorM);
    }
    /**
     * Ball lvl1
     */
    public void EleLvl2(){
        ElevatorM.set(ControlMode.Position, EleLevel2);
        ElevatorS.follow(ElevatorM);
        }
    /**
     * Hatch lvl2
     */
    public void EleLvl3(){
        ElevatorM.set(ControlMode.Position, EleLevel3);
        ElevatorS.follow(ElevatorM);
        }
    /**
     * Ball lvl2
     */
    public void EleLvl4(){
        ElevatorM.set(ControlMode.Position, EleLevel4);
        ElevatorS.follow(ElevatorM);
        }
    /**
     * Hatch lvl3
     */
    public void EleLvl5(){
        ElevatorM.set(ControlMode.Position, EleLevel5);
        ElevatorS.follow(ElevatorM);
        }
    /**
     * Ball lvl3
     */
    public void EleLvl6(){
        ElevatorM.set(ControlMode.Position, EleLevel6);
        ElevatorS.follow(ElevatorM);
        }
    // void ElevatorMove(double elevator,int elevatorMode){
    //     if(elevatorMode == 1) ElevatorM.set(ControlMode.PercentOutput,elevator);
    //     else if(elevatorMode == 0){
    //         level = elevator;
    //         setH = level == 1 ? 0 : level == 2 ? EleLevel2 : level == 3 ? EleLevel3 : level == 4 ? EleLevel4 : level == 5 ? EleLevel5: level == 6 ? EleLevel6 : setH;
    // if(level == 0){
    //             if(Math.abs(EleEnc()) < 25) ElevatorM.set(ControlMode.PercentOutput,0);
    //             else if(Math.abs(EleEnc()) < 1600){
    //                 EleVelocityMode(500);
    //             }
    //             else ElevatorM.set(ControlMode.Position,setH);
    //         }
    //         else ElevatorM.set(ControlMode.Position,setH);
    //     }
    //     // elevatorS.Set(ControlMode::Follower,elevatorM.GetDeviceID());
    //     ElevatorS.follow(ElevatorM);
    // }
    public void ElevatorMove(double elevator,boolean elevatorMode){
        level = elevator;
        mode = elevatorMode;
    
        if(elevatorMode == true){
            ElevatorM.set(ControlMode.PercentOutput,elevator);
        }
        else if(elevatorMode == false){
            if(safety == true && level == 0){
                level = 1;
            }
              
            if(level == 0){
                setH = EleLevel0;
            }
            else if(level == 1){
                setH = EleSafe;
            }
            else if(level == 2){
                setH = EleLevel2;
            }
            else if(level == 3){
                setH = EleLevel3;
            }
            else if(level == 4){
                setH = EleLevel4;
            }
            else if(level == 5){
                setH = EleLevel5;
            }
            else if(level == 6){
                setH = EleLevel6;
            }
    
            if(level == 0){
                if(Math.abs(EleEnc()) < 500 ){
                    ElevatorM.set(ControlMode.PercentOutput,0);
                }
                else if(Math.abs(EleEnc()) < 4003){
                    EleVelocityMode(1750);
                }
                else{
                    ElevatorM.set(ControlMode.Position,setH);
                }
                //Soft stop
            }
            else{
                ElevatorM.set(ControlMode.Position,setH);
            }
            //Mode 0: Position, Mode 1: Manual
        }
        // elevatorS.Set(ControlMode::Follower,elevatorM.GetDeviceID());
        ElevatorS.follow(ElevatorM);
    }    
    void EleVelocityMode(int setpoint){
        VelError = setpoint - EleVelocity();
        VelSpeed = -((setpoint * 0.00015) - (VelError * 0.0002));
        ElevatorM.set(ControlMode.PercentOutput, VelSpeed);
        ElevatorS.follow(ElevatorM);
        
    }
/*********************************************************************************
 ***********          Elevator Init          *************************************
 **********************************************************************************/


        // public void EleInit(){

        //     ElevatorM.config_kP(0,5,10);
        //     ElevatorM.config_kI(0,0.05,10);
        //     ElevatorM.config_kD(0,512,10);
        //     ElevatorM.configMaxIntegralAccumulator(0,100,10);
        
        //     ElevatorM.setSensorPhase(true);    
        //     ElevatorM.selectProfileSlot(0,0);
        //     ElevatorM.configClosedloopRamp(0.1);
        //     ElevatorM.configPeakOutputForward(0.35);
        //     ElevatorM.configPeakOutputReverse(-0.95);
        
        //     ElevatorM.setInverted(InvertType.InvertMotorOutput);
        //     // elevatorS.SetInverted(motorcontrol::InvertType::InvertMotorOutput);
        //     ElevatorS.setInverted(InvertType.FollowMaster);
        
        //     ElevatorM.enableVoltageCompensation(true);
        //     ElevatorM.configVoltageCompSaturation(10);
        
        //     ElevatorM.enableCurrentLimit(true);
        //     ElevatorM.configContinuousCurrentLimit(16);
        //     ElevatorM.configPeakCurrentLimit(22);
        //     ElevatorM.configPeakCurrentDuration(100);
        //     ElevatorM.setSelectedSensorPosition(0,0,10);
                    
        // }
        void EleInit(){
            ElevatorM.config_kP(0,1.2,10);
            ElevatorM.config_kI(0,0.05);
            ElevatorM.config_kD(0,180,10);
            ElevatorM.configMaxIntegralAccumulator(0,100,10);
    
            ElevatorM.setSensorPhase(true);    
            ElevatorM.selectProfileSlot(0,0);
            ElevatorM.configClosedloopRamp(0.1);
            ElevatorM.configPeakOutputForward(0.27);
            ElevatorM.configPeakOutputReverse(-1);
    
            ElevatorM.setInverted(InvertType.InvertMotorOutput);
            ElevatorS.setInverted(InvertType.FollowMaster);
    
            ElevatorM.enableVoltageCompensation(true);
            ElevatorM.configVoltageCompSaturation(10.5);
    
            ElevatorM.enableCurrentLimit(true);
            ElevatorM.configContinuousCurrentLimit(20);
            ElevatorM.configPeakCurrentLimit(30);
            ElevatorM.configPeakCurrentDuration(850);
            ElevatorM.setSelectedSensorPosition(0, 0, 10);
        }
}
