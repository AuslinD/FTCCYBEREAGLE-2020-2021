package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.BatteryChecker;

public abstract class MasterClass extends LinearOpMode {
    AutoMethods autoMethods = null;
    NavigationMethods navigationMethods = null;
    StateMachine sm = null;
    Vision vision = null;


    public void Initialize()
    {

        autoMethods = new AutoMethods();
        navigationMethods = new NavigationMethods();
        vision = new Vision();
        sm = new StateMachine();
        //mecTeleOp = new MecTeleOp();

        vision.initVision(this);
        navigationMethods.initNav(this);
        autoMethods.ready(this);

    }

}
