package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "RedWobble", group = "Auto")
@Disabled
public class RedAutoWobbleGoalV1 extends MasterClass{
    @Override
    public void runOpMode() {
        waitForStart();

        char targetZone;

        while (!isStopRequested() && opModeIsActive()){
            Initialize();
            sm.ChangeState("MoveInch");
            if(sm.state == "MoveInch") {
                autoMethods.MoveInchEncoder(1, 1380);//put ticks in
                sm.ChangeState("scan");

            }

            if(sm.state == "scan"){
                //put scan thing here
                sm.ChangeState("StrafeLeft");
            }


            targetZone = 'A'; //delete this delete this delete this delete this delete this delete this delete this delete this


            if(sm.state == "StrafeRight") {

                autoMethods.StrafeRight(1, 1239);//change this!
                    }

                    //pick up the wobble goal


                    switch(targetZone) {
                        case 'A':
                            telemetry.addData("targetZone", "A");
                            telemetry.update();

                            sm.ChangeState("MoveInch");

                            if(sm.state == "MoveInch") {
                                autoMethods.MoveInchEncoder(1, 1000);
                                sm.ChangeState("DropWobble");

                    }

                    if(sm.state == "DropWobble") {
                        //drop wobble goal

                    }



                    break;
                case 'B':
                    telemetry.addData("targetZone", "B");
                    telemetry.update();
                    sm.ChangeState("MoveInch");
                    if(sm.state == "MoveInch") {
                        autoMethods.MoveInchEncoder(1, 1500);
                        sm.ChangeState("DropWobble");
                    }

                    if(sm.state == "DropWobble") {
                        //drop wobble goal
                        sm.ChangeState("MoveInch");
                    }
                    if(sm.state == "MoveInch") {
                        autoMethods.MoveInchEncoder(-1, -256);
                    }
                    break;
                case 'C':
                    telemetry.addData("targetZone", "C");
                    telemetry.update();
                    sm.ChangeState("MoveInch");
                    if(sm.state == "MoveInch") {
                        autoMethods.MoveInchEncoder(1, 2000);
                        sm.ChangeState("DropWobble");
                    }
                    if(sm.state == "DropWobble") {
                        //drop wobble goal
                        sm.ChangeState("MoveInch");
                    }
                    if(sm.state == "MoveInch") {
                        autoMethods.MoveInchEncoder(-1, -512);
                    }
                    break;



            }


        }

    }
}
