package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Powershot", group = "Auto")
public class BluePowerShotAuto extends AutoMethods {

    @Override
    public void runOpMode() throws InterruptedException {

        ready();
        while (!isStopRequested() && opModeIsActive()){
            StateMachine sm = new StateMachine();
            sm.ChangeState("StrafeRight");
            if(sm.state == "StrafeRight") {
                StrafeRight(1, 2132);
                sm.ChangeState("MoveInch");
            }
            if(sm.state == "MoveInch") {
                MoveInchEncoder(1, 1231);
                sm.ChangeState("Shootdisc");
            }
            if(sm.state == "Shootdisc") {
                //shoot the disc things
                sm.ChangeState("MoveInch");
            }
            if(sm.state == "MoveInch") {
                MoveInchEncoder(1, 100);
            }

        }
    }
}
