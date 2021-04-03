package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AAAA", group="Auto")
public class CameraTest extends MasterClass {

    int i = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        vision.initVision(this);
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            telemetry.addData("", vision.returnDisks());
            telemetry.update();
            i++;
        }
    }

}
