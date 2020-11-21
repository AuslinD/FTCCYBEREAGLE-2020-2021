package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class MasterClass extends LinearOpMode {
    AutoMethods autoMethods = new AutoMethods();
    NavigationMethods navigationMethods = new NavigationMethods();

    public void Initialize()
    {
        navigationMethods.initNav(this);
        autoMethods.ready(this);
    }

}
