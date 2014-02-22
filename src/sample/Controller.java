package sample;

import com.googlecode.javacv.OpenCVFrameGrabber;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {
    public TextField aX;
    public TextField aY;
    public TextField bX;
    public TextField bY;
    public TextField cX;
    public TextField cY;
    public TextField dX;
    public TextField dY;
    private  OpenCVFrameGrabber grabber;
    private FrameGrabber frameGrabber;
    public Canvas canvas;
    private Marker marker;

    public void initialize(){
        marker = new Marker();
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if(aX.isFocused() || aY.isFocused()){
                            aX.setText(String.valueOf(t.getX()));
                            aY.setText(String.valueOf(t.getY()));
                            marker.setA(new Point2D(t.getX(), t.getY()));
                            bX.requestFocus();
                        }  else  if(bX.isFocused() || bY.isFocused()){
                            bX.setText(String.valueOf(t.getX()));
                            bY.setText(String.valueOf(t.getY()));
                            marker.setB(new Point2D(t.getX(), t.getY()));
                            cX.requestFocus();
                        } else  if(cX.isFocused() || cY.isFocused()){
                            cX.setText(String.valueOf(t.getX()));
                            cY.setText(String.valueOf(t.getY()));
                            marker.setC(new Point2D(t.getX(), t.getY()));
                            dX.requestFocus();
                        } else  if(dX.isFocused() || dY.isFocused()){
                            dX.setText(String.valueOf(t.getX()));
                            dY.setText(String.valueOf(t.getY()));
                            marker.setD(new Point2D(t.getX(), t.getY()));
                            dX.getParent().requestFocus();
                        }
                    }
                });
        grabber = new OpenCVFrameGrabber(0);
        frameGrabber = new FrameGrabber(canvas, grabber, marker);
    }

    public void initGrabber(){
        frameGrabber.initGrabber();
    }

    public  void startGrabber(){
        frameGrabber.start();
    }

    public void stopGrabber(){
        frameGrabber.stopGrabber();
    }
}
