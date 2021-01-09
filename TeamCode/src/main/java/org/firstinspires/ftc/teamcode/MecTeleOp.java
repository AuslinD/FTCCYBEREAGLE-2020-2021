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
    DcMotor comp1;
    DcMotor comp2;
    DcMotor flipper;
    Servo push;
    int curTargetComp = 0;
    int dir = 1;
    int targetPos = 500;
    boolean clamped = false;
    boolean active = false;
    boolean hasFlipped = false;
    boolean pushReset = false;
    boolean xPressed = false;
    String xState ="";
    ElapsedTime clampTime = new ElapsedTime();
    ElapsedTime flipTime = new ElapsedTime();
    ElapsedTime pushTime = new ElapsedTime();
    ElapsedTime xTime = new ElapsedTime();
    AutoMethods autoMethods = new AutoMethods();
    StateMachine stateMachine = new StateMachine();
    boolean finishedState = false;


    @Override
    public void init() {
        stateMachine.state = "";
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        comp1 = hardwareMap.dcMotor.get("comp1");
        comp2 = hardwareMap.dcMotor.get("comp2");
        flipper = hardwareMap.dcMotor.get("flipper");
        push = hardwareMap.servo.get("push");
        clamp1 = hardwareMap.servo.get("c1");
        clamp2 = hardwareMap.servo.get("c2");
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        clamp1.setPosition(.4);
        clamp2.setPosition(.6);
    }

    public void ShootY()
    {
        if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition()))/2 < curTargetComp + 6000) {
            pushReset = true;
            push.setPosition(.5);
            comp1.setPower(1);
            comp2.setPower(-1);
        }
        else
        {
            curTargetComp += 6000;
            pushReset = false;
            push.setPosition(.2;
            comp1.setPower(0);
            comp2.setPower(0);
            finishedState = true;
        }
        finishedState = false;
    }
    public void FlipB()
    {
        if (flipper.getCurrentPosition() < 220 && !hasFlipped) {
            active = true;
            flipper.setPower(.2);
        }
        else if (flipper.getCurrentPosition() >= 220 && !hasFlipped)
        {
            flipper.setPower(0);
            flipTime.reset();
            active = false;
            hasFlipped = true;
            finishedState = true;
        }
        else if (flipper.getCurrentPosition() > 0 && hasFlipped)
        {
            active = true;
            flipper.setPower(-.2);
        }
        else if(flipper.getCurrentPosition() < 0 && hasFlipped)
        {
            flipper.setPower(0);
            flipTime.reset();
            active = false;
            hasFlipped = false;
            finishedState = true;
        }
        finishedState = false;
    }

    public void ClampA()
    {
        if (!clamped)
        {
            clamped = true;
            clamp1.setPosition(.2);
            clamp2.setPosition(.65);
        }
        else
        {
            clamped = false;
            clamp1.setPosition(.4);
            clamp2.setPosition(.45);
        }
        clampTime.reset();
        finishedState = true;
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
            ClampA();
        }

        if ((gamepad2.b && flipTime.milliseconds() > 500) || active)
        {
            FlipB();
        }
        if ((gamepad2.y || pushReset))
        {
            ShootY();
        }

        /*if (gamepad2.x || xPressed)
        {
            xPressed = true;
            if (stateMachine.state == "") {
                ClampA();
                if (finishedState) {
                    stateMachine.ChangeState("flip");
                    finishedState = false;
                }
            }
            if (stateMachine.state == "flip" && stateMachine.stateTime.milliseconds() > 500)
            {
                FlipB();
                if (finishedState)
                {
                    stateMachine.ChangeState("release");
                    finishedState = false;
                }
            }
            if (stateMachine.state == "release" && stateMachine.stateTime.milliseconds() > 500)
            {
                ClampA();
                if (finishedState)
                {
                    stateMachine.ChangeState("pushNShoot");
                    finishedState = false;
                }
            }
            if (stateMachine.state == "pushNShoot" && stateMachine.stateTime.milliseconds() > 500)
            {
                ShootY();
                if (finishedState)
                {
                    stateMachine.ChangeState("");
                    xPressed=false;
                    finishedState = false;
                }
            }
        }
        */
        telemetry.addData("comp1", comp1.getCurrentPosition());
        telemetry.addData("comp2", comp2.getCurrentPosition());
        telemetry.addData("flipper", flipper.getCurrentPosition());
        telemetry.update();





    }
}
