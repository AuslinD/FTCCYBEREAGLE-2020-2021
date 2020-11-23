package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class NavigationMethods {
    ColorSensor colorSensor;
    DistanceSensor distanceSensor;
    public int[] colorVals = new int[3];
    public float[] hsvVals = new float[3];
    int blackMaxThresh = 50;
    int minHueVal = 110;
    String saturation;
    String hue;

    public String[] readColor()
    {
        colorVals[0] = colorSensor.red();
        colorVals[1] = colorSensor.green();
        colorVals[2] = colorSensor.blue();
        Color.RGBToHSV(colorVals[0], colorVals[1], colorVals[2], hsvVals);

            if (hsvVals[0] >= 330 || hsvVals[0] < 60)
            {
                if (hsvVals[1] > .5)
                {
                    saturation = "pure ";
                }
                else
                {
                    saturation = "light ";
                }
                hue = "red";
            }
            else if (hsvVals[0] >= 60 && hsvVals[0] < 140)
            {
                if (hsvVals[1] > .5)
                {
                    saturation = "pure ";
                }
                else
                {
                    saturation = "light ";
                }
                hue = "green";
            }
            else if (hsvVals[0] >= 140 && hsvVals[0] < 330)
                {
                    if (hsvVals[1] > .5)
                    {
                        saturation = "pure ";
                    }
                    else
                    {
                        saturation = "light ";
                    }
                    hue = "blue";
                }
            if (hsvVals[1] < .4)
            {
                hue = "grey";
            }

        if (hsvVals[2] < .3)
        {
            hue = "black";
        }
        String colors[] = new String[2];
        colors[0] = saturation;
        colors[1] = hue;
        return colors;
    }
    public double ReturnDist()
    {
        return distanceSensor.getDistance(DistanceUnit.CM);
        //return 0;
    }
    public void initNav(MasterClass masterClass)
    {
        colorSensor = masterClass.hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        distanceSensor = masterClass.hardwareMap.get(DistanceSensor.class, "sensor_color_distance");
        masterClass.telemetry.addData("hi",  colorSensor);
        masterClass.telemetry.update();
    }
}
