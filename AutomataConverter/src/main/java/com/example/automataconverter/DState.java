package com.example.automataconverter;

import callbackinterfaces.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.ArrayList;


public class DState {
    private Circle circle;
    private Circle innerCircle;
    private StateType stateType;
    private Label stateName;
    private AddTransition TransitionCallBack;
    private UpdateTransition updateTransition;
    private ShowTransitionScreen showTransitionScreen;
    private GetDestinationState getDestinationState;
    private CanvasCallBack canvasCallBack;
    private ArrayList<STransition> STransitions = new ArrayList<STransition>();
    private STransition currentTransition;
    public DState(double radius,StateType stateType,double centerX, double centerY){
        this.circle = new Circle(radius);
        this.stateType=stateType;
        innerCircle = new Circle();
        stateName = new Label();
        stateName.toBack();
        innerCircle.centerXProperty().bind(circle.centerXProperty());
        innerCircle.centerYProperty().bind(circle.centerYProperty());
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        stateName.setLayoutX(circle.getCenterX() - stateName.getWidth()/2);
        stateName.setLayoutY(circle.getCenterY() - stateName.getHeight()/2);
        for(STransition transition : STransitions) {
            transition.setLine(new Line(circle.getCenterX(), circle.getCenterY() + circle.getRadius(), circle.getCenterX(), circle.getCenterY() + circle.getRadius() + 20));
        }


        createNode();
        if(this.stateType.equals(StateType.Final)){
            makeFinal();
        }else if(this.stateType.equals(StateType.Normal)){
            makeNormal();
        }else if(this.stateType.equals(StateType.Start)){
            makeStart();
        }


    }
    public DState(double radius,StateType stateType) {
        this.circle = new Circle(radius);
        this.stateType=stateType;
        innerCircle = new Circle();
        stateName = new Label();
        stateName.setLayoutX(circle.getCenterX() - stateName.getWidth()/2);
        stateName.setLayoutY(circle.getCenterY() - stateName.getHeight()/2);
        stateName.toBack();
        innerCircle.centerXProperty().bind(circle.centerXProperty());
        innerCircle.centerYProperty().bind(circle.centerYProperty());

        for(STransition transition : STransitions) {
            transition.setLine(new Line(circle.getCenterX(), circle.getCenterY() + circle.getRadius(), circle.getCenterX(), circle.getCenterY() + circle.getRadius() + 20));
        }


        createNode();
        if(this.stateType.equals(StateType.Final)){
            makeFinal();
        }else if(this.stateType.equals(StateType.Normal)){
            makeNormal();
        }else if(this.stateType.equals(StateType.Start)){
            makeStart();
        }


    }
    // External Use update Functions
    public void updateTransitionLabel(){
        currentTransition.setTiteral(this.TransitionCallBack.apply());
    }
    public void updateDestination(){
        currentTransition.setDestinationState(this.getDestinationState.apply());
    }

    // Functional Functions
    private void createNode(){
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
    }
    private void makeFinal(){
        this.stateType=StateType.Final;
        this.circle.setStroke(Color.BLACK);
        innerCircle.setRadius(40); // Set smaller radius
        innerCircle.setFill(Color.WHITE); // White fill
        innerCircle.setStroke(Color.BLACK); // Black border
        innerCircle.setStrokeWidth(2); // Border width
    }
    private void addTransition(){
        STransition transition =new STransition();
        transition.setArrayTransition(()-> STransitions);
        transition.setLine(new Line(this.circle.getCenterX()+ this.circle.getRadius(), this.circle.getCenterY() , this.circle.getCenterX()+this.circle.getRadius() + 20, this.circle.getCenterY() ));
        double startX = this.circle.getCenterX()+50;
        double startY=circle.getCenterY();
        transition.getArrow().getPoints().addAll(
                startX, startY - 20,
                startX + 10, startY - 10,
                startX, startY

        );
        transition.setUpdateTransition(()-> updateTransition.apply());
        updateTransition.apply().getChildren().add(transition.getArrow());
        updateTransition.apply().getChildren().add(transition.getLine());
        updateTransition.apply().getChildren().add(transition.getTliteral());
        currentTransition=transition;

    }
    public void makeNormal(){
        this.stateType=StateType.Normal;
        innerCircle.setStroke(Color.TRANSPARENT); // Black border
        this.circle.setStroke(Color.BLACK);
    }
    private void makeStart(){
        this.stateType=StateType.Start;
        innerCircle.setStroke(Color.TRANSPARENT);// Black border
        this.circle.setStroke(Color.RED);
    }
    //getters Functions
    public Circle getCircle() {
        return circle;
    }
    public Circle getInnerCircle() {
        return innerCircle;
    }
    public Label getStateName() {
        return stateName;
    }
    public ArrayList<STransition> getTransitionSTransitions() {
        return STransitions;
    }
    public StateType getStateType() {
        return stateType;
    }

    // onAction Event Functions


    private void onArrowDragged() {
        for(STransition transition : this.STransitions) {
            transition.getArrow().setOnMouseDragged(event -> {
                transition.getArrow().getPoints().setAll(
                        event.getX(), event.getY() - 20,
                        event.getX() + 10, event.getY() - 10,
                        event.getX(), event.getY()
                );
                transition.getLine().setFill(Color.RED);
                transition.getLine().setStartX(this.circle.getCenterX() + 50);
                transition.getLine().setStartY(circle.getCenterY());
                transition.getLine().setEndX(event.getX());
                transition.getLine().setEndY(event.getY() - 10);
                transition.getTliteral().translateXProperty().bind((transition.getLine().startXProperty().add(transition.getLine().endXProperty())).divide(2).subtract(transition.getTliteral().widthProperty().divide(2)));
                // Bind label's translate Y to the midpoint of the line's start and end Y minus an offset
                transition.getTliteral().translateYProperty().bind((transition.getLine().startYProperty().add(transition.getLine().endYProperty())).divide(2).subtract(20));


                transition.getLine().setStartX(this.circle.getCenterX()+ this.circle.getRadius());
                transition.getLine().setStartY(this.circle.getCenterY());
                transition.getLine().setEndX(transition.getDestinationState().getCircle().getCenterX()-50);
                transition.getLine().setEndY(transition.getDestinationState().getCircle().getCenterY());

                double startX = transition.getLine().getEndX();
                double startY=transition.getLine().getEndY();
                double arrowMidX = startX;
                double arrowMidY = startY - 9;

                transition.getArrow().getPoints().setAll(
                        startX, startY - 20,
                        startX + 10, startY - 10,
                        startX, startY

                );

                transition.getLine().setEndX(arrowMidX);
                transition.getLine().setEndY(arrowMidY);

            });
        }
    }

    //callback updates
    public void setGetDestinationState(GetDestinationState callBack){
        this.getDestinationState=callBack;
    }
    //setter Functions
    public void setStateName(String stateName) {
        this.stateName.setText(stateName);
        this.stateName.setLayoutX(circle.getCenterX() - this.stateName.getWidth()/2);
        this.stateName.setLayoutY(circle.getCenterY() - this.stateName.getHeight()/2);
    }
    }



