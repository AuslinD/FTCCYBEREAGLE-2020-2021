package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous
        (name = "Test", group = "test")
public class Test extends MasterClass {
    public void runOpMode() {
        Initialize();
        waitForStart();

        while (!isStopRequested() && opModeIsActive()){

            telemetry.addData("", navigationMethods.readColor());
            telemetry.addData("red", navigationMethods.colorVals[0]);
            telemetry.addData("green", navigationMethods.colorVals[1]);
            telemetry.addData("blue", navigationMethods.colorVals[2]);
            telemetry.update();
        }
    }
}
