package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous(name = "RedAuto", group = "Auto")
public class RedAutoWobbleGoalV1 extends AutoMethods {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        ready();


        waitForStart();

        char targetZone;

        while (!isStopRequested() && opModeIsActive()){
            StateMachine sm = new StateMachine();
            sm.ChangeState("MoveInch");
            if(sm.state == "MoveInch") {
                MoveInchEncoder(1, 1380);//put ticks in
                telemetry.addData("Running MoveInchEncoder", "complete");
                telemetry.update();

                sm.ChangeState("scan");


            }

            if(sm.state == "scan"){
                //put scan thing here
                sm.ChangeState("StrafeRight", 1000);
            }


            targetZone = 'A'; //delete this delete this delete this delete this delete this delete this delete this delete this


            if(sm.state == "StrafeRight") {

                StrafeRight(1, 1239);//change this!
            }

            //pick up the wobble goal


            switch(targetZone) {
                case 'A':
                    telemetry.addData("targetZone", "A");
                    telemetry.update();
                    sm.ChangeState("MoveInch", 2000);
                    if(sm.state == "MoveInch") {
                        MoveInchEncoder(1, 1000);
                        sm.ChangeState("DropWobble", 1000);
                    }

                    if(sm.state == "DropWobble") {
                        //drop wobble goal
                    }



                    break;
                case 'B':
                    telemetry.addData("targetZone", "B");
                    telemetry.update();
                    sm.ChangeState("MoveInch", 3000);
                    if(sm.state == "MoveInch") {
                        MoveInchEncoder(1, 1500);
                        sm.ChangeState("DropWobble", 1000);
                    }

                    if(sm.state == "DropWobble") {
                        //drop wobble goal
                        sm.ChangeState("MoveInch", 1000);
                    }
                    if(sm.state == "MoveInch") {
                        MoveInchEncoder(-1, -256);
                    }
                    break;
                case 'C':
                    telemetry.addData("targetZone", "C");
                    telemetry.update();
                    sm.ChangeState("MoveInch", 3500);
                    if(sm.state == "MoveInch") {
                        MoveInchEncoder(1, 2000);
                        sm.ChangeState("DropWobble", 1000);
                    }
                    if(sm.state == "DropWobble") {
                        //drop wobble goal
                        sm.ChangeState("MoveInch", 1000);
                    }
                    if(sm.state == "MoveInch") {
                        MoveInchEncoder(-1, -512);
                    }
                    break;



            }


        }
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

    }
}
