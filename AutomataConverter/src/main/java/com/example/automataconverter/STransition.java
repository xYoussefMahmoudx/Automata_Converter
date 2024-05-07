package com.example.automataconverter;
import callbackinterfaces.GetTransitionsArray;
import callbackinterfaces.UpdateTransition;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;


public class STransition {
    private Polygon arrow = new Polygon();
    private Line line;
    private Label tliteral;
    private NState destinationState;
    private Label deleteLabel = new Label("delete");
    private ContextMenu menu=new ContextMenu(new CustomMenuItem(deleteLabel));
    private GetTransitionsArray getTransitionsArray;
    private UpdateTransition updateTransition;

    public STransition(){
        this.arrow= new Polygon();
        this.arrow.setFill(Color.RED);
        this.tliteral=new Label();
        onArrowClicked();
        onRemoveClicked();

    }

    private void onRemoveClicked(){
        deleteLabel.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                for(STransition transition : getTransitionsArray.apply()){
                    if(transition.equals(this)){
                        getTransitionsArray.apply().remove(transition);
                        updateTransition.apply().getChildren().removeAll(this.arrow,this.line,this.tliteral);
                        break;
                    }
                }
            }
        });
    }
    private void onArrowClicked(){
        this.arrow.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.SECONDARY) {
                menu.show(arrow, e.getScreenX(), e.getScreenY());
                e.consume();
            }
        });
        this.tliteral.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.SECONDARY) {
                menu.show(arrow, e.getScreenX(), e.getScreenY());
                e.consume();
            }
        });
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }



    public Polygon getArrow() {
        return arrow;
    }

    public void setTiteral(String literal){
        tliteral.setText(literal);
    }

    public Label getTliteral() {
        return tliteral;
    }

    public void setDestinationState(NState destinationState) {
        this.destinationState = destinationState;
    }

    public NState getDestinationState() {
        return destinationState;
    }
    public void setArrayTransition(GetTransitionsArray callBack){
        this.getTransitionsArray=callBack;
    }
    public void setUpdateTransition(UpdateTransition updateTransition) {
        this.updateTransition = updateTransition;
    }
}
