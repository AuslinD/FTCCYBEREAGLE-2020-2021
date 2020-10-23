package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutoMethods extends LinearOpMode {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    public void MoveInch(double speed){
        fl.setPower(speed);
        bl.setPower(speed);
        fr.setPower(-speed);
        br.setPower(-speed);
    }
    





    public void MoveInchEncoder(double speed, double ticks){
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double ticksDis = ticks;
        while(bl.getCurrentPosition() < ticks){

            ticksDis -= bl.getCurrentPosition();
            speed = 1-(1/Math.sqrt(ticksDis));
            if(speed > 1){
                speed = 1;
            }
            bl.setPower(speed);
            fl.setPower(speed);
            br.setPower(-speed);
            fr.setPower(-speed);


        }


    }
    public void StrafeRight(double speed,double ticks ){
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double ticksDis = ticks;
        while(bl.getCurrentPosition() < ticks){

            ticksDis -= bl.getCurrentPosition();
            speed = 1-(1/Math.sqrt(ticksDis));
            if(speed > 1){
                speed = 1;
            }
            bl.setPower(speed);
            fl.setPower(-speed);
            br.setPower(speed);
            fr.setPower(-speed);


        }


    }





    public void ready() {
        while (!isStopRequested()){
            fl = hardwareMap.dcMotor.get("fl");
            fr = hardwareMap.dcMotor.get("fr");
            bl = hardwareMap.dcMotor.get("bl");
            br = hardwareMap.dcMotor.get("br");
        }
    }


    public void runOpMode() throws InterruptedException {

    }
}


 public  void ChangeState(String nextState)
    {
        // nextatse is moveforward1
        // allstates index 0 moveforward2
        // alstate idex 1 mo
        allStates.add(nextState);
        // index 0 of allstate = moveForward
        for (int i = 0; i < allStates.size(); i++)
        {
            if (nextState == allStates.get(i))
            {
                //telemetry.addData ("staterepeats", nextState);
            }
            else if (i > allStates.size() - 1)
            {
                state = nextState;
            }
        }
        ///// There will be more stuff here once we progress..

    }


}

