package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Test_Comps", group = "Auto")
public class TestComps extends MasterClass{

    DcMotor C1;
    DcMotor C2;
    public void runOpMode(){
        waitForStart();

        C1 = hardwareMap.dcMotor.get("C1");
        C2 = hardwareMap.dcMotor.get("C2");
        while (opModeIsActive() && !isStopRequested())
        {
            C1.setPower(1);
            C2.setPower(-1);
        }
    }
}
