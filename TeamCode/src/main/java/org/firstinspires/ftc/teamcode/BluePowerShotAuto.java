package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Powershot", group = "Auto")
public class BluePowerShotAuto extends MasterClass {

    @Override
    public void runOpMode() throws InterruptedException {

        while (!isStopRequested() && opModeIsActive()){
            Initialize();
            StateMachine sm = new StateMachine();
            sm.ChangeState("StrafeRight");
            if(sm.state == "StrafeRight") {
                autoMethods.StrafeRight(1, 2132);
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
