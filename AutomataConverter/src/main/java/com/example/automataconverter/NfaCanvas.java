package com.example.automataconverter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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


    @FXML
    void convertToDFA(ActionEvent event) {

    }

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
                mainCanvas.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName(),s.getArrow(),s.getLine());
                s.setAnchorCallBack(()->returnAnchorFunction());
                s.setArrayCallBack(()->returnArrayFunction());
                s.setTransitionCallBack(()-> {
                    try {
                        showTransitionScreen();
                    } catch (IOException x) {
                        throw new RuntimeException(x);
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
                mainCanvas.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName(),s.getArrow(),s.getLine());
                s.setAnchorCallBack(()->returnAnchorFunction());
                s.setArrayCallBack(()->returnArrayFunction());
                s.setTransitionCallBack(()-> {
                    try {
                        showTransitionScreen();
                    } catch (IOException x) {
                        throw new RuntimeException(x);
                    }
                });
            }
        });
    }
    private void onStartClicked()  {
        sLabel.setOnMouseClicked(e-> {
            if (e.getButton() == MouseButton.PRIMARY) {
                NState s = new NState(50,StateType.Normal);
                try {
                    showStateNameScreen(s);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Nodes.add(s);
                mainCanvas.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName(),s.getArrow(),s.getLine());
                s.setAnchorCallBack(()->returnAnchorFunction());
                s.setArrayCallBack(()->returnArrayFunction());
                s.setTransitionCallBack(()-> {
                    try {
                        showTransitionScreen();
                    } catch (IOException x) {
                        throw new RuntimeException(x);
                    }
                });
            }
        });
    }
    public void showStateNameScreen(NState state) throws IOException {
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
        return mainCanvas;
    }
    public  ArrayList returnArrayFunction() {
        return Nodes;
    }

    public void showTransitionScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource(
                        "transitionNameInput.fxml"));
        root = loader.load();
        stateNameInputController c = loader.getController();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
