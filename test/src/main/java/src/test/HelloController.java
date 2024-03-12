package src.test;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HelloController {
    @FXML
    AnchorPane test;
    @FXML
    Button b;

    ArrayList<Circle> circles = new ArrayList<>();





    int counter = 0;
    @FXML
    public void spawn(){

        //Circle c = new Circle(30,Color.RED);
        //circles.add(c);
        NState s = new NState(30,'n');
        test.getChildren().add(s.getCircle());


    }
}