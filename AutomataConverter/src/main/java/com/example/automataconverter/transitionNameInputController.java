package com.example.automataconverter;

import callbackinterfaces.AddTransition;
import callbackinterfaces.GetSourceNode;
import callbackinterfaces.SetStateName;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class transitionNameInputController {

    @FXML
    private TextField destinationState;

    @FXML
    private CheckBox epsilonCheck;

    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane pane;
    @FXML
    private Button btn;
    @FXML
    private TextField transitionLiteral;
    @FXML
    private Stage stage;
    private GetSourceNode sourceNode;
    private AddTransition tLiteral;
    private boolean flag=false;


    @FXML
    void submit(ActionEvent event) {
        sourceNode.apply().setTransitionCallBack(()->returnLiteral());
        sourceNode.apply().updateTransitionLabel();
        if(returnLiteral().isBlank()){
            errorLabel.setText("please enter a transition literal");
        }else{
            stage.hide();
        }


    }
    String returnLiteral(){return transitionLiteral.getText();}



    public Label getErrorLabel(){
        return errorLabel;
    }

    public void setGetSourceNode(GetSourceNode callBack){
        this.sourceNode=callBack;
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }
}
