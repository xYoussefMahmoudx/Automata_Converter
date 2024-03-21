package com.example.automataconverter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloController {
    @FXML
    AnchorPane test;
    @FXML
    Button b;

    ArrayList<NState> Nodes = new ArrayList<>();

    private Parent root;
    private Stage stage;
    private Scene scene;


    int counter = 0;
    @FXML
    public void spawn() throws IOException {
        NState s = new NState(50,StateType.Normal);
        showStateNameScreen(s);
        Nodes.add(s);
        test.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
        s.setAnchorCallBack(()->returnAnchorFunction());
        s.setArrayCallBack(()->returnArrayFunction());
    }

    public void showStateNameScreen(NState state) throws IOException{
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource(
                        "stateNameInputView.fxml"));
        root = loader.load();
        stateNameInputController c = loader.getController();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        c.setNameCallback((String name)->{state.setStateName(name);stage.hide();});
    }



    public  AnchorPane returnAnchorFunction() {
       return test;
    }
    public  ArrayList returnArrayFunction() {
        return Nodes;
    }


}