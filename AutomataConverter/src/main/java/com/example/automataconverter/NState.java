package com.example.automataconverter;

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
    private NSideMenu sideMenu;
    private RemoveNode anchorPaneCallBack;
    private RemoveNodeFromArray arrayCallBack;

    private Polygon arrow;
    private Line line;




    public NState(double radius) {

        this.circle = new Circle(radius);
        innerCircle = new Circle();
        this.sideMenu=new NSideMenu();
        arrow=new Polygon();

        this.line = new Line(circle.getCenterX(), circle.getCenterY() + circle.getRadius(), circle.getCenterX(), circle.getCenterY() + circle.getRadius() + 20);
        createNode();
        onNormalClicked();
        onFinalClicked();
        onNodeDragged();
        onRemoveClicked();
        onTransitionClicked();
        onArrowDragged();
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

    private void onTransitionClicked(){
        sideMenu.gettLabel().setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {


                addTransition();
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
                System.out.println("Hello There");
                sideMenu.getMenu().show(this.circle,e.getScreenX(),e.getScreenY());
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

