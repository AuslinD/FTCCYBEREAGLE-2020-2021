package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="redPowerShot", group="Auto")
public class RedPowerShot extends MasterClass {
    int numDisks = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        Initialize();
        waitForStart();

        sm.ChangeState("locate_disks");

        while (opModeIsActive() && !isStopRequested()) {

            telemetry.addData("state ", sm.state);
            telemetry.update();


            if (sm.state == "locate_disks") {
                numDisks = vision.returnDisks();
                sm.ChangeState("forward_until_white");
            }
            else if (sm.state == "forward_until_white"/* && sm.stateTime.milliseconds() > 300*/) {
                autoMethods.MoveInchEncoder(.5,1000);
                navigationMethods.forwardUntil(null, "white", .2);
                sm.ChangeState("strafe_right_to_goal");
            }
            else if (sm.state == "strafe_right_to_goal" && sm.stateTime.milliseconds() > 300) {
                autoMethods.turnPDT(-90, .4, 2000);
                autoMethods.MoveInchEncoder(.7, 900);
                sm.ChangeState("decide_path");
            }
            else if (sm.state == "decide_path" && sm.stateTime.milliseconds() > 300) {
                if (numDisks == 0)
                {
                    autoMethods.turnPDT(-180, .7, 3500);
                    autoMethods.moveWobble(-.4, -100, 1000);
                    autoMethods.setWobbleGoal(.7f);
                    autoMethods.moveWobble(.4, 100, 1000);
                    sm.ChangeState("correct_self_middle");
                }
                else if(numDisks == 1)
                {
                    autoMethods.moveWobble(-.4, -100, 1000);
                    autoMethods.setWobbleGoal(.7f);
                    autoMethods.moveWobble(.4, 100, 1000);
                    autoMethods.StrafeRight(-.4, 700);
                    autoMethods.turnPDT(0, .7, 2000);
                    sm.ChangeState("correct_self_middle");
                }
                else if(numDisks == 4)
                {
                    autoMethods.MoveInchEncoder(.3, 700);
                    autoMethods.StrafeRight(.4, 1500);
                    autoMethods.moveWobble(-.4, -100, 1000);
                    autoMethods.setWobbleGoal(.7f);
                    autoMethods.moveWobble(.4, 100, 1000);
                    autoMethods.MoveInchEncoder(-.3, 600);
                    autoMethods.StrafeRight(-.4, 1800);
                    autoMethods.MoveInchEncoder(-.3, 200);
                    sm.ChangeState("correct_self_middle");
                }
            }

            else if(sm.state == "correct_self_middle" && sm.stateTime.milliseconds() > 300 )
            {
                autoMethods.turnPDT(0,.35, 3000);
                autoMethods.StrafeRight(.4, 700);
                vision.StrafeRightVision("tower");
             //   autoMethods.turnPD(0,.2);
                sm.ChangeState("shoot1");
            }
            else if (sm.state == "shoot1" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                autoMethods.ShootY(false);
                autoMethods.ShootY(false);
                autoMethods.ShootY(false);
                sm.ChangeState("correct_self_right");
            }
            else if (sm.state == "forward" && sm.stateTime.milliseconds() > 300) {
                autoMethods.MoveInchEncoder(.3, 500);
                autoMethods.SetPush(.35f);
                sm.ChangeState("set_flipper");
            }
            else if (sm.state == "set_flipper" && sm.stateTime.milliseconds() > 300) {
                autoMethods.flipperZero(-.3f);
                sm.ChangeState("");
            }
        }



    }

}
