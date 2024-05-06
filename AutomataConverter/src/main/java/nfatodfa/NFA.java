package nfatodfa;

import java.util.*;

public class NFA {
    private List<State> states;
    private Set<String> alphabet;
    private State initialState;
    private Set<State> finalStates;

    public NFA() {
        this.states = new ArrayList<>();
        this.alphabet = new HashSet<>();
        this.finalStates = new HashSet<>();
    }

    public void addState(State state) {
        states.add(state);
        if (state.getStateType() == StateType.INITIAL) {
            if (initialState == null)
                initialState = state;
            else
                System.out.println("Initial state already exist");
        }
        else if (state.getStateType() == StateType.FINAL) {
            finalStates.add(state);
        }
    }

    public List<State> getStates() {
        return states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public State getInitialState() {
        return initialState;
    }

    public Set<State> getFinalStates() {
        return finalStates;
    }

    public void addTransition(State fromState, String symbol, State toState) {
        fromState.addTransition(new Transition(symbol, toState));
        alphabet.add(symbol);
    }

    public void sortAllTransitions() {
        for(State s: states)
            s.sortTransitions();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NFA:\n");
        sb.append("States: ").append(states).append("\n");
        sb.append("Alphabet: ").append(alphabet).append("\n");
        sb.append("Initial State: ").append(initialState).append("\n");
        sb.append("Accepting States: ").append(finalStates).append("\n");
        for (State state : states) {
            if (!state.getTransitions().isEmpty()) {
                sb.append("Transitions from ").append(state).append(":\n");
                for (Transition transition : state.getTransitions())
                    sb.append("  ").append(transition.getSymbol()).append(" -> ").append(transition.getNextState()).append("\n");

            }
        }
        return sb.toString();
    }
}
