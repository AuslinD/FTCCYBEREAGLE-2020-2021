package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.android.dx.dex.code.DalvCode;


@TeleOp(name="MecTeleOp", group = "TeleOp")
public class MecTeleOp extends OpMode {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    Servo clamp1;
    Servo clamp2;
    DcMotor flipper;
    Servo push;
    int dir = 1;
    int pushDir = 1;
    boolean clamped;
    boolean pushReset = false;
    ElapsedTime clampTime = new ElapsedTime();
    ElapsedTime flipTime = new ElapsedTime();
    ElapsedTime pushTime = new ElapsedTime();

    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        clamp1 = hardwareMap.servo.get("c1");
        clamp1 = hardwareMap.servo.get("c2");
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {
        if(Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_x) > 0.1|| Math.abs(gamepad1.right_stick_x) > 0.1){
                double FLP = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
                double FRP = -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
                double BLP = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
                double BRP = -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;

                double max = Math.max(Math.max(FLP, FRP), Math.max(BLP, BRP));

                if(max > 1) {
                FLP /= max;
                FRP /= max;
                BLP /= max;
                BRP /= max;
            }

            if(gamepad1.right_trigger > 0.1){
                fl.setPower(FLP * 0.35);
                fr.setPower(FRP * 0.35);
                bl.setPower(BLP * 0.35);
                br.setPower(BRP * 0.35);
            }
            else {
                fl.setPower(FLP);
                fr.setPower(FRP);
                br.setPower(BRP);
                bl.setPower(BLP);
            }

        }
        else{
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);


        }

        if (gamepad2.a && clampTime.milliseconds() > 500)
        {
            if (clamped)
            {
                clamped = false;
                clamp1.setPosition(1);
                clamp2.setPosition(1);
            }
            else
            {
                clamped = true;
                clamp1.setPosition(0);
                clamp2.setPosition(0);
            }
            clampTime.reset();
        }

        if ((gamepad2.b && flipTime.milliseconds() > 500))
        {
            if (flipper.getCurrentPosition() < 500) {
                flipper.setPower(.2 * dir);
            }
            dir = -dir;
        }
        if (gamepad2.y && pushReset)
        {
            push.setPosition(1 * pushDir);
            while (push)
            pushDir = 0;

        }


    }
}
