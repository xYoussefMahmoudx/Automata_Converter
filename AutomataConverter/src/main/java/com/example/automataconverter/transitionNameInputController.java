package com.example.automataconverter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class transitionNameInputController {

    @FXML
    private TextField destinationState;

    @FXML
    private CheckBox epsilonCheck;

    @FXML
    private Text errorLabel;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField transitionLiteral;

    @FXML
    void submit(ActionEvent event) {


    }

}
