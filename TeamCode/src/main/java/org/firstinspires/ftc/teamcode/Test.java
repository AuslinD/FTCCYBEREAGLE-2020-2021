package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous
        (name = "Test", group = "test")
public class Test extends MasterClass {
    NavigationMethods navigationMethods = new NavigationMethods();
    public void runOpMode() {
        while (true) {
            Initialize();
            telemetry.addData("", navigationMethods.ReturnDist());
            telemetry.update();
        }
    }
}
