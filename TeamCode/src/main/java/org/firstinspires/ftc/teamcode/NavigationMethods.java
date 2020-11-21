package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;


public class NavigationMethods {
    ColorSensor colorSensor;
    int[] colorVals;
    int blackMaxThresh = 50;
    int minHueVal = 110;

    public String readColor()
    {
        colorVals[0] = colorSensor.red();
        colorVals[1] = colorSensor.green();
        colorVals[2] = colorSensor.blue();
        float avg = (colorVals[0] + colorVals[1] + colorVals[2])/3;
        float redDiff = colorVals[2] + colorVals[1];
        float greenDiff = colorVals[2] + colorVals[0];
        float blueDiff = colorVals[1] + colorVals[0];
        if (avg <= blackMaxThresh)
        {
            return "black";
        }
        else
        {
            if (colorVals[0] > redDiff + 15 && colorVals[0] > minHueVal)
            {
                return "red";
            }
            else if (colorVals[1] > greenDiff + 30 && colorVals[1] > minHueVal)
            {
                return "green";
            }
            else if (colorVals[2] > blueDiff + 15 && colorVals[2] > minHueVal)
            {
                return "blue";
            }
            else
            {
                return "";
            }
        }
    }
    public void initNav(MasterClass masterClass)
    {
        colorSensor = masterClass.hardwareMap.get(ColorSensor.class, "colorsensor");
    }
}
