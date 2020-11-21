package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class MasterClass extends LinearOpMode {
    AutoMethods autoMethods = new AutoMethods();

    public void Initialize()
    {
        autoMethods.ready(this);
    }
    public void runOpMode(){

    }

}
