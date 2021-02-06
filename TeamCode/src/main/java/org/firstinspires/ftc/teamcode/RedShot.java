package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="redShot", group="Auto")
public class RedShot extends MasterClass {
    public void Init()
    {

    }
    @Override
    public void runOpMode() {
        waitForStart();
        Initialize();

        sm.ChangeState("strafe_right");

        while (opModeIsActive() && !isStopRequested()) {
                if (sm.state == "strafe_right") {
                    while (true) {
                        telemetry.addData("IMU", autoMethods.getGyroYaw());
                        telemetry.update();
                    }
                }


            telemetry.addData("state ", sm.state);
            telemetry.update();


            if (sm.state == "strafe_right") {
                autoMethods.StrafeRight(-.5, 2000);
                sm.ChangeState("forward_until_white");
            }
            else if (sm.state == "forward_until_white"/* && sm.stateTime.milliseconds() > 300*/) {
                navigationMethods.forwardUntil(null, "white", .2);
                sm.ChangeState("back_from_white");
            }
            else if (sm.state == "back_from_white" && sm.stateTime.milliseconds() > 300) {
                autoMethods.MoveInchEncoder(-.3, 350);
                sm.ChangeState("strafe_left_to_shoot");
            }
            else if (sm.state == "strafe_left_to_shoot" && sm.stateTime.milliseconds() > 300) {
                autoMethods.StrafeRight(.3, 1300);
                sm.ChangeState("correctSelf");
            }
            else if(sm.state == "correctSelf" && sm.stateTime.milliseconds() > 300 )
            {
                autoMethods.turnPD(0,.2);
                sm.ChangeState("shoot1");
            }
            else if (sm.state == "shoot1" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("shoot2");
            }
            else if (sm.state == "shoot2" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("shoot3");
            }
            else if (sm.state == "shoot3" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("shoot4");
            }
            else if (sm.state == "shoot4" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("shoot5");
            }
            else if (sm.state == "shoot5" && sm.stateTime.milliseconds() > 300) {
                autoMethods.ShootY(false);
                sm.ChangeState("shoot6");
            }
            else if (sm.state == "shoot6" && sm.stateTime.milliseconds() > 300) {
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
