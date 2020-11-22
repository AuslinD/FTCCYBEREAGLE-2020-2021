package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

<<<<<<< Updated upstream
@Autonomous
public class RedPowerShotAuto extends MasterClass {
=======
<<<<<<< HEAD
@Autonomous(name="")
public class RedPowerShotAuto extends AutoMethods {
=======
@Autonomous
public class RedPowerShotAuto extends MasterClass {
>>>>>>> bb9089a57986500d9ceaa7617709360eb84ae231
>>>>>>> Stashed changes
    @Override
    public void runOpMode() throws InterruptedException {


        while (!isStopRequested() && opModeIsActive()){
            Initialize();
            StateMachine sm = new StateMachine();
            sm.ChangeState("StrafeRight");
            if(sm.state == "StrafeLeft") {
                autoMethods.StrafeRight(-1, 2132);
                sm.ChangeState("MoveInch");
            }
            if(sm.state == "MoveInch") {
                autoMethods.MoveInchEncoder(1, 1231);
                sm.ChangeState("Shootdisc");
            }
            if(sm.state == "Shootdisc") {
                //shoot the disc things
                sm.ChangeState("MoveInch");
            }
            if(sm.state == "MoveInch") {
                autoMethods.MoveInchEncoder(1, 100);
            }

        }
    }
}
