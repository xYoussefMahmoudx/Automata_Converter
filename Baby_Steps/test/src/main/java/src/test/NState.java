package src.test;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NState {
    private Circle circle;
    private Circle innerCircle;
    private StateType stateType;
    private NSideMenu sideMenu;
    private boolean removeflag;




    public NState(double radius) {

        this.circle = new Circle(radius);
        innerCircle = new Circle();
        this.sideMenu=new NSideMenu();
        createNode();
        onNormalClicked();
        onFinalClicked();
        onNodeDragged();
    }
    private void createNode(){
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        this.stateType = StateType.Normal;



    }
    private void onFinalClicked(){
        sideMenu.getfLabel().setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                stateType=StateType.Final;
                makeFinal();
            }
        });
    }

    private void onNormalClicked(){
        sideMenu.getnLabel().setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                stateType=StateType.Normal;
                makeNormal();
            }
        });
    }
    private void onNodeDragged(){
        this.circle.setOnMouseClicked(e->{
            if(e.getButton() == MouseButton.SECONDARY){
                System.out.println("Hello There");
                sideMenu.getMenu().show(this.circle,e.getScreenX(),e.getScreenY());
            }
        });
        circle.setOnMouseDragged(event-> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            innerCircle.setCenterX(event.getX());
            innerCircle.setCenterY(event.getY());
        });
    }

    private void makeFinal(){
        // Inner Circle (white fill)

        innerCircle.setRadius(40); // Set smaller radius
        innerCircle.setCenterX(circle.getCenterX());
        innerCircle.setCenterY(circle.getCenterY());
        innerCircle.setFill(Color.WHITE); // White fill
        innerCircle.setStroke(Color.BLACK); // Black border
        innerCircle.setStrokeWidth(2); // Border width
        innerCircle.setOnMouseDragged(event-> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            innerCircle.setCenterX(event.getX());
            innerCircle.setCenterY(event.getY());
        });
        this.innerCircle.setOnMouseClicked(e->{
            if(e.getButton() == MouseButton.SECONDARY){
                System.out.println("Hello There");
                sideMenu.getMenu().show(this.circle,e.getScreenX(),e.getScreenY());
            }
        });

    }



    private void makeNormal(){
        innerCircle.setStroke(Color.TRANSPARENT); // Black border
    }

    public Circle getCircle() {
        return circle;
    }

    public Circle getInnerCircle() {
        return innerCircle;
    }


}

