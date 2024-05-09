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

    private ArrayList<NState> DFAstates = new ArrayList<NState>();
    public void renderDFA(List<List<State>> transitionTable,
                          ArrayList<Double> xCoords,
                          ArrayList<Double> yCoords){
        if(transitionTable.size() > xCoords.size()){
            int i;
            //Render rest of states
            for(i = 0 ; i < xCoords.size() ; i++){
                NState s = new NState(50,StateType.Normal,xCoords.get(i),yCoords.get(i));
                DFAstates.add(s);
                s.setStateName(transitionTable.get(i).get(0).getName());
                dfaScreen.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
            }


            //Render Extra State at fixed coordinate
            NState s = new NState(50,StateType.Normal,50,50);
            DFAstates.add(s);
            s.setStateName(transitionTable.get(i).get(0).getName());
            dfaScreen.getChildren().addAll(s.getCircle(), s.getInnerCircle(),s.getStateName());
        }
        else{
            //render the states
            for(int i = 0 ; i < transitionTable.size() ; i++){
                NState s = new NState(50,StateType.Normal);
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
                String literal = getTransitionLiteral(getNState(srcState.getName()),
                        getNState(transitionTable.get(srcStateIndex).get(dstStateIndex).getName()));
                NState dstState = getNState(transitionTable.get(srcStateIndex).get(dstStateIndex).getName());
                //Add Transition between srcState and DstState Here
                srcNState.addTransition(literal,dstState);
            }
            for(STransition trans : srcNState.getTransitionSTransitions()){
                dfaScreen.getChildren().addAll(trans.getLine(), trans.getArrow());
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
}
