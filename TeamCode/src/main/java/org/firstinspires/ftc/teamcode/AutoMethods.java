package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

import java.util.*;

public class AutoMethods {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    DcMotor C1;
    DcMotor C2;
    public ElapsedTime time = new ElapsedTime();



    public void MoveInch(double speed){
        fl.setPower(speed);
        bl.setPower(speed);
        fr.setPower(-speed);
        br.setPower(-speed);
    }


    public void MoveInchEncoder(double speed, double ticks) {
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double ticksDis = ticks;
        while (bl.getCurrentPosition() < ticks) {

            ticksDis -= bl.getCurrentPosition();
            speed = 1 - (1 / Math.sqrt(ticksDis));
            if (speed > 1) {
                speed = 1;
            }
            bl.setPower(speed);
            fl.setPower(speed);
            br.setPower(-speed);
            fr.setPower(-speed);


        }

    }

    public void GyroStable(double speed, double ticks) {
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double ticksDis = ticks;
    }

        public void SetDiffPower(float leftFrontPower, float rightFrontPower, float leftBackPower, float rightBackPower){
            fl.setPower((leftBackPower));
        }

    public void ShootDisk(double distance, double height, float seconds) {
        time.reset();
        double gravity = -10;
        double power = 0;
        power = (-20.0f + height - gravity*(distance*distance))/distance;
        if (time.milliseconds() > 1000*seconds)
        {
            C1.setPower(power);
            C2.setPower(power);
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

    public void ready(MasterClass masterclass) {
            fl = masterclass.hardwareMap.dcMotor.get("fl");
            fr = masterclass.hardwareMap.dcMotor.get("fr");
            bl = masterclass.hardwareMap.dcMotor.get("bl");
            br = masterclass.hardwareMap.dcMotor.get("br");

    }

}