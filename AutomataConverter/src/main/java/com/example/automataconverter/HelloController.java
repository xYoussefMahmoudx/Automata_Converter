package com.example.automataconverter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class HelloController {
    @FXML
    AnchorPane test;
    @FXML
    Button b;

    ArrayList<NState> Nodes = new ArrayList<>();





    int counter = 0;
    @FXML
    public void spawn(){

        NState s = new NState(50);
        Nodes.add(s);
        test.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
        s.setAnchorCallBack(()->returnAnchorFunction());
        s.setArrayCallBack(()->returnArrayFunction());

        Nodes.forEach((n) -> System.out.println(n));



    }





    public  AnchorPane returnAnchorFunction() {
       return test;
    }
    public  ArrayList returnArrayFunction() {
        return Nodes;
    }



}