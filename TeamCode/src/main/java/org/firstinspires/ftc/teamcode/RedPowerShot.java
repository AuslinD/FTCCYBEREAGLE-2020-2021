package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="redPowerShot", group="Auto")
public class RedPowerShot extends MasterClass {
    int numDisks = 0;
    int numofF = 0;
    int numofO = 0;
    int numofZ = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        Initialize();
        waitForStart();

        sm.ChangeState("locate_disks");

        while (opModeIsActive() && !isStopRequested()) {



            if (sm.state == "locate_disks") {
                numDisks = vision.returnDisks();
                if (numDisks == 4)
                {
                    numofF += 1;
                }
                if (numDisks == 1)
                {
                    numofO += 1;
                }
                if (numDisks == 0)
                {
                    numofZ += 1;
                }
                if(numofF > 3)
                {
                    numDisks = 4;
                    sm.ChangeState("forward_until_white");
                }
                if(numofO > 3)
                {
                    numDisks = 1;
                    sm.ChangeState("forward_until_white");
                }
                if(numofZ > 3)
                {
                    numDisks = 0;
                    sm.ChangeState("forward_until_white");
                }
            }
            else if (sm.state == "forward_until_white"/* && sm.stateTime.milliseconds() > 300*/) {
                autoMethods.MoveInchEncoder(.5,1700);
                navigationMethods.forwardUntil(null, "white", .2);
                sm.ChangeState("strafe_right_to_goal");
            }
            else if (sm.state == "strafe_right_to_goal" && sm.stateTime.milliseconds() > 300) {
                autoMethods.turnPDT(-90, .4, 2000);
                autoMethods.MoveInchEncoder(.7, 750);
                sm.ChangeState("decide_path");
            }
            else if (sm.state == "decide_path" && sm.stateTime.milliseconds() > 300) {
                if (numDisks == 0)
                {
                    autoMethods.turnPDT(-180, .7, 3500);
                    autoMethods.moveWobble(-.4, -100, 1000);
                    autoMethods.setWobbleGoal(.7f);
                    autoMethods.moveWobble(.4, 100, 1000);
                    autoMethods.MoveInchEncoder(.4, 350);
                    autoMethods.turnPDT(0,.55, 3000);
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
                    autoMethods.MoveInchEncoder(.3, 600);
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
                autoMethods.turnPDT(0,.30, 3000);
                autoMethods.StrafeRight(.4, 550);
                vision.StrafeRightVision("tower");
                autoMethods.turnPDT(0, .2, 1000);
             //   autoMethods.turnPD(0,.2);
                sm.ChangeState("shoot1");
            }
            else if (sm.state == "shoot1" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false, .5);
                autoMethods.ShootY(false, .5);
                autoMethods.ShootY(false, .5);
                autoMethods.ShootY(false, .5);
                sm.ChangeState("forward");
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

        telemetry.addData("state ", sm.state);
        telemetry.update();



    }

}
