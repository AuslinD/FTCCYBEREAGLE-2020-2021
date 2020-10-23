package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class RedPowerShotAuto extends AutoMethods {
    @Override
    public void runOpMode() throws InterruptedException {
        ready();

        while (!isStopRequested() && opModeIsActive()){
            StrafeRight(-1, 1213);
            sleep(1000);
            MoveInchEncoder(1, 1234);
            sleep(1000);
            // shoot the powershot
            MoveInchEncoder(1,100);
        }

    }
}
