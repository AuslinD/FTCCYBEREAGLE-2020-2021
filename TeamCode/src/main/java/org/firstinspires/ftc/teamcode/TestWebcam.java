package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="TestWebcam", group="Auto")
public class TestWebcam extends MasterClass {
    public void Init()
    {

    }
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Initialize();

        sm.ChangeState("webcam_Test");

        while (opModeIsActive() && !isStopRequested()) {
            telemetry.addData("before", 0);
           // vision.TestWeb();
            telemetry.addData("after", 0);
            telemetry.update();
        }



    }

}
