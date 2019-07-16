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
public class Auton extends Controls{
  int AutoCount = 0;
  int AutoState = 0;
  int AutonBreak = 0;
  int DriveEnc = 0;
 
  public int ArmEnd = 0;
  public double EleEnd = 0;
  public boolean TeleSwitch = false;

  boolean side = false; //0 == Left,1 == Right
  double AutoMult = .9;
   public boolean isFinished = false;

void autoInit(){
  DT.Init();
  EL.EleInit();
  AR.ArmInit();
  
  DT.ResetEnc();
  EL.ResetEleEnc();
  AR.ResetArmEnc();

  DT.gyroReset();

  AR.Hatch(false);

  AutoCount = 0;
  AutoState = 0;

  ArmEnd = 0;
  EleEnd = 0;

  // side = true;
}

boolean autoPeriodic(int auton, boolean side){
  switch(auton){
    case 0:
      return autoZero();
    case 1:
      return twoRocket(side);
    case 2:
      return CargoRocket(side);
    default:
      return autoZero();

  }
}
int autoCount = 0;
int autoState = 0;
    public boolean autoZero(){
      return true;
    }


    public boolean twoRocket(boolean side){
        autoCount++;
        if(side == true){
          DriveEnc = DT.leftEnc();
          // System.out.println("LEFT SIDE");
        }else{
          DriveEnc = -DT.rightEnc();
          //System.out.println("RIGHT SIDE");
        }
        if(autoCount%10 == 1){
          System.out.println(autoState);
        }
        switch(autoState){
          case 0://Yeet
           armRotate(50,0);
           EL.ElevatorMove(0,false);
            DT.driveGyro(.8,side? 1 : -1,true);
            if(DriveEnc < -5000 * AutoMult){//Flip to greater than
              autoState = 1;
              // System.out.println(autoState);
              autoCount = 0;
            }
            break;
          case 1://drive towards rocket flip arm
            armRotate(-2562,0);
            ArmEnd = -2562;
            DT.ScoreOn();
            EL.ElevatorMove(0,false);
            
            DT.driveGyro(.85,side ? 30 : -30, true);
            if(DriveEnc < -10000 * AutoMult){
              autoState = 102;
              // System.out.println(autoState);
              autoCount = 0;
            }
            break;
          case 102:
            armRotate(-2562, 0);
            ArmEnd = -2562;
            DT.ScoreOn();
            EL.ElevatorMove(3, false);
            // System.out.println("Left Enc" + DriveEnc);

            DT.driveGyro(.55, side ? 30 : -30, true);
            if(DriveEnc < (side?-16000 * AutoMult :-14450 * AutoMult)){//Flip to greater than
              autoState = 2;
              // System.out.println(autoState);
              autoCount = 0;
            }
            break;
          case 2:   //allign with rocket
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(3,false);
            EleEnd = 3;
            DT.driveGyro(.2,side? -21 : 24, true);
            // System.out.println("Left Enc" + DriveEnc);

            if(autoCount > 20){
              autoState = 3;//break here for far side line up
              // System.out.println(autoState);
              autoCount = 0;
            }
            break;
          case 3:   //Lime toward rocket
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(3,false);
            EleEnd = 3;
            DT.driveLime(-.3,-2562);//////////////////////////restore to .3
            // System.out.println("3");
            // System.out.println("Left Enc" + DriveEnc);

            if(DT.llShootShort() > 95){
              // DT.ResetEnc();
              autoState = -1;//break here for hatch one
              // System.out.println(autoState);
              autoCount = 0;
            }
            break;
          case 4:    //pullout
            AR.Hatch(true);
            armRotate(-2562,0); 
            ArmEnd = -2562;
            EL.ElevatorMove(3,false);
            EleEnd = 3;
            DT.driveGyro(.6,side? -24 : 37, true);
            // System.out.println("4");
            // System.out.println(DriveEnc);
            // -14495

            if(AutoCount == 40 ){//DriveEnc <= -15500
              DT.ResetEnc();
              autoState = 5;
              // System.out.println(autoState);

              autoCount = 0;
            }
            break;
          case 5:   //turn and go towards player station
            AR.Hatch(true);
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveGyro(-.6,0, true);
            // System.out.println(DriveEnc);

            if(DriveEnc > 3700 * AutoMult){
              autoState = 6;
              // System.out.println(autoState);

              autoCount = 0;
              //DT.gyroReset();
            }
            break;
          case 6:   //lime towards player station fast
            AR.Hatch(true);
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveLime(-.8, -2562);
            // System.out.println(DriveEnc);

            if(DriveEnc > 9550 * AutoMult){
              autoState = 7;
              // System.out.println(autoState);

              autoCount = 0;
              //DT.gyroReset();
            }
            break;
          case 7:    
            AR.Hatch(true);
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveGyro(-.6,side ? -17 : 0, true);
            // System.out.println(DriveEnc);

            if(DriveEnc > 10800 * AutoMult){
              autoState = 8;
              // System.out.println(autoState);

              autoCount = 0;
              //DT.gyroReset();
            }
            break;
          case 8:   //Lime towards player stations slower
            AR.HatchDetect();
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveGyro(-.15, 0, true);
            // System.out.println(DT.llShootShort());

            if(DT.llShootShort() > 98){
              autoState = 9;
              // System.out.println(autoState);

              autoCount = 0;
              //DT.gyroReset();
              DT.ResetEnc();
            }
            break;
          case 9:
            AR.Hatch(false);
            ArmEnd = -2562;
            armRotate(-2562,0);
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveGyro(.450,side? -15 : 15,true);
            // System.out.println("9");
            if(DriveEnc < -1000 * AutoMult){
              autoState = 10;
              // System.out.println(autoState);

              autoCount = 0;
            }
            break;
          case 10: // 
            AR.Hatch(false);
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveGyro(.8,side ? -7 : 7,true);
            // System.out.println("10");

            if(DriveEnc < -10000 * AutoMult){
              autoState = 11;
              autoCount = 0;
            }
            break;
          case 11:
            AR.Hatch(false);
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveGyro(.65,side ? 10 : -10, true);
            // System.out.println("11");

            if(DriveEnc < -13950 * AutoMult){
              autoState = 12;
              autoCount = 0;
            }
            break;
          case 12:
            AR.Hatch(false);
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveGyro(.35,side? -20:20, true);
            // System.out.println("12");

            if(autoCount == 15 ){
              autoState = 13;
              autoCount = 0;
            }
            break;
          case 13:
            AR.Hatch(false);
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.driveLime(-.3,-2562);
            // System.out.println("13");

            if(DT.llShootShort() > 90){
              autoState = 14;
              autoCount = 0;
              // DT.gyroReset();
              DT.ResetEnc();
            }
            break;
          case 14:
            AR.Hatch(true);
            armRotate(-2562,0);
            ArmEnd = -2562;
            EL.ElevatorMove(0,false);
            EleEnd = 0;
            DT.drivePercentArcade(.8,0);
            // System.out.println("14");

            if(autoCount == 100){
              autoState = 21;
              autoCount = 0;
              DT.gyroReset();
              DT.ResetEnc();
            }
            case 21:
              AR.Hatch(true);
              DT.drivePercentArcade(0 ,0);
              if(autoCount == 25){
                isFinished = true;
              }
            break;
            case -1:
            DT.drivePercentArcade(0, 0);
            break;
        }
        if(autoState != -1 || isFinished == false){
          return false;
        }else{
          return true;
        }
      } 





      boolean CargoRocket(boolean side){
        if(side == true){
          DriveEnc = DT.leftEnc();
        }else{
          DriveEnc = DT.rightEnc();
        }
        autoCount++;
        if(autoCount%10 == 1){
          System.out.println(autoState);
        }
        switch (autoState) {
          case 0://Yeet
          armRotate(50,0);
          EL.ElevatorMove(0,false);
           DT.driveGyro(.8,side? 1 : -1,true);
           if(DriveEnc < -5000 * AutoMult){//Flip to greater than
             autoState = 1;
             autoCount = 0;
           }
            break;
           case 1:
           DT.driveGyro(.7, side? 30 : -45, true);
           armRotate(-2562,0);
           ArmEnd = -2562;
           DT.ScoreOn();
           EL.ElevatorMove(0,false);
            
           if(DriveEnc < -11500 *AutoMult){
             autoState = 2;
             autoCount = 0;
           }
           break;
           case 2:
           DT.driveGyro(.4, 0, true);
           armRotate(-2562, 0);
           if(DriveEnc < -14500 * AutoMult){
             autoState = 3;
             autoCount = 0; 
           }
           break;
           case 3:
           DT.driveGyro(0, side? 90 : -90, true);
           armRotate(-2562, 0);
           if(autoCount >= 60 && (side? (DT.gyroValue() > 87 && DT.gyroValue() < 93): (DT.gyroValue() < -87 && DT.gyroValue() > -93))){
             autoState = 4;
             autoCount = 0;
           }
           break;
           case 4:
           DT.driveLime(-.25, 0);
           if(DT.llShootShort() > 90){
            DT.ResetEnc();
            autoState = -5;
            autoCount = 0; 
           }
           break;
           case 5:
           AR.Hatch(true);
           DT. driveGyro(-.5, side? 90: -90, true);
           if(DriveEnc > 1000 * AutoMult){
            autoState = 6;
            autoCount = 0;
          }
          break;
          case 6:
          AR.Hatch(true);
          DT.driveGyro(.75, side? -12:12, true);
          if(DriveEnc > 10000){
            autoState = -1;
            autoCount = 0;
          }
           case -1:
           DT.drivePercentArcade(0, 0);
           break;
        }
        if(autoState != -1){
          return false;
        }else{
          return true;
        }
      }

      public int ArmOut(){return ArmEnd;}
      public double EleOut(){return EleEnd;}
}
