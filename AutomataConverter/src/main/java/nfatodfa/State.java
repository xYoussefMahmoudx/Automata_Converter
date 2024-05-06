package nfatodfa;

import java.util.*;

public class State {
    private String name;
    private List<Transition> transitions;
    private StateType stateType;

    public State(String name) {
        this.name = name;
        this.transitions = new ArrayList<>();
        this.stateType = StateType.NORMAL;
    }

    public State(String name, StateType stateType) {
        this.name = name;
        this.transitions = new ArrayList<>();
        this.stateType = stateType;
    }

    public String getName() {
        return name;
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public StateType getStateType() {
        return stateType;
    }

    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    public void sortTransitions() {
        this.transitions.sort(Comparator
                .comparing(Transition::getSymbol)
                .thenComparing(transition -> transition.getNextState().getName()));
    }

//    private boolean isTransitionExist() {
//
//    }

    @Override
    public String toString() {
        return name;
    }

//    public enum StateType {
//        NORMAL,
//        INITIAL,
//        ACCEPTING
//    }
}