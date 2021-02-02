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
    CameraDevice camera;
    VuforiaLocalizer vuforia;

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

    public android.graphics.Bitmap getBitmap() throws InterruptedException {

        VuforiaLocalizer.CloseableFrame picture;
        picture = vuforia.getFrameQueue().take();
        Image rgb = picture.getImage(1);

        long numImages = picture.getNumImages();

        masterClass.telemetry.addData("Num images", numImages);

        for (int i = 0; i < numImages; i++) {

            int format = picture.getImage(i).getFormat();
            if (format == PIXEL_FORMAT.RGB565) {
                rgb = picture.getImage(i);
                break;
            } else {
                masterClass.telemetry.addLine("Didn't find correct RGB format");


            }
        }

        android.graphics.Bitmap imageBitmap = android.graphics.Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), android.graphics.Bitmap.Config.RGB_565);
        imageBitmap.copyPixelsFromBuffer(rgb.getPixels());

        masterClass.telemetry.addData("Image width", imageBitmap.getWidth());
        masterClass.telemetry.addData("Image height", imageBitmap.getHeight());


        masterClass.sleep(500);

        picture.close();

        return imageBitmap;
    }

    public int getHeight() throws InterruptedException{
        android.graphics.Bitmap bitmap = getBitmap();
        return bitmap.getHeight();
    }
    public int getWidth() throws InterruptedException{
        android.graphics.Bitmap bitmap = getBitmap();
        return bitmap.getWidth();
    }

    public int getPix(int x, int y) throws InterruptedException
    {
        android.graphics.Bitmap bitmap = getBitmap();
        return bitmap.getPixel(x,y);
    }

    public int ReturnDisks() throws InterruptedException {
        int xMax = getHeight();
        int yMax = getWidth();
        int orangeX = 0;
        int otherX = 0;
        int orangeY = 0;
        int x = 0;
        int y = 0;
        int numDisks = 0;
        boolean endLoop = false;
        while (!endLoop) {
            for (x = 0; x < xMax; x += 4) {
                if (red(getPix(x, y)) > 230 && green(getPix(x, y)) > 100 && green(getPix(x, y)) < 240) {
                    orangeX += 1;
                } else {
                    otherX += 1;
                }
            }
            y += 2;
            if (orangeX / otherX > .33) {
                orangeY += 1;
            }
            if (y > yMax - 3) {
                endLoop = true;
            }
            if (orangeY > 50) {
                numDisks = 2;
            } else if (orangeY > 10) {
                numDisks = 1;
            } else {
                numDisks = 0;
            }
        }
        return numDisks;
    }

    /*public int AimBotDir() throws InterruptedException
    {
        int xMax = getWidth();
        int yMax = getHeight();
        int y = 0;
        for (int x = 0; x < xMax; x++)
        {
            if (red(getPix(x,y)) > 220 || (red(getPix(x,y)) > green(getPix(x,y)) + blue(getPix(x,y)) && red(getPix(x,y)) > 160))
        }
    }
    */

    public void initNav(MasterClass master)
    {
        masterClass = master;
        colorSensor = masterClass.hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        distanceSensor = masterClass.hardwareMap.get(DistanceSensor.class, "sensor_color_distance");
        masterClass.telemetry.addData("hi",  colorSensor);
        masterClass.telemetry.update();
    }
}
