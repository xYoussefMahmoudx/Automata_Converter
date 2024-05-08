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
import nfatodfa.DFAConverter;
import nfatodfa.NFA;
import nfatodfa.State;
import nfatodfa.Transition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    void convertToDFA(ActionEvent event) throws IOException{
        NFA nfa=getNFA();
        System.out.println(nfa);
        DFAConverter converter = new DFAConverter(nfa, true);
        List<List<State>> transitionTable = converter.convertToDFA();
        printTable(converter.getNfa(), transitionTable);

        //Get all coords of previous states
        ArrayList<Double> xCoords = new ArrayList<Double>();
        ArrayList<Double> yCoords = new ArrayList<Double>();

        for (int i = 0 ; i < Nodes.size() ; i++){
            xCoords.add(Nodes.get(i).getCircle().getCenterX());
            yCoords.add(Nodes.get(i).getCircle().getCenterY());
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource(
                        "dfa-screen.fxml"));
        root = loader.load();
        dfaScreenController c = loader.getController();
        c.renderDFA(transitionTable,xCoords,yCoords);


        stage = new Stage();
        stage.setTitle("Result DFA");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    public static void printTable(NFA nfa, List<List<State>> transitionTable) {
        System.out.println("--- Transition Table ---");
        System.out.print("\tState \t");
        StringBuilder sb = new StringBuilder();
        for (char s : nfa.getAlphabets())
            sb.append(s).append("\t\t");
        sb.setLength(sb.length() - 2);
        System.out.println(sb.append("\t"));

        System.out.println("--------------------------");

        for (List<State> row : transitionTable) {
            System.out.print("\t");
            for (int i = 0; i < row.size(); i++) {
                if (nfa.getStateType(row.get(i)) == nfatodfa.StateType.FINAL && i == 0)
                    System.out.print("* ");
                else if (nfa.getInitialState() == row.get(i) && i == 0)
                    System.out.print("> ");
                System.out.print(row.get(i) + "\t\t");
            }
            System.out.println();
        }
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

    public NFA getNFA(){
        NFA nfa=new NFA();
        ArrayList<State>states=new ArrayList<>();
        ArrayList<ArrayList<Transition>>transitions=new ArrayList<>();
        for (NState node:Nodes){
            states.add(new State(node.getStateName().getText()));
            nfa.addState(states.get(states.size()-1));
            if(node.getStateType().equals(StateType.Start)){
                nfa.setInitialState(states.get(states.size()-1));
            }
            if(node.getStateType().equals(StateType.Final)){
                nfa.addFinalState(states.get(states.size()-1));
            }

        }

        for (NState node:Nodes){
            ArrayList<Transition>tempArray=new ArrayList<>();
            for (STransition transition:node.getTransitionSTransitions()){
                tempArray.add(new Transition(transition.getTliteral().getText().charAt(0),states.get(Nodes.indexOf(transition.getDestinationState()))));
            }
            transitions.add(tempArray);



        }int i=0;
        for (ArrayList<Transition> arr :transitions){

            for (Transition tran :arr){
                nfa.addTransition(states.get(i),tran.getAlphabet(),tran.getNextState());
            }
            i++;
        }
            return nfa;
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

