package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedPowerShotAuto extends AutoMethods {
    @Override
    public void runOpMode() throws InterruptedException {
        ready();

        while (!isStopRequested() && opModeIsActive()){
            StateMachine sm = new StateMachine();
            sm.ChangeState("StrafeRight",1000);
            if(sm.state == "StrafeRight"){
                StrafeRight(-1, 1213);
                sm.ChangeState("MoveInch",1000);
            }


            if(sm.state == "MoveInch"){
                MoveInchEncoder(1, 1234);
                sm.ChangeState("ShootDisc", 1000);
            }

            if(sm.state == "ShootDisc") {
                // shoot the powershot
                sm.ChangeState("MoveInch", 1000);
            }
            if(sm.state == "MoveInch"){
                MoveInchEncoder(1, 100);

            }

        }

    }
}
