package org.firstinspires.ftc.teamcode;

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

public class Vision {

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
    int totalAvg = 0;
    float interval = 501;


    public android.graphics.Bitmap getBitmap() throws InterruptedException {

        vuforia.setFrameQueueCapacity(10);
        VuforiaLocalizer.CloseableFrame picture;
        picture = vuforia.getFrameQueue().take();

        Image rgb;

        long numImages = picture.getNumImages();



            int format = picture.getImage(0).getFormat();
            masterClass.telemetry.update();
           // if (format == 4) {
                rgb = picture.getImage(0);
            masterClass.sleep(10);
            // else {
               // masterClass.telemetry.addLine("Didn't find correct RGB format");


          //  }


        masterClass.telemetry.update();

        android.graphics.Bitmap imageBitmap = android.graphics.Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), android.graphics.Bitmap.Config.RGB_565);
        imageBitmap.copyPixelsFromBuffer(rgb.getPixels());




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

    public int TestWeb() throws InterruptedException
    {
        return red(getPix(0,0));
    }

    public int CalcLeftRight(int x, int grace) throws InterruptedException
    {
        if (getWidth()/2 > x + grace)
        {
            return -1;
        }
        else
        {
            return 1;
        }


    }

    public void CalcMiddle() throws InterruptedException
    {
        float rThreshold = 97;
        float bThreshold = 61;
        float gThreshold = 61;
        int test = 1;

            for (int y = getHeight() / 2; y < getHeight(); y++)
            {
                interval = 0;
                for (int x = 0; x < getWidth(); x++)
                {

                    masterClass.telemetry.addData("test", test);
                    masterClass.telemetry.update();
                    test += 1;11111121

                    // is red

                    if (red(getPix(x,y)) > rThreshold && green((getPix(x,y))) < gThreshold && blue(getPix(x,y)) < bThreshold)
                    {
                        if (xStreak == 0)
                        {
                            firstRed = x;
                            reachMax = false;
                        }
                        xStreak += 1;
                        exceptionStreak += 1;

                        if (xStreak > 35)
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

                    if (reachMax && (xStreak < 15 || x == getWidth()-1))
                    {
                        if (exceptionStreak > 300)
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
            for (int y = getHeight() / 2; y < getHeight(); y++)
            {
                totalAvg = totalAvgs / totalNumAvgs;
            }

        if (CalcLeftRight(totalAvg, 2) == 1)
        {
            masterClass.telemetry.addLine("right");
        }
        else
        {
            masterClass.telemetry.addLine("left");
        }


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
