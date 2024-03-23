package com.example.automataconverter;

import callbackinterfaces.RemoveNode;
import callbackinterfaces.RemoveNodeFromArray;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Line;


public class NState {
    private Circle circle;
    private Circle innerCircle;
    private StateType stateType;
    private Label stateName;
    private NSideMenu sideMenu;
    private RemoveNode anchorPaneCallBack;
    private RemoveNodeFromArray arrayCallBack;
    private Polygon arrow;
    private Line line;

    public NState(double radius,StateType stateType) {

        this.circle = new Circle(radius);
        this.stateType=stateType;
        innerCircle = new Circle();
        this.sideMenu=new NSideMenu();
        stateName = new Label();
        stateName.setLayoutX(circle.getCenterX() - stateName.getWidth()/2);
        stateName.setLayoutY(circle.getCenterY() - stateName.getHeight()/2);
        stateName.toBack();
        innerCircle.centerXProperty().bind(circle.centerXProperty());
        innerCircle.centerYProperty().bind(circle.centerYProperty());
        arrow=new Polygon();

        this.line = new Line(circle.getCenterX(), circle.getCenterY() + circle.getRadius(), circle.getCenterX(), circle.getCenterY() + circle.getRadius() + 20);
        createNode();
        onNormalClicked();
        onFinalClicked();
        onNodeDragged();
        onRemoveClicked();
        onTransitionClicked();
        onArrowDragged();
        if(stateType.equals(StateType.Final)){
            makeFinal();
        }
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
        //this.stateType = StateType.Normal;
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
                sideMenu.getMenu().show(this.circle,e.getScreenX(),e.getScreenY());
                e.consume();
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

    private void onTransitionClicked(){
        sideMenu.gettLabel().setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {


                addTransition();
            }
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
                sideMenu.getMenu().show(this.circle,e.getScreenX(),e.getScreenY());
                e.consume();
            }
        });

    }

    private void addTransition(){

        arrow.setFill(Color.RED);

            double startX = this.circle.getCenterX()+50;
            double startY=circle.getCenterY();
            arrow.getPoints().addAll(
                    startX, startY - 20,
                    startX + 10, startY - 10,
                    startX, startY

            );


        this.circle.setOnMouseClicked(e->{
            if(e.getButton() == MouseButton.SECONDARY){
                sideMenu.getMenu().show(this.circle,e.getScreenX(),e.getScreenY());
                e.consume();
            }
        });

        //this.line.setStroke(Color.RED);
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

    public Line getLine() {
        return line;
    }
    private void onArrowDragged() {



        arrow.setOnMouseDragged(event-> {
            arrow.getPoints().setAll(
                    event.getX(), event.getY() - 20,
                    event.getX() + 10, event.getY() - 10,
                    event.getX(), event.getY()
            );
            line.setFill(Color.RED);
            line.setStartX(this.circle.getCenterX()+50);
            line.setStartY(circle.getCenterY());
            line.setEndX(event.getX());
            line.setEndY(event.getY()-10);



        });

    }

    public Polygon getArrow() {
        return arrow;
    }

}

