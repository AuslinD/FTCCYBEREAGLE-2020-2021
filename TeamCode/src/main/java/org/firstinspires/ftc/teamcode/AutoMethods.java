package org.firstinspires.ftc.teamcode;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

import java.util.*;

public class AutoMethods {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    Servo clamp1;
    Servo clamp2;
    DcMotor comp1;
    DcMotor comp2;
    DcMotor flipper;
    DcMotor WobbleFlipper;
    Servo wClamp;
    Servo push;
    public boolean pushReset = false;
    public int curTargetComp = 2500;
    int test = 0;
    public ElapsedTime time = new ElapsedTime();
    MasterClass masterClass = null;
    MecTeleOp mecTeleOp = null;
    Orientation angles;
    public BNO055IMU imu;
    double curDiff = 0;
    ElapsedTime wTime = new ElapsedTime();
    ElapsedTime turnTime = new ElapsedTime();
    ElapsedTime flipperTime = new ElapsedTime();
    ElapsedTime speedTime = new ElapsedTime();
    double maxGy = 0;



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
        while (Math.abs(bl.getCurrentPosition()) < ticks) {

          //  ticksDis -= bl.getCurrentPosition();
          //  speed = 1 - (1 / Math.sqrt(ticksDis));
            if (speed > 1) {
                speed = 1;
            }
            bl.setPower(speed);
            fl.setPower(speed);
            br.setPower(-speed);
            fr.setPower(-speed);
            masterClass.telemetry.addData("bl", bl.getCurrentPosition());
            masterClass.telemetry.update();


        }
            bl.setPower(0);
            fl.setPower(0);
            br.setPower(0);
            fr.setPower(0);

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

    public void ShootY(boolean tele, double power)
    {
        if (!tele) {
            while ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 < curTargetComp) {
                pushReset = true;
                comp1.setPower(power);
                comp2.setPower(-power);
                if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 > curTargetComp - 1000) {
                    push.setPosition(.45);
                }
                if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 > curTargetComp - 550) {
                    push.setPosition(.1);
                }
                if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 > curTargetComp - 100) {
                    push.setPosition(.1);
                }
            }


            curTargetComp += 2500;
            pushReset = false;
            comp1.setPower(0);
            comp2.setPower(0);
        }
        else
        {
            if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 < curTargetComp) {
                pushReset = true;
                comp1.setPower(1);
                comp2.setPower(-1);
                if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 > curTargetComp - 1000) {
                    push.setPosition(.45);
                }
                if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 > curTargetComp - 550) {
                    push.setPosition(.2);
                }
                if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 > curTargetComp - 100) {
                    push.setPosition(.28);
                }
            }
            else {

                curTargetComp += 2500;
                pushReset = false;
                comp1.setPower(0);
                comp2.setPower(0);
            }
        }
    }

    public float ReturnMotorStrength()
    {
        comp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        speedTime.reset();
        while (speedTime.milliseconds() < 1000) {
            comp2.setPower(.55);
        }
        float spinRate = ((comp2.getCurrentPosition()) - 650) * .005f;
        float grossPower = .6f * spinRate + .3f;
        float truePower = .9f - (grossPower - .3f);
        masterClass.telemetry.addData("motor values", (comp2.getCurrentPosition()));
        masterClass.telemetry.addData("gross power", grossPower);
        return truePower;
        // .9 = 1600, .3 = 8000
    }

    public void SetPush(float position)
    {
        push.setPosition(position);
    }

    public void flipperZero(float speed)
    {
        while (flipper.getCurrentPosition() > -240)
        {
            flipper.setPower(speed);
        }
        flipper.setPower(0);
    }

    public void flipperUp(float speed, float timeout)
    {
        flipperTime.reset();
        while (flipper.getCurrentPosition() < 100 && flipperTime.milliseconds() < timeout)
        {
            flipper.setPower(speed);
        }
        flipper.setPower(0);
    }

    public void ColorMove(String desiredColor, float speed, boolean careSaturation)
    {
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (!careSaturation) {
            while (masterClass.navigationMethods.readColor()[1] != desiredColor) {
                fl.setPower(speed);
                fr.setPower(speed);
                bl.setPower(speed);
                br.setPower(speed);
            }
        }
        if (careSaturation) {
            while (masterClass.navigationMethods.readColor()[0] + masterClass.navigationMethods.readColor()[1] != desiredColor) {
                fl.setPower(speed);
                fr.setPower(speed);
                bl.setPower(speed);
                br.setPower(speed);
            }
        }
    }

    public void turnPD(int tarDegree, double speed)
    {
        double startDiff = tarDegree - getGyroYaw();
        double p = 0;
        curDiff = startDiff;
        double plusMin = 0;
        while(Math.abs(curDiff) > 1)
        {
            curDiff = tarDegree - getGyroYaw();
            double trueDiff = tarDegree - getGyroYaw();
            int calc = Math.abs((int)(tarDegree - curDiff));
            p = Math.abs(curDiff)/Math.abs(startDiff);
            p = p * Math.signum(trueDiff);
            plusMin = .1 * Math.signum(trueDiff);
            bl.setPower(speed * p + plusMin);
            fl.setPower(speed * p + plusMin);
            br.setPower(speed * p + plusMin);
            fr.setPower(speed * p + plusMin);
            masterClass.telemetry.addData("getGyrowyaw", getGyroYaw());
            masterClass.telemetry.addData("curdiff", curDiff);
            masterClass.telemetry.addData("startd", startDiff);
            masterClass.telemetry.update();
        }
        bl.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        fr.setPower(0);
    }

    public void turnPDT(int tarDegree, double speed, double timeout)
    {
        double startDiff = tarDegree - getGyroYaw();
        double p = 0;
        curDiff = startDiff;
        double plusMin = 0;
        turnTime.reset();
        while(Math.abs(curDiff) > 1 && turnTime.milliseconds() < timeout)
        {
            curDiff = tarDegree - getGyroYaw();
            double trueDiff = tarDegree - getGyroYaw();
            int calc = Math.abs((int)(tarDegree - curDiff));
            p = Math.abs(curDiff)/Math.abs(startDiff);
            p = p * Math.signum(trueDiff);
            plusMin = .1 * Math.signum(trueDiff);
            bl.setPower(speed * p + plusMin);
            fl.setPower(speed * p + plusMin);
            br.setPower(speed * p + plusMin);
            fr.setPower(speed * p + plusMin);
            masterClass.telemetry.addData("getGyrowyaw", getGyroYaw());
            masterClass.telemetry.addData("curdiff", curDiff);
            masterClass.telemetry.addData("startd", startDiff);
            masterClass.telemetry.update();
        }
        bl.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        fr.setPower(0);
    }

    public double getGyroYaw() {
        updateGyroValues();
        return angles.firstAngle;
    }

    public void updateGyroValues() {
        angles = imu.getAngularOrientation();
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
        while(Math.abs(bl.getCurrentPosition()) < ticks){

            //ticksDis -= bl.getCurrentPosition();
            //speed = 1-(1/Math.sqrt(ticksDis));
            if(speed > 1){
                speed = 1;
            }
            bl.setPower(speed);
            fl.setPower(-speed);
            br.setPower(speed);
            fr.setPower(-speed);
            //masterClass.telemetry.addData("bl", bl.getCurrentPosition());
        }
        bl.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        fr.setPower(0);
    }

    public void Strafe(double speed, double orientation, double val){// positive speed is right
        double gyro = (getGyroYaw() - orientation) * val;
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // positive is right

        if(speed > 1){
            speed = 1;
        }
        bl.setPower(speed);
        fl.setPower(-speed - gyro);
        br.setPower(speed);
        fr.setPower(-speed - gyro);

    }

    public void ready(MasterClass master) {
        masterClass = master;
        //mecTeleOp = mecTeleOpM;
        fl = masterClass.hardwareMap.dcMotor.get("fl");
        fr = masterClass.hardwareMap.dcMotor.get("fr");
        bl = masterClass.hardwareMap.dcMotor.get("bl");
        br = masterClass.hardwareMap.dcMotor.get("br");
        comp1 = masterClass.hardwareMap.dcMotor.get("comp1");
        comp2 = masterClass.hardwareMap.dcMotor.get("comp2");
        flipper = masterClass.hardwareMap.dcMotor.get("flipper");
        push = masterClass.hardwareMap.servo.get("push");
        clamp1 = masterClass.hardwareMap.servo.get("c1");
        clamp2 = masterClass.hardwareMap.servo.get("c2");
        wClamp = masterClass.hardwareMap.servo.get("w1");
        WobbleFlipper = masterClass.hardwareMap.dcMotor.get("WobbleFlipper");
        //  wFlip = hardwareMap.dcMotor.get("wf1");
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        WobbleFlipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        comp1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        comp2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //    wFlip.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WobbleFlipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //   wFlip.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        WobbleFlipper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // wFlip.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        clamp1.setPosition(.7);
        clamp2.setPosition(.8);
        wClamp.setPosition(.1);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";

        imu = masterClass.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
       // wClamp.setPosition(.1);


    }
    public void setWobbleGoal(float position) {
        wClamp.setPosition(position);
    }
    public void moveWobble(double power, int targetEncoder, int timeout) {
        WobbleFlipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WobbleFlipper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wTime.reset();
        if (power < 0) {
            while (WobbleFlipper.getCurrentPosition() > targetEncoder && wTime.milliseconds() < timeout) {
                if (WobbleFlipper.getCurrentPosition() < targetEncoder) {
                    WobbleFlipper.setPower(.05);
                } else if (WobbleFlipper.getCurrentPosition() > targetEncoder) {
                    WobbleFlipper.setPower(power);
                } else {
                    WobbleFlipper.setPower(0);

                }
                masterClass.telemetry.addData("", WobbleFlipper.getCurrentPosition());
                masterClass.telemetry.update();
            }
        } else if (power > 0) {
            while (WobbleFlipper.getCurrentPosition() < targetEncoder && wTime.milliseconds() < timeout) {
                if (WobbleFlipper.getCurrentPosition() > targetEncoder) {
                    WobbleFlipper.setPower(-.05);
                } else if (WobbleFlipper.getCurrentPosition() < targetEncoder) {
                    WobbleFlipper.setPower(power);
                } else {
                    WobbleFlipper.setPower(0);

                }
                masterClass.telemetry.addData("", WobbleFlipper.getCurrentPosition());
                masterClass.telemetry.update();
            }
        }
    }

}