package com.example.automataconverter;

import javafx.fxml.FXML;

import javafx.scene.layout.AnchorPane;
import nfatodfa.State;

import java.util.ArrayList;
import java.util.List;
import nfatodfa.State;
public class dfaScreenController {
    @FXML
    private AnchorPane dfaScreen;


    public void renderDFA(List<List<State>> transitionTable,
                          ArrayList<Double> xCoords,
                          ArrayList<Double> yCoords){
        if(transitionTable.size() > xCoords.size()){
            int i;
            //Render rest of states
            for(i = 0 ; i < xCoords.size() ; i++){
                NState s = new NState(50,StateType.Normal,xCoords.get(i),yCoords.get(i));
                s.setStateName(transitionTable.get(i).get(0).getName());
                dfaScreen.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
            }

            //Render Extra State at fixed coordinate
            NState s = new NState(50,StateType.Normal,50,50);
            s.setStateName(transitionTable.get(i).get(0).getName());
            dfaScreen.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
        }
        else{
            //render the states
            for(int i = 0 ; i < transitionTable.size() ; i++){
                NState s = new NState(50,StateType.Normal);
                s.getCircle().setCenterX(xCoords.get(i));
                s.getInnerCircle().setCenterX(xCoords.get(i));
                s.getCircle().setCenterY(yCoords.get(i));
                s.getInnerCircle().setCenterY(yCoords.get(i));
                s.setStateName(transitionTable.get(i).get(0).getName());
                dfaScreen.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
            }


        }

    }
}
