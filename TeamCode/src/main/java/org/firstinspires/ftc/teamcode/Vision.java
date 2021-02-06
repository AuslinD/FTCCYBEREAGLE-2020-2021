package org.firstinspires.ftc.teamcode;

import com.vuforia.CameraDevice;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import static android.graphics.Color.green;
import static android.graphics.Color.red;

public class Vision {

    CameraDevice camera;
    public VuforiaLocalizer vuforia;
    WebcamName webcam;
    MasterClass masterClass = null;

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

    public android.graphics.Bitmap TestWeb() throws InterruptedException
    {
        return getBitmap();
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
        String VUFORIA_KEY =
                "AQswYz7/////AAABmcNWVtOSYEEXpQufuBKrdNMSKO5UAESlpwf1GyWOEzZMytfVdfY2BsxJop+3JkhqYQEby7j5SJHbcw6kSDuMe40rGeec5vJtb9m+qxy8jqy8EuBZ8n9IAldRtolwfIBkMI+d9+EkoqSBiZwhSWDzT0EVw83o3H+WzzMmj91dURhqRNzdHjEz0lUUgwDNrfNuW3oGPn1A1alADdHYnnAo++SiO9m4hHPVkdomVSxNjxu3I6whv16zWlQTLdK97POf2t37U+rS/2hZ5GSNG054PtWDppXH+ec8XNrDfys6+OmeG/m6MFvNjoUAyUgV7bsqMM+QUM3eTI3/FENR6PZ3VND47T3Dm74Hxkor++lEZHEi";

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        webcam = masterClass.hardwareMap.get(WebcamName.class, "Webcam 1");
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        int cameraMonitorViewId = masterClass.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", masterClass.hardwareMap.appContext.getPackageName());
        parameters.cameraName = webcam;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        masterClass.idle();
    }

}
