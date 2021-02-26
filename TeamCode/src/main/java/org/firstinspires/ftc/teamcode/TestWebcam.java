package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="TestWebcam", group="Auto")
public class TestWebcam extends MasterClass {
    Orientation angles;
    public BNO055IMU imu;

    public void Init()
    {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
    public double getGyroYaw() {
        updateGyroValues();
        return angles.firstAngle;
    }

    public void updateGyroValues() {
        angles = imu.getAngularOrientation();
    }
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Initialize();
        Init();

        sm.ChangeState("webcam_Test");


        while (opModeIsActive() && !isStopRequested()) {
            if(sm.state == "webcam_Test") {
                vision.CalcMiddle();
            }

            telemetry.update();
        }



    }

}
