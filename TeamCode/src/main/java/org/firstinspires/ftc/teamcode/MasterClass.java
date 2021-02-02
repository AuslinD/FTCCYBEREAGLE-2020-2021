package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class MasterClass extends LinearOpMode {
    AutoMethods autoMethods = null;
    NavigationMethods navigationMethods = null;
    StateMachine sm = null;
    //MecTeleOp mecTeleOp = null;


    public void Initialize()
    {
        autoMethods = new AutoMethods();
        navigationMethods = new NavigationMethods();
        sm = new StateMachine();
        //mecTeleOp = new MecTeleOp();

        navigationMethods.initNav(this);
        autoMethods.ready(this);
    }

}
