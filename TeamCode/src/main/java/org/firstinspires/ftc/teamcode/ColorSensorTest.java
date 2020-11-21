package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;


@Autonomous(name = "BlueAuto", group = "Auto")
public class ColorSensorTest extends LinearOpMode {
    String block;

    ColorSensor color;

    DcMotor lb;
    DcMotor lf;
    DcMotor rb;
    DcMotor rf;
    Servo push;

    public void forward(double power, double ticks){// the ticks for the left back motor (make tlelemetry and drive the distance in TeleOp)




        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (lb.getCurrentPosition() < ticks){
            power = ticks / 2800;
            if (power > 1) {
                power = 1;
            }

            lb.setPower(power);
            lf.setPower(power);
            rb.setPower(power);
            rf.setPower(power);
        }


    }
    public void strafe(double power, double ticks){// the ticks for the left back wheel
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (lb.getCurrentPosition() < ticks){
            power = ticks / 2800;
            if (power > 1){
                power = 1;
            }
            rf.setPower(-power);
            rb.setPower(power);
            lf.setPower(power);
            lb.setPower(-power);
        }

    }

    @Override
    public void runOpMode() throws InterruptedException {


        lb = hardwareMap.dcMotor.get("lb");
        lf = hardwareMap.dcMotor.get("lf");
        rb = hardwareMap.dcMotor.get("rb");
        rf = hardwareMap.dcMotor.get("rf");
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        color = hardwareMap.get(ColorSensor.class, "colorsensor");
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        double step = 1;
        String pattern = "unknown";



        waitForStart();
        telemetry.addData("Red", color.red());
        telemetry.addData("Green", color.green());
        telemetry.addData("Blue", color.blue());
        telemetry.addData("Hue", hsvValues[0]);
        telemetry.addData("Block Type", block);
        telemetry.addData("step", step);
        telemetry.addData("pattern", pattern );
        telemetry.update();




        forward(1, 8400);//ticks is the distance, find it using telemetry and driving the distance in TeleOp. USE THE LEFT BACK WHEEL AS THE EXAMPLE
        step += 1;

        telemetry.update();

        if (hsvValues[0] > 300 || hsvValues[0] < 10){
            block = "red ball";
        }
        else if(hsvValues[0] > 30 && hsvValues[0] < 65){
            block = "yellow stone";
        }
        else if (hsvValues[0] > 190 && hsvValues[0] < 210){
            block = "blue ball";
        }
        else {
            block = "unknown, please check values";
        }


        if (block != "blue ball"){
            pattern = "2,4";
        }
        else {
            push.setPosition(1);
            sleep(1000);
            push.setPosition(0);

        }
        telemetry.update();

        if (pattern == "2,4"){
            strafe(.5, 1200 );//CHANGE VALUE
            push.setPosition(1);
            sleep(1000);
            push.setPosition(0);

            strafe(1, 2400);

            push.setPosition(1);
            sleep(1000);
            push.setPosition(0);
            telemetry.update();
            forward(-1, -8400);
            strafe(1, 11160);
        }

        else{
            strafe(1, 2400);
            if (hsvValues[0] > 300 || hsvValues[0] < 10){
                block = "red ball";
            }
            else if(hsvValues[0] > 30 && hsvValues[0] < 65){
                block = "yellow stone";
            }
            else if (hsvValues[0] > 190 && hsvValues[0] < 210){
                block = "blue ball";
            }
            else {
                block = "unknown, please check values";
            }

            if(block == "blue ball"){
                pattern = "3,5";
                push.setPosition(1);
                sleep(1000);
                push.setPosition(0);
                telemetry.update();
                forward(-1, -8400);
                strafe(1, 9960);
            }

            else {
                pattern = "2,5";
                strafe(.5, 1200);

                push.setPosition(1);
                sleep(1000);
                push.setPosition(0);

                forward(-1, -8400);
                telemetry.update();
                strafe(1, 11160);

            }


        }

    }
}
