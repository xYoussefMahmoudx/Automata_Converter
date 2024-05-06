package com.example.automataconverter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;


public class STransition {
    private Polygon arrow = new Polygon();
    private Line line;

    public STransition(){
        this.arrow= new Polygon();
        this.arrow.setFill(Color.RED);

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
}
