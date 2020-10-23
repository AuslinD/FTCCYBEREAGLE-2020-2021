package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Powershot", group = "Auto")
public class BluePowerShotAuto extends AutoMethods {

    @Override
    public void runOpMode() throws InterruptedException {

        ready();
        while (!isStopRequested() && opModeIsActive()){
            StrafeRight(1,2132);
            sleep(1000);
            MoveInchEncoder(1, 1231);
            sleep(3000);

            //shoot the disc things

            MoveInchEncoder(1, 100);
            sleep(1000);

        }
    }
}
