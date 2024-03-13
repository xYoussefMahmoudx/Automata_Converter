package com.example.automataconverter;

import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class NState {
    private Circle circle;
    private Circle innerCircle;
    private StateType stateType;
    private Label stateName;
    private NSideMenu sideMenu;
    private RemoveNode anchorPaneCallBack;
    private RemoveNodeFromArray arrayCallBack;


    public NState(double radius) {

        this.circle = new Circle(radius);
        innerCircle = new Circle();
        this.sideMenu=new NSideMenu();
        stateName = new Label();
        stateName.setLayoutX(circle.getCenterX() - stateName.getWidth()/2);
        stateName.setLayoutY(circle.getCenterY() - stateName.getHeight()/2);
        innerCircle.centerXProperty().bind(circle.centerXProperty());
        innerCircle.centerYProperty().bind(circle.centerYProperty());
        createNode();
        onNormalClicked();
        onFinalClicked();
        onNodeDragged();
        onRemoveClicked();
    }
    public void setAnchorCallBack(RemoveNode callBack){
        this.anchorPaneCallBack=callBack;
    }
    public void setArrayCallBack(RemoveNodeFromArray callBack){
        this.arrayCallBack=callBack;
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
    private void onRemoveClicked(){
        sideMenu.getrLabel().setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                anchorPaneCallBack.apply().getChildren().removeAll(this.innerCircle,this.circle);
                deleteNode();
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
            stateName.setLayoutX(circle.getCenterX() - stateName.getWidth()/2);
            stateName.setLayoutY(circle.getCenterY() - stateName.getHeight()/2);
        });
        innerCircle.setOnMouseDragged(event-> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
            stateName.setLayoutX(circle.getCenterX() - stateName.getWidth()/2);
            stateName.setLayoutY(circle.getCenterY() - stateName.getHeight()/2);
        });
        
    }

    private void makeFinal(){
        // Inner Circle (white fill)
        innerCircle.setRadius(40); // Set smaller radius
        innerCircle.setFill(Color.WHITE); // White fill
        innerCircle.setStroke(Color.BLACK); // Black border
        innerCircle.setStrokeWidth(2); // Border width
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



    public void deleteNode(){
        arrayCallBack.apply().remove(this);
    }


    public Label getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName.setText(stateName);
    }
}

