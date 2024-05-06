package nfatodfa;

import java.util.*;

public class NFA {
    private Set<State> states;
    private Set<Character> alphabets;
    private State initialState;
    private Set<State> finalStates;
    private boolean hasEpsilon;

    public NFA() {
        this.states = new LinkedHashSet<>();
        this.alphabets = new HashSet<>();
        this.finalStates = new HashSet<>();
        hasEpsilon = false;
    }

    // public enum StateType {INITIAL, FINAL}

    private boolean isStateExist(State state) {
        for (State s : states)
            if (s.getName().equals(state.getName()))
                return true;
        return false;
    }

    public void addState(State state) {
        if (!isStateExist(state))
            states.add(state);
        else
            System.out.println("State" + state.getName() + "already exist");
    }

    public Set<State> getStates() {
        return states;
    }

    public Set<Character> getAlphabets() {
        return alphabets;
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public void addFinalState(State finalState) {
        finalStates.add(finalState);
    }

    public Set<State> getFinalStates() {
        return finalStates;
    }

    public void addTransition(State fromState, char alphabet, State toState) {
        if (alphabet == 'e')
            hasEpsilon = true;
        else
            alphabets.add(alphabet);
        fromState.addTransition(new Transition(alphabet, toState));

    }

    public StateType getStateType(State state) {
        if (this.getInitialState() == state)
            return StateType.INITIAL;

        else if (finalStates.contains(state))
            return StateType.FINAL;

        return null;
    }

    public State getStateByName(String name) {
        // if state exist return it else create new state and return it
        for (State state : states)
            if (state.getName().equals(name))
                return state;

        State newState = new State(name);
        this.addState(newState);
        return newState;

    }

    // public void sortAllTransitions() {
    // for(State s: states)
    // s.sortTransitions();
    // }

    public boolean hasEpsilonTransition() {
        return hasEpsilon;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NFA:\n");
        sb.append("States: ").append(states).append("\n");
        sb.append("Alphabet: ").append(alphabets).append("\n");
        sb.append("Initial State: ").append(initialState).append("\n");
        sb.append("Accepting States: ").append(finalStates).append("\n");
        for (State state : states) {
            if (!state.getTransitions().isEmpty()) {
                sb.append("Transitions from ").append(state).append(":\n");
                for (Transition transition : state.getTransitions())
                    sb.append("  ").append(transition).append("\n");

            }
        }
        return sb.toString();
    }
}
