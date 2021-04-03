package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
        (name = "Test", group = "test")
//test
public class Test extends MasterClass {
    public void runOpMode() {
        Initialize();
        waitForStart();
        ElapsedTime time = new ElapsedTime();
        double nextTime = 0;

        while (!isStopRequested() && opModeIsActive()){

            telemetry.addData("", navigationMethods.readColor());
            telemetry.addData("index 0", navigationMethods.hsvVals[0]);
            telemetry.addData("index 0", navigationMethods.hsvVals[1]);
            telemetry.addData("index 0", navigationMethods.hsvVals[2]);
            if (time.milliseconds() > 500)
            {
                time.reset();
                nextTime = navigationMethods.ReturnDist();
            }
            telemetry.addData("distance", nextTime);
            telemetry.update();
        }
    }
}
