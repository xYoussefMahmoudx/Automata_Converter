package com.example.automataconverter;

import callbackinterfaces.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.ArrayList;


public class NState {
    private Circle circle;
    private Circle innerCircle;
    private StateType stateType;
    private Label stateName;
    private NSideMenu sideMenu;
    private RemoveNode anchorPaneCallBack;
    private RemoveNodeFromArray arrayCallBack;
    private AddTransition TransitionCallBack;
    private UpdateTransition updateTransition;
    private ShowTransitionScreen showTransitionScreen;
    private GetDestinationState getDestinationState;
    private ArrayList<STransition> STransitions = new ArrayList<STransition>();
    private STransition currentTransition;

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

        for(STransition transition : STransitions) {
            transition.setLine(new Line(circle.getCenterX(), circle.getCenterY() + circle.getRadius(), circle.getCenterX(), circle.getCenterY() + circle.getRadius() + 20));
        }

        createNode();
        onNormalClicked();
        onFinalClicked();
        onNodeDragged();
        onRemoveClicked();
        onTransitionClicked();

        if(stateType.equals(StateType.Final)){
            makeFinal();
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
        STransition transition =new STransition();
        transition.setLine(new Line(this.circle.getCenterX()+ this.circle.getRadius(), this.circle.getCenterY() , this.circle.getCenterX()+this.circle.getRadius() + 20, this.circle.getCenterY() ));
        double startX = this.circle.getCenterX()+50;
        double startY=circle.getCenterY();
        transition.getArrow().getPoints().addAll(
                startX, startY - 20,
                startX + 10, startY - 10,
                startX, startY

        );

        updateTransition.apply().getChildren().add(transition.getArrow());
        updateTransition.apply().getChildren().add(transition.getLine());
        updateTransition.apply().getChildren().add(transition.getTliteral());
        currentTransition=transition;
        this.STransitions.add(transition);
        this.circle.setOnMouseClicked(e->{
            if(e.getButton() == MouseButton.SECONDARY){
                sideMenu.getMenu().show(this.circle,e.getScreenX(),e.getScreenY());
                e.consume();
            }
        });
    }
    private void makeNormal(){
        innerCircle.setStroke(Color.TRANSPARENT); // Black border
    }
    public void deleteNode(){
        arrayCallBack.apply().remove(this);
    }
    //getters Functions
    public Circle getCircle() {
        return circle;
    }
    public Circle getInnerCircle() {
        return innerCircle;
    }
    public RemoveNodeFromArray getArrayCallBack() {
        return arrayCallBack;
    }
    public Label getStateName() {
        return stateName;
    }
    public ArrayList<STransition> getTransitionSTransitions() {
        return STransitions;
    }
    // onAction Event Functions
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
                anchorPaneCallBack.apply().getChildren().removeAll(this.innerCircle,this.circle,this.stateName);
                for (STransition transition :STransitions){
                    anchorPaneCallBack.apply().getChildren().removeAll(transition.getLine(),transition.getArrow(),transition.getTliteral());
                }

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
                transition.getLine().setEndX(transition.getDestinationState().circle.getCenterX()-50);
                transition.getLine().setEndY(transition.getDestinationState().circle.getCenterY());

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
    private void onTransitionClicked(){
        sideMenu.gettLabel().setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                showTransitionScreen.apply();
                addTransition();
                onArrowDragged();
            }
        });

    }
    // setters for the callBacks
    public void setAnchorCallBack(RemoveNode callBack){
        this.anchorPaneCallBack=callBack;
    }
    public void setArrayCallBack(RemoveNodeFromArray callBack){
        this.arrayCallBack=callBack;
    }
    public void setUpdateTransition(UpdateTransition updateTransition) {
        this.updateTransition = updateTransition;
    }
    public void setShowTransitionScreen(ShowTransitionScreen showTransitionScreen) {this.showTransitionScreen = showTransitionScreen;}
    public void setTransitionCallBack(AddTransition callBack){
        this.TransitionCallBack=callBack;
    }
    public void setGetDestinationState(GetDestinationState callBack){
        this.getDestinationState=callBack;
    }
    //setter Functions
    public void setStateName(String stateName) {
        this.stateName.setText(stateName);
    }
}

