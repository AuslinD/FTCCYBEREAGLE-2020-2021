package org.firstinspires.ftc.teamcode;

import android.os.Debug;

import com.vuforia.CameraDevice;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;

import java.util.ArrayList;
import java.util.Collections;

import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static android.graphics.Color.blue;

public class Vision{

    CameraDevice camera;
    public VuforiaLocalizer vuforia;
    WebcamName webcam;
    MasterClass masterClass = null;
    int xStreak = 0;
    boolean[] red;
    boolean reachMax;
    int firstRed;
    int lastRed;
    int avgs;
    int numAvgs = 0;
    int totalAvgs;
    int totalNumAvgs = 0;
    boolean finished = false;
    boolean ignoreRow = false;
    int timesThrough = 0;
    int exceptionStreak;
    boolean exception;
    int thisAvg = 0;
    public int totalAvg = 0;
    float interval = 501;
    android.graphics.Bitmap bitmap;
    boolean first = true;
    int num = 0;
    int oStreakX = 0;
    int oStreakY = 0;
    float avgY = 0;
    int numAvgY = 0;
    int xs = 0;
    int possibleX = 0;
    int firstY = -1;
    int lastY = 0;
    int absentY = 0;
    float totalAvgY = 0;


    public android.graphics.Bitmap getBitmap() throws InterruptedException {

        vuforia.setFrameQueueCapacity(10);
        VuforiaLocalizer.CloseableFrame picture;
        picture = vuforia.getFrameQueue().take();

        Image rgb;

        long numImages = picture.getNumImages();



            int format = picture.getImage(0).getFormat();
           // if (format == 4) {
                rgb = picture.getImage(0);
            masterClass.sleep(10);
            // else {
               // masterClass.telemetry.addLine("Didn't find correct RGB format");


          //  }



        android.graphics.Bitmap imageBitmap = android.graphics.Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), android.graphics.Bitmap.Config.RGB_565);
        imageBitmap.copyPixelsFromBuffer(rgb.getPixels());




        //masterClass.sleep(500);

        picture.close();

        return imageBitmap;
    }


    public int getPix(int x, int y) throws InterruptedException
    {
        android.graphics.Bitmap bitmap = getBitmap();
        return bitmap.getPixel(x,y);
    }

    public int TestWeb() throws InterruptedException
    {
        return red(getPix(0,0));
    }

    public void StrafeRightVision(String goal) throws InterruptedException{
        double speed = CalcMiddle(goal);
        while (Math.abs(speed) >.23) {
            speed = CalcMiddle(goal);
            if (speed < -.5)
            {
                speed = -.5;
            }
            if (speed > .5)
            {
                speed = .5;
            }
            masterClass.autoMethods.Strafe(speed);
        }
        masterClass.autoMethods.Strafe(0);
    }

    public double CalcLeftRight(int x, int grace, String goal) throws InterruptedException
    {
        double power = 0;
        int width = bitmap.getWidth();
        if(goal == "left") {
            power = (((width/1.3193)) - x) / 300;
            if (x == 0)
            {
                power = -.07;
            }
            if(power < 0)
            {
                return power - .2;
            }
            else
            {
                return power+.2;
            }
        }
        if(goal == "middle") {
            power = ((width/1.4193) - x) / 300;
            if (x == 0)
            {
                power = -.07;
            }
            if(power < 0)
            {
                return power - .2;
            }
            else
            {
                return power +.2;
            }
        }
        if(goal == "right") {
            power = ((width/1.4993) - x) / 300;
            if (x == 0)
            {
                power = -.07;
            }
            if(power < 0)
            {
                return power - .2;
            }
            else
            {
                return power+.2;
            }
        }
        if (goal == "tower") {
            power = ((width/4) - x) / 300;
            if (x == 0)
            {
                power = -.07;
            }
            if(power < 0)
            {
                return power - .2;
            }
            else
            {
                return power+.2;
            }
        }
        else{
            return 0;
    }



    }

    public double CalcMiddle(String goal) throws InterruptedException
    {
        bitmap = getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float rThreshold = 97;
        float bThreshold = 97;
        float gThreshold = 97;
        int test = 1;
        totalAvg = 0;
        totalNumAvgs = 0;
        totalAvgs = 0;
        avgs = 0;
        numAvgs = 0;
        timesThrough = 0;
        xStreak = 0;
        int xStep = 2;
        int yStep = 2;
        float fWidth = (float)width;
        float fStep = (float)xStep;
        int eStreak = (int)(.146484375 * fWidth / fStep);
        int lStreak = (int)(.00732421875 * fWidth / fStep);
        int rStreak = (int)(.01708984375 * fWidth / fStep);


        for (int yc = height/2 ; yc < height; yc+=yStep)
        {
            int y = height - yc;
            interval = 0;

            for (int x = 0; x < width; x+=xStep)
            {

                //masterClass.telemetry.addData("test", test);
                //masterClass.telemetry.update();
                test += 1;

                // is red
                int pixel = bitmap.getPixel(x,y);

                if (red(pixel) > rThreshold && green(pixel) < gThreshold && blue(pixel) < bThreshold)
                {
                    if (xStreak == 0)
                    {
                        firstRed = x;
                        reachMax = false;
                    }
                    xStreak += 1;
                    exceptionStreak += 1;

                    if (xStreak > rStreak)
                    {
                        xStreak = 35;
                        reachMax = true;
                        lastRed = x;
                    }
                }

                // is not red

                else
                {
                    xStreak -= 1;
                    if (xStreak < 0)
                    {
                        xStreak = 0;
                        reachMax = false;
                    }
                }

                // ending

                if (reachMax && (xStreak < lStreak || x == width-1))
                {
                    if (exceptionStreak > eStreak)
                    {
                        timesThrough = 2;
                    }
                    timesThrough += 1;
                    avgs += firstRed;
                    avgs += lastRed;
                    numAvgs += 2;
                    xStreak = 0;
                    reachMax = false;
                }
            }

            if (timesThrough <= 1)
            {
                ignoreRow = true;
                avgs = 0;
                numAvgs = 0;
            }
            else
            {
                ignoreRow = false;
            }
            xStreak = 0;
            if (numAvgs > 0 && !ignoreRow)
            {
                totalAvgs += avgs / numAvgs;
                totalNumAvgs += 1;
                numAvgs = 0;
                avgs = 0;
            }
            avgs = 0;
            exceptionStreak = 0;
            timesThrough = 0;
        }

        if (totalNumAvgs > 0)
           totalAvg = totalAvgs / totalNumAvgs;

        int retVal = totalAvg;
        masterClass.telemetry.addData("x ", totalAvg);
        masterClass.telemetry.update();

        return CalcLeftRight(totalAvg,10, goal);

    }

    public int returnDisks() throws InterruptedException {
        bitmap = getBitmap();
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int minNumY = (int)(.01953125 * h);
        int fRing = (int)(.02153125 * h);
        int oRing = (int)(.00651041666 * h);
        int oS = (int)(.00146484375 * w);
        //if (interval > 500)
        {
            interval = 0;

            float rThreshold = 153.0f;
            float gMin = 81.6f;
            float bMin = 63.75f;

            int yStart = h / 4;
            int yEnd = 3 * h / 4;
            int xStart = w / 4;
            int xEnd = 3 * w / 4;

            yStart = h/2;
            yEnd = h;
            xStart = 0;
            xEnd = w;

            for (int y = yStart; y < yEnd; y++)
            {
                for (int x = xStart; x < xEnd; x++)
                {
                    int pixel = bitmap.getPixel(x,y);
                    float red = red(pixel);
                    float grn = green(pixel);
                    float blu = blue(pixel);
                    if (red > rThreshold && grn > gMin && blu < bMin)
                    {
                        oStreakX += 1;
                    }
                    if (x == w/2 && y == h/2) {
                        masterClass.telemetry.addData("red=", red);
                        masterClass.telemetry.addData("grn=", grn);
                        masterClass.telemetry.addData("blu=", blu);
                    }

                }
                if (oStreakX > oS)
                {
                    absentY += 1;
                    numAvgY += 1;
                    avgY += y;
                    if (firstY == -1)
                    {
                        firstY = y;
                    }
                    lastY = y;
                }
                else
                {
                    if (absentY > 0)
                    {
                        absentY -= 1;
                    }
                }
                oStreakX = 0;
                if (numAvgY > minNumY && absentY == 0)
                {
                    break;
                }
            }

            masterClass.telemetry.addData("avgy", numAvgY);

            if (numAvgY != 0)
            {
                totalAvgY = avgY / numAvgY;
            }

            if (totalAvgY - firstY > fRing)
            {
                masterClass.telemetry.addData("ta", totalAvgY);
                masterClass.telemetry.addData("fy", firstY);
                masterClass.telemetry.addData("4", totalAvgY-firstY);
                masterClass.telemetry.update();
                first = false;
                firstY = -1;
                avgY = 0;
                numAvgY = 0;
                oStreakX = 0;
                absentY = 0;
                totalAvgY = 0;
                return 4;
            }
            else if (totalAvgY - firstY > oRing)
            {
                masterClass.telemetry.addData("ta", totalAvgY);
                masterClass.telemetry.addData("fy", firstY);
                masterClass.telemetry.addData("1", totalAvgY-firstY);
                masterClass.telemetry.update();
                first = false;
                firstY = -1;
                avgY = 0;
                numAvgY = 0;
                oStreakX = 0;
                absentY = 0;
                totalAvgY = 0;
                return 1;
            }
            else
            {
                masterClass.telemetry.addData("ta", totalAvgY);
                masterClass.telemetry.addData("fy", firstY);
                masterClass.telemetry.addData("0", totalAvgY-firstY);
                masterClass.telemetry.update();
                first = false;
                firstY = -1;
                avgY = 0;
                numAvgY = 0;
                oStreakX = 0;
                absentY = 0;
                totalAvgY = 0;
                return 0;
            }
        }
    }

    public void initVision(MasterClass mClass)
    {

        masterClass = mClass;

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        String VUFORIA_KEY =
                "AQswYz7/////AAABmcNWVtOSYEEXpQufuBKrdNMSKO5UAESlpwf1GyWOEzZMytfVdfY2BsxJop+3JkhqYQEby7j5SJHbcw6kSDuMe40rGeec5vJtb9m+qxy8jqy8EuBZ8n9IAldRtolwfIBkMI+d9+EkoqSBiZwhSWDzT0EVw83o3H+WzzMmj91dURhqRNzdHjEz0lUUgwDNrfNuW3oGPn1A1alADdHYnnAo++SiO9m4hHPVkdomVSxNjxu3I6whv16zWlQTLdK97POf2t37U+rS/2hZ5GSNG054PtWDppXH+ec8XNrDfys6+OmeG/m6MFvNjoUAyUgV7bsqMM+QUM3eTI3/FENR6PZ3VND47T3Dm74Hxkor++lEZHEi";

        webcam = masterClass.hardwareMap.get(WebcamName.class, "Webcam 1");
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        int cameraMonitorViewId = masterClass.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", masterClass.hardwareMap.appContext.getPackageName());
        parameters.cameraName = webcam;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        vuforia.enableConvertFrameToBitmap();
        masterClass.idle();
    }

}
