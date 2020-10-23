package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class AutoWobbleGoalV1 extends AutoMethods {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        ready();


        waitForStart();

        char targetZone;

        while (!isStopRequested() && opModeIsActive()){
            MoveInchEncoder(1, 1380);//put ticks in
            telemetry.addData("Running MoveInchEncoder", "complete");
            telemetry.update();
            sleep(1000);

            targetZone = 'A'; //delete this delete this delete this delete this delete this delete this delete this delete this

            //Put scan thing here

            StrafeRight(1,1239);//change this!

            //pick up the wobble goal


            switch(targetZone) {
                case 'A':
                    telemetry.addData("targetZone", "A");
                    telemetry.update();
                    MoveInchEncoder(1,1000);
                    sleep(2000);
                    //drop wobble goal


                    break;
                case 'B':
                    telemetry.addData("targetZone", "B");
                    telemetry.update();
                    MoveInchEncoder(1,1500);
                    sleep(3000);
                    //drop wobble goal
                    MoveInchEncoder(-1, -256);
                    break;
                case 'C':
                    telemetry.addData("targetZone", "C");
                    telemetry.update();
                    MoveInchEncoder(1,2000);
                    sleep(3500);
                    //drop wobble goal
                    MoveInchEncoder(-1, -512);
                    break;



            }


        }
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

    }
}
