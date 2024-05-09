package com.example.automataconverter;

import javafx.fxml.FXML;

import javafx.scene.layout.AnchorPane;
import nfatodfa.State;
import nfatodfa.StateType;

import java.util.ArrayList;
import java.util.List;

public class dfaScreenController {
    @FXML
    private AnchorPane dfaScreen;

    private ArrayList<NState> DFAstates = new ArrayList<NState>();
    public void renderDFA(List<List<State>> transitionTable,
                          ArrayList<Double> xCoords,
                          ArrayList<Double> yCoords,
                          Character alphabets[]){
        if(transitionTable.size() > xCoords.size()){
            int i;
            //Render rest of states
            for(i = 0 ; i < xCoords.size() ; i++){
                NState s = new NState(50, convertToGUIStateType(transitionTable.get(i).get(0).getStateType()),xCoords.get(i),yCoords.get(i));
                DFAstates.add(s);
                s.setStateName(transitionTable.get(i).get(0).getName());
                dfaScreen.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
            }


            //Render Extra State at fixed coordinate
            NState s = new NState(50, convertToGUIStateType(transitionTable.get(i).get(0).getStateType()),800,600);
            DFAstates.add(s);
            s.setStateName(transitionTable.get(i).get(0).getName());
            dfaScreen.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
        }
        else{
            //render the states
            for(int i = 0 ; i < transitionTable.size() ; i++){
                NState s = new NState(50, convertToGUIStateType(transitionTable.get(i).get(0).getStateType()));
                DFAstates.add(s);
                s.getCircle().setCenterX(xCoords.get(i));
                s.getInnerCircle().setCenterX(xCoords.get(i));
                s.getCircle().setCenterY(yCoords.get(i));
                s.getInnerCircle().setCenterY(yCoords.get(i));
                s.setStateName(transitionTable.get(i).get(0).getName());
                dfaScreen.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
            }

        }
        for (int srcStateIndex = 0 ; srcStateIndex < transitionTable.size() ; srcStateIndex++){
            State srcState = transitionTable.get(srcStateIndex).get(0);
            NState srcNState = getNState(srcState.getName());
            for (int dstStateIndex = 1 ; dstStateIndex < transitionTable.get(srcStateIndex).size() ; dstStateIndex++){

                NState dstState = getNState(transitionTable.get(srcStateIndex).get(dstStateIndex).getName());
                //Add Transition between srcState and DstState Here
                srcNState.addTransition(alphabets[dstStateIndex - 1].toString(), dstState);

            }
            for(STransition trans : srcNState.getTransitionSTransitions()){
                //trans.getTliteral().setText(getTransitionLiteral(getNState(srcState.getName()),
                        //getNState(trans.getDestinationState().getStateName().getText())));
                dfaScreen.getChildren().addAll(trans.getLine(), trans.getArrow(), trans.getTliteral());
            }

        }



    }
    private NState getNState(String name){
        for(int i = 0 ; i < DFAstates.size() ; i++){
            if(DFAstates.get(i).getStateName().getText().equals(name)){
                return DFAstates.get(i);
            }
        }
        return null;
    }

    private String getTransitionLiteral(NState src, NState dst){
        for (int i = 0 ; i < src.getTransitionSTransitions().size() ; i++){
            if(src.getTransitionSTransitions().get(i).getDestinationState().getStateName().getText()
                    .equals(dst.getStateName().getText())){
                return src.getTransitionSTransitions().get(i).getTliteral().getText();
            }
        }
        return "?";
    }

    private GUIStateType convertToGUIStateType(StateType type){
        if(type == StateType.FINAL){
            return GUIStateType.Final;
        }
        else if(type == StateType.NORMAL) {
            return GUIStateType.Normal;
        }
        else if(type == StateType.INITIAL){
            return GUIStateType.Start;
        }
        return null;
    }
}
