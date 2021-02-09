package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="redPowerShot", group="Auto")
public class RedPowerShot extends MasterClass {
    public void Init()
    {

    }
    @Override
    public void runOpMode() {
        waitForStart();
        Initialize();

        sm.ChangeState("strafe_left");

        while (opModeIsActive() && !isStopRequested()) {

            telemetry.addData("state ", sm.state);
            telemetry.update();


            if (sm.state == "strafe_left") {
                autoMethods.StrafeRight(.7, 2000);
                sm.ChangeState("forward_until_white");
            }
            else if (sm.state == "forward_until_white"/* && sm.stateTime.milliseconds() > 300*/) {
                navigationMethods.forwardUntil(null, "white", .2);
                sm.ChangeState("back_from_white");
            }
            else if (sm.state == "back_from_white" && sm.stateTime.milliseconds() > 300) {
                autoMethods.MoveInchEncoder(-.3, 350);
                sm.ChangeState("strafe_right_to_shoot");
            }
            else if (sm.state == "strafe_right_to_shoot" && sm.stateTime.milliseconds() > 300) {
                autoMethods.StrafeRight(-.3, 1300);
                sm.ChangeState("correct_self_middle");
            }
            else if(sm.state == "correct_self_middle" && sm.stateTime.milliseconds() > 300 )
            {
                autoMethods.turnPD(0,.2);
                sm.ChangeState("shoot1");
            }
            else if (sm.state == "shoot1" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("correct_self_right");
            }
            else if (sm.state == "correct_self_right" && sm.stateTime.milliseconds() > 300) {
                autoMethods.turnPD(8,.15);
                sm.ChangeState("shoot2");
            }
            else if (sm.state == "shoot2" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("shoot3");
            }
            else if (sm.state == "shoot3" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("correct_self_left");
            }
            else if (sm.state == "correct_self_left" && sm.stateTime.milliseconds() > 300) {
                autoMethods.turnPD(-8,.1);
                sm.ChangeState("shoot4");
            }
            else if (sm.state == "shoot4" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("shoot5");
            }
            else if (sm.state == "shoot5" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
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



    }

}
