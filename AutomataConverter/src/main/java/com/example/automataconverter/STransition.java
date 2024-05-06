package com.example.automataconverter;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;


public class STransition {
    private Polygon arrow = new Polygon();
    private Line line;
    private Label tliteral;
    private NState destinationState;

    public STransition(){
        this.arrow= new Polygon();
        this.arrow.setFill(Color.RED);
        this.tliteral=new Label();

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
}
