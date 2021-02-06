package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.vuforia.CameraDevice;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;
import java.util.Collections;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;


public class NavigationMethods {
    ColorSensor colorSensor;
    DistanceSensor distanceSensor;
    public int[] colorVals = new int[3];
    public float[] hsvVals = new float[3];
    MasterClass masterClass = null;
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
            if (hsvVals[1] <.25 && hsvVals[2] > .23)
            {
                hue = "white";
            }

        if (hsvVals[2] < .3)
        {
            hue = "black";
        }
        masterClass.telemetry.addData("hue", hue);
        masterClass.telemetry.addData("saturation", saturation);
        masterClass.telemetry.addData("true sat", hsvVals[1]);
        masterClass.telemetry.addData("true val", hsvVals[2]);
        masterClass.telemetry.update();
        String colors[] = new String[2];
        colors[0] = saturation;
        colors[1] = hue;
        return colors;
    }

    public void forwardUntil(String saturation, String hue, double speed)
    {
        boolean finished = false;
        while(!finished) {
            if (hue != null) {
                if (saturation == null) {
                    if (readColor()[1] == hue) {
                        finished = true;
                    }
                }
                else
                {
                    if (readColor()[1] == hue && readColor()[0] == saturation ) {
                        finished = true;
                    }
                }
            }
            else if (saturation != null)
            {
                if (readColor()[0] == saturation) {
                    finished = true;
                }
            }
                masterClass.autoMethods.MoveInch(speed);
        }
    }
    public double ReturnDist()
    {
        return distanceSensor.getDistance(DistanceUnit.CM);
        //return 0;
    }


    public void initNav(MasterClass master)
    {
        masterClass = master;
        colorSensor = masterClass.hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        distanceSensor = masterClass.hardwareMap.get(DistanceSensor.class, "sensor_color_distance");
        masterClass.telemetry.addData("hi",  colorSensor);
        masterClass.telemetry.update();
    }
}
