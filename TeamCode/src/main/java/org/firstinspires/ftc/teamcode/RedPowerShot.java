package org.firstinspires.ftc.teamcode;

import android.app.ApplicationErrorReport;
import android.os.BatteryManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.BatteryChecker;

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
                if(numofF > 2)
                {
                    numDisks = 4;
                    sm.ChangeState("forward_until_white");
                }
                if(numofO > 2)
                {
                    numDisks = 1;
                    sm.ChangeState("forward_until_white");
                }
                if(numofZ > 2)
                {
                    numDisks = 0;
                    sm.ChangeState("forward_until_white");
                }
            }
            else if (sm.state == "forward_until_white"/* && sm.stateTime.milliseconds() > 300*/) {
                autoMethods.MoveInchEncoder(.8,1750);
                navigationMethods.forwardUntil(null, "white", .15);
                autoMethods.MoveInchEncoder(-.4,150);
                sm.ChangeState("strafe_right_to_goal");
            }

            else if (sm.state == "strafe_right_to_goal" && sm.stateTime.milliseconds() > 300) {
                    autoMethods.turnPDT(0, .2, 1000);
                    vision.StrafeRightVision("tower");//0.55
                    autoMethods.ShootY(false, .5);
                    autoMethods.ShootY(false, .5);
                    autoMethods.ShootY(false, .5);
                    autoMethods.ShootY(false, .5);
                if (numDisks != 4) {
                    autoMethods.turnPDT(-90, .5, 2000);
                }
                sm.ChangeState("decide_path");
            }
            else if (sm.state == "decide_path" && sm.stateTime.milliseconds() > 300) {
                if (numDisks == 0)
                {
                    autoMethods.MoveInchEncoder(1, 950);
                    autoMethods.moveWobble(.4, 257, 500);
                    autoMethods.setWobbleGoal(.7f);
                    autoMethods.MoveInchEncoder(-.5, 150);
                    sm.ChangeState("set_flipper");
                }
                else if(numDisks == 1)
                {
                    autoMethods.MoveInchEncoder(.7, 150);
                    autoMethods.StrafeRight(.4, 800);
                    autoMethods.moveWobble(.4, 1000, 1000);
                    autoMethods.setWobbleGoal(.7f);
                    autoMethods.StrafeRight(-.4, 700);
                    sm.ChangeState("set_flipper");
                }
                else if(numDisks == 4)
                {
                    autoMethods.MoveInchEncoder(.8, 2700);
                    autoMethods.flipperUp(.4f, 1000);
                    autoMethods.turnPDT(-180, .65, 3000);
                    autoMethods.StrafeRight(.6, 500);
                    autoMethods.moveWobble(.4, 1000, 500);
                    autoMethods.setWobbleGoal(.7f);
                    autoMethods.StrafeRight(-.6, 500);

                    autoMethods.MoveInchEncoder(.8, 2300);
                    sm.ChangeState("set_flipper");
                }
            }

            else if(sm.state == "correct_self_middle" && sm.stateTime.milliseconds() > 300 )
            {
                autoMethods.turnPDT(0,.40, 3000);
                vision.StrafeRightVision("tower");
                autoMethods.turnPDT(0, .15, 1000);
                if (numDisks == 4)
                {
                    autoMethods.StrafeRight(.4, 775);
                }
             //   autoMethods.turnPD(0,.2);
                sm.ChangeState("shoot1");
            }
            else if (sm.state == "shoot1" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false, .55);
                autoMethods.ShootY(false, .55);
                autoMethods.ShootY(false, .55);
                autoMethods.ShootY(false, .55);
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
