package sample;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 21.02.14
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class FrameGrabber {
    private Canvas canvas;
    private OpenCVFrameGrabber grabber;
    private boolean running = true;
    private int frameRate = 25;
    private Marker marker;

    public FrameGrabber(Canvas canvas, OpenCVFrameGrabber grabber, Marker marker) {
        this.canvas = canvas;
        this.grabber = grabber;
        this.marker = marker;


    }

    public void initGrabber(){
        boolean grabberStartet = false;
        while (!grabberStartet) {
            try {
                grabber.start();
                grabber.setImageWidth((int) canvas.getWidth());
                grabber.setImageHeight((int) canvas.getHeight());
                grabberStartet = true;
            } catch (Exception e) {
                System.out.println("Error by Grabber start. Noch ein Versuch...");
            }
        }
        System.out.println("Grabber startet");
    }

    public void start() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                final int waitTime = 1000 / frameRate;
                while (running) {
                    opencv_core.IplImage cvimg = null;
                    try {
                        cvimg = grabber.grab();
                    } catch (Exception e) {

                    }
                    if (cvimg != null) {
                        try {
                            // show image on window
                            BufferedImage bufImg = cvimg.getBufferedImage();
                            WritableImage fxImage = SwingFXUtils.toFXImage(bufImg, null);
                            canvas.getGraphicsContext2D().drawImage(fxImage, 0, 0);
                            drawMarker();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Image von grabber is null");
                    }
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                try {
                    grabber.stop();
                    grabber.release();
                    System.out.println("Grabber stopped");
                } catch (Exception e) {
                    System.out.println("Error by stop of Grabber");
                }
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        Thread th = new Thread(task);
//        th.setDaemon(true);
        th.start();
    }

    public void stopGrabber() {
        running = false;
    }

    private void drawMarker(){
        if(marker.getA() != null){
            double markerX = marker.getA().getX();
            double markerY = marker.getA().getY();
            double textX = markerX - 20;
            double textY = markerY - 20;
            canvas.getGraphicsContext2D().fillText("A", textX,  textY);
            canvas.getGraphicsContext2D().setStroke(Color.RED);
            canvas.getGraphicsContext2D().strokeLine(markerX, markerY, textX, textY);
        }

        if(marker.getB() != null){
            double markerX = marker.getB().getX();
            double markerY = marker.getB().getY();
            double textX = markerX + 20;
            double textY = markerY - 20;
            canvas.getGraphicsContext2D().fillText("B", textX,  textY);
            canvas.getGraphicsContext2D().setStroke(Color.RED);
            canvas.getGraphicsContext2D().strokeLine(markerX, markerY, textX, textY);
        }

        if(marker.getC() != null){
            double markerX = marker.getC().getX();
            double markerY = marker.getC().getY();
            double textX = markerX + 20;
            double textY = markerY + 20;
            canvas.getGraphicsContext2D().fillText("C", textX,  textY);
            canvas.getGraphicsContext2D().setStroke(Color.RED);
            canvas.getGraphicsContext2D().strokeLine(markerX, markerY, textX, textY);
        }

        if(marker.getD() != null){
            double markerX = marker.getD().getX();
            double markerY = marker.getD().getY();
            double textX = markerX - 20;
            double textY = markerY + 20;
            canvas.getGraphicsContext2D().fillText("D", textX - 10,  textY);
            canvas.getGraphicsContext2D().setStroke(Color.RED);
            canvas.getGraphicsContext2D().strokeLine(markerX, markerY, textX, textY);
        }
    }
}
