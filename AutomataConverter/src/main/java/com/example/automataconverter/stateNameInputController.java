package com.example.automataconverter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class stateNameInputController {
    @FXML

    private TextField field;
    private String stateName;

    private SetStateName namer;

    public void submit(){
        namer.apply(field.getText());
    }

    public void setNameCallback(SetStateName func){
        namer = func;
    }
    public String getStateName() {
        return stateName;
    }
}
