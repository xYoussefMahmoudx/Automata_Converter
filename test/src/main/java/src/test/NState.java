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

    private char stateType;
    private ContextMenu menu;


    public NState(double radius, char stateType) {
        this.circle = new Circle(radius);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        this.stateType = stateType;
        this.menu = new ContextMenu();
        Label lbl = new Label("Final");
        Label lbl2 = new Label("Remove");
        CustomMenuItem i1 = new CustomMenuItem(lbl);
        CustomMenuItem i2 = new CustomMenuItem(lbl2);
        menu.getItems().add(i1);
        menu.getItems().add(i2);
        lbl.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                FileInputStream f;
                try {
                    f = new FileInputStream(
                            "C:\\Users\\user\\Desktop\\Java Projects\\test\\src\\main\\resources\\src\\test\\scam.png");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                Image im = new Image(f);
                this.circle.setFill(new ImagePattern(im));
            }
        });

        this.circle.setOnMouseClicked(e->{
            if(e.getButton() == MouseButton.SECONDARY){
                System.out.println("Hello There");
                this.menu.show(this.circle,e.getScreenX(),e.getScreenY());
            }
        });
        circle.setOnMouseDragged(event-> {
            circle.setCenterX(event.getX());
            circle.setCenterY(event.getY());
        });

    }

    public Circle getCircle() {
        return circle;
    }
}
