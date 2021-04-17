package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.BatteryChecker;

public abstract class MasterClass extends LinearOpMode {
    AutoMethods autoMethods = null;
    NavigationMethods navigationMethods = null;
    StateMachine sm = null;
    Vision vision = null;
    BatteryChecker batterychecker = null;
    BatteryChecker.BatteryWatcher batterywatcher = null;


    public class MyBatteryWatcher implements BatteryChecker.BatteryWatcher{
        private Double percent = new Double(null);

        @Override
        public void updateBatteryStatus(BatteryChecker.BatteryStatus status){
            this.percent = status.percent;
        }
        public double getPower() {
            return this.percent == null ? 0.5 : this.percent.doubleValue() * -0.075 + 8;
        }
    }

    //MecTeleOp mecTeleOp = null;


    public void Initialize()
    {

        autoMethods = new AutoMethods();
        navigationMethods = new NavigationMethods();
        vision = new Vision();
        sm = new StateMachine();
        //mecTeleOp = new MecTeleOp();
        batterywatcher = new MyBatteryWatcher();
        batterychecker = new BatteryChecker(batterywatcher, 1000);

        vision.initVision(this);
        navigationMethods.initNav(this);
        autoMethods.ready(this);

    }

}
