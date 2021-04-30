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
    DcMotor WobbleFlipper;
    Servo push;
    //DcMotor wFlip;
    Servo wClamp;
    int dir = 1;
    int targetPos = 500;
    boolean clamped = false;
    boolean active = false;
    boolean hasFlipped = false;
    boolean aActive = false;
    boolean xActive = false;
    boolean wClamped = false;
    boolean wFlipped = false;
    public boolean pushReset = false;
    public int curTargetComp = 0;
    boolean rBumper = false;
    String xState ="";
    public float upCount = 0;
    boolean firstFrame = true;
    ElapsedTime wobbleTimer = new ElapsedTime();
    ElapsedTime clampTime = new ElapsedTime();
    ElapsedTime flipTime = new ElapsedTime();
    ElapsedTime pushTime = new ElapsedTime();
    ElapsedTime wobbleTime = new ElapsedTime();
    ElapsedTime dPadWait = new ElapsedTime();
    AutoMethods autoMethods = new AutoMethods();
    StateMachine stateMachine = new StateMachine();
    boolean finishedState = false;
    int nextPush = 0;
    boolean exceeds = false;
    boolean useEncoder = true;


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
        WobbleFlipper = hardwareMap.dcMotor.get("WobbleFlipper");
        clamp1 = hardwareMap.servo.get("c1");
        clamp2 = hardwareMap.servo.get("c2");
        wClamp = hardwareMap.servo.get("w1");
      //  wFlip = hardwareMap.dcMotor.get("wf1");
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        comp1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        comp2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WobbleFlipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        WobbleFlipper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flipper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flipper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        comp2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        comp1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        clamp1.setPosition(.85);
        clamp2.setPosition(.75);
        wClamp.setPosition(.1);
    }

    public void ClampWobble()
    {
        if (wClamped == true) {
            wClamp.setPosition(.7);

            wClamped = false; // it is not clamped
        } else
        {
            wClamp.setPosition(.1);
            wClamped = true;
        }
    }

    public void moveWobble(double power, int targetEncoder, int timeout) {
        if (power < 0) {
            if (WobbleFlipper.getCurrentPosition() < targetEncoder - 5) {
                WobbleFlipper.setPower(.05);
            } else if (WobbleFlipper.getCurrentPosition() > targetEncoder) {
                WobbleFlipper.setPower(power);
            } else {
                WobbleFlipper.setPower(0);

            }
        } else if (power > 0) {
            if (WobbleFlipper.getCurrentPosition() > targetEncoder + 5) {
                WobbleFlipper.setPower(-.05);
            } else if (WobbleFlipper.getCurrentPosition() < targetEncoder) {
                WobbleFlipper.setPower(power);
            } else {
                WobbleFlipper.setPower(0);

            }

        }
        else
        {
            WobbleFlipper.setPower(0);
        }
    }

    public void ShootY(boolean tele)
    {
        if (!tele) {
            while ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 < curTargetComp) {
                pushReset = true;
                comp1.setPower(.55);
                comp2.setPower(-.55);
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

            curTargetComp += 4000;
            pushReset = false;
            comp1.setPower(0);
            comp2.setPower(0);
        }
        else
        {

                if ((Math.abs(comp1.getCurrentPosition()) + Math.abs(comp2.getCurrentPosition())) / 2 < curTargetComp) {

                    pushReset = true;
                    moveFlipper(-.15, -50, 0);
                    comp1.setPower(.45 + upCount);
                    comp2.setPower(-.45 - upCount);
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

    public void moveFlipper(double power, int targetEncoder, int timeout) {
        if (power < 0) {
            if (flipper.getCurrentPosition() < targetEncoder - 5) {
                flipper.setPower(.05);
            } else if (flipper.getCurrentPosition() > targetEncoder) {
                flipper.setPower(power);
            } else {
                flipper.setPower(0);

            }
        } else if (power > 0) {
            if (flipper.getCurrentPosition() > targetEncoder + 5) {
                flipper.setPower(-.05);
            } else if (flipper.getCurrentPosition() < targetEncoder) {
                flipper.setPower(power);
            } else {
                flipper.setPower(0);

            }
        }
    }
    public void ClampA()
    {
        if (!clamped)
        {
            // clamping
            clamped = true;
            clamp1.setPosition(.65);
            clamp2.setPosition(1);
        }
        else
        {
            // unclamping
            clamped = false;
            clamp1.setPosition(.85);
            clamp2.setPosition(.75);
        }
        finishedState = true;
    }
    @Override
    public void loop() {
        if(Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_x) > 0.1|| Math.abs(gamepad1.right_stick_x) > 0.1){

                double lx = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
                double ly = gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y);
                double rx = gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x);
                double FLP = ly - lx - rx;
                double FRP = -ly - lx - rx;
                double BLP = ly + lx - rx;
                double BRP = -ly + lx - rx;

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

        if ((gamepad2.a && clampTime.milliseconds() > 500) || aActive) {
            if (!gamepad2.x) {
                if (firstFrame) {
                    clampTime.reset();
                    aActive = true;
                    firstFrame = false;
                }
                if (clampTime.milliseconds() < 500) {
                    push.setPosition(.28);
                    moveFlipper(-.2, -2000, 0);
                } else if (clampTime.milliseconds() < 1000) {
                    if (!clamped)
                        ClampA();
                } else if (clampTime.milliseconds() < 1750) {
                    moveFlipper(.6, 2600, 0);
                } else if (clampTime.milliseconds() < 2000) {
                    push.setPosition(.2);
                    if (clamped)
                        ClampA();
                } else if (clampTime.milliseconds() < 2250) {
                    aActive = false;
                    firstFrame = true;
                }
            }
            else
            {
                aActive = false;
                firstFrame = true;
            }
        }
        else if(gamepad2.b)
        {
            push.setPosition(.28);
            moveFlipper(-.15, -5000, 0);
        }
        else
        {
            moveFlipper(.3, 1000, 0);
            if(clamped)
                ClampA();
        }

        if ((gamepad2.y || pushReset))
        {
           // if (gamepad2.left_stick_y > .01)
          //  {
            //    ShootY(true, 500);
          //  }
           // else {
                ShootY(true);
           // }
        }
        moveWobble(0,1000,5);
        if (gamepad2.left_bumper && wobbleTime.milliseconds() > 500)
        {
            wobbleTime.reset();
            ClampWobble();
        }
        if (gamepad2.right_trigger > .1 && !exceeds)
        {
                moveWobble(-.5, -1000, 5);
        }
        else if (gamepad2.left_trigger > .1 && !exceeds)
        {
            moveWobble(.5, 1000, 5);
        }
        if (WobbleFlipper.getCurrentPosition() < -390 && useEncoder)
        {
            moveWobble(.3, 1000, 5);
            telemetry.addData("aqui < -390", WobbleFlipper.getCurrentPosition());
            exceeds = true;
        }
        else    {
            exceeds = false;
        }
        if (WobbleFlipper.getCurrentPosition() > 5 && useEncoder)
        {
            telemetry.addData("aqui > 90", WobbleFlipper.getCurrentPosition());
            moveWobble(-.3, -1000, 5);
            exceeds = true;
        }
        else{
            exceeds = false;
        }
        if (gamepad2.right_bumper)
        {
            push.setPosition(.2);
        }
        if(gamepad2.dpad_up && dPadWait.milliseconds() > 500)
        {
            dPadWait.reset();
            upCount += .02;
            if (upCount > .54f)
            {
                upCount = .54f;
            }
        }
        if(gamepad2.dpad_down && dPadWait.milliseconds() > 500)
        {
            dPadWait.reset();
            upCount -= .015;
            if (upCount < -.44f)
            {
                upCount = -.44f;
            }
        }
        telemetry.addData("upCount ", upCount);
        if (gamepad2.left_stick_y < -.5)
        {
            useEncoder = false;
        }
        else
        {
            useEncoder = true;
        }



         /*if(gamepad2.x || xPressed)
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
        telemetry.addData("pusher", push.getPosition());
        telemetry.addData("wobble arm", WobbleFlipper.getCurrentPosition());
        telemetry.addData("flipper", flipper.getCurrentPosition());
        telemetry.update();





    }
}
