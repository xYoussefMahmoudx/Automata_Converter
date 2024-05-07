package com.example.automataconverter;

import callbackinterfaces.CanvasCallBack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class NfaCanvas {

    @FXML
    private Button convertToDFABtn;
    @FXML
    private AnchorPane mainCanvas;
    private Label sLabel =new Label("start") ;
    private Label fLabel =new Label("Final");
    private Label nLabel = new Label("Normal");
    private ContextMenu menu=new ContextMenu(new CustomMenuItem(sLabel),
                                            new CustomMenuItem(fLabel),
                                            new CustomMenuItem(nLabel));
    private ArrayList<NState> Nodes = new ArrayList<>();
    private Parent root;
    private Stage stage;
    private Scene scene;
//Convert to DFA Button
    @FXML
    void convertToDFA(ActionEvent event) {

    }
// onAction Event Functions
    @FXML
    void onRightClicked(MouseEvent event) {
        if(event.getButton() == MouseButton.SECONDARY){
            menu.show(mainCanvas,event.getScreenX(),event.getScreenY());
            onFinalClicked();
            onStartClicked();
            onNormalClicked();
        }
    }
    private void onFinalClicked(){
        fLabel.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                NState s = new NState(50,StateType.Final);
                try {
                    showStateNameScreen(s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Nodes.add(s);
                mainCanvas.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
                s.setAnchorCallBack(()->returnAnchorFunction());
                s.setArrayCallBack(()->returnArrayFunction());
                s.setAnchorCallBack(()->returnAnchorFunction());
                s.setArrayCallBack(()->returnArrayFunction());
                s.setUpdateTransition(()->returnAnchorFunction());
                s.setCanvasCallBack(()->returnCanvas());
                s.setShowTransitionScreen(()-> {
                    try {
                        showTransitionScreen(s);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });
    }
    private void onNormalClicked(){
        nLabel.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                NState s = new NState(50,StateType.Normal);
                try {
                    showStateNameScreen(s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Nodes.add(s);
                mainCanvas.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
                s.setAnchorCallBack(()->returnAnchorFunction());
                s.setArrayCallBack(()->returnArrayFunction());
                s.setUpdateTransition(()->returnAnchorFunction());
                s.setCanvasCallBack(()->returnCanvas());
                s.setShowTransitionScreen(()-> {
                    try {
                        showTransitionScreen(s);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });
    }
    private void onStartClicked()  {
        sLabel.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                NState s = new NState(50,StateType.Start);
                try {
                    showStateNameScreen(s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Nodes.add(s);
                for (NState state:Nodes) {
                    if (state.getStateType().equals(StateType.Start) && !state.equals(s)) {
                        state.makeNormal();
                    }
                }

                mainCanvas.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
                s.setAnchorCallBack(()->returnAnchorFunction());
                s.setArrayCallBack(()->returnArrayFunction());
                s.setUpdateTransition(()->returnAnchorFunction());
                s.setCanvasCallBack(()->returnCanvas());
                s.setShowTransitionScreen(()-> {
                    try {
                        showTransitionScreen(s);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });
    }
    //show External Screens Functions
    public void showStateNameScreen(NState state) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource(
                        "stateNameInputView.fxml"));
        root = loader.load();
        stateNameInputController c = loader.getController();
        stage = new Stage();
        stage.setTitle("Enter state Name");
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        c.setNameCallback((String name)->{state.setStateName(name);stage.hide();});
    }
    public void showTransitionScreen(NState state) throws IOException{
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource(
                        "transitionNameInput.fxml"));
        root = loader.load();
        transitionNameInputController c = loader.getController();
        stage = new Stage();
        stage.setTitle("Enter Transition");
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        c.setGetSourceNode(()->{return state;});
        c.setStage(stage);
        dataComboBox(c.getDropDownMenu());
    }
    //Function used in External Screen
    private void dataComboBox(ComboBox dropDownMenu) {
        ObservableList<String> data = FXCollections.observableArrayList();
        for(NState state: Nodes){
            data.add(state.getStateName().getText());
        }
        dropDownMenu.setItems(data);
    }
    //return Functions for CallBacks
    public  AnchorPane returnAnchorFunction() {
    return mainCanvas;
}
    public  ArrayList returnArrayFunction() {
        return Nodes;
    }
    public NfaCanvas returnCanvas() {
        return this;
    }



}

