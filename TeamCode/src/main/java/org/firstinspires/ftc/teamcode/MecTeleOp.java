package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

<<<<<<< Updated upstream:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/TeleOp.java
=======
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecTeleOp.java
@TeleOp
public class MecTeleOp extends OpMode {
=======
>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecTeleOp.java
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
>>>>>>> bb9089a57986500d9ceaa7617709360eb84ae231:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/TeleOp.java
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");

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
<<<<<<< Updated upstream:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/TeleOp.java
=======
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecTeleOp.java
        else{
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
=======
>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecTeleOp.java
        else
        {
            fl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
            bl.setPower(0);
<<<<<<< Updated upstream:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/TeleOp.java
=======
>>>>>>> bb9089a57986500d9ceaa7617709360eb84ae231:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/TeleOp.java
>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/MecTeleOp.java
        }

    }
}
