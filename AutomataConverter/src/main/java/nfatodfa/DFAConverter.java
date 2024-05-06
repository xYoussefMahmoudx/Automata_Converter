package nfatodfa;

import java.util.*;

public class DFAConverter {
    private NFA nfa;
    private List<List<State>> table;
    private Set<State> handledStates;
    private Set<State> unhandledStates;
    private boolean phiStateExist;


    public DFAConverter(NFA nfa) {
        this.nfa = nfa;
        table = new ArrayList<>();
        handledStates = new HashSet<>();
        unhandledStates = new HashSet<>();
        phiStateExist = false;
    }

    public List<List<State>> getTransitionTableStates() {
        addTableRow(nfa.getInitialState());
        handledStates.add(nfa.getInitialState());

        processUnhandledStates();
        getRemainingStates(); // modify neso4


        if (phiStateExist)
            addPhiRow();
        return table;
    }

    private void processUnhandledStates() {
        while (!unhandledStates.isEmpty()) {
            Iterator<State> iterator = unhandledStates.iterator();
            State state = iterator.next();

            if (!isStateExist(handledStates, state)) {
                handledStates.add(state);
                unhandledStates.remove(state);

                addTableRow(state);
            }
        }
    }

    private boolean isStateExist(Set<State> states, State s){
        boolean isHandled = false;
        for (State state:states){
            if (state.getName().equals(s.getName()))
                return true;
        }
        return isHandled;
    }

    private void addPhiRow () {
        State phi = new State("Φ");
        List<State> row = new ArrayList<>();
        for (int i = 0; i <= nfa.getAlphabet().size(); i++)
            row.add(phi);

        table.add(row);
    }

    private void addTableRow(State state) {
        List<State> row = new ArrayList<>();
        row.add(state);
        handledStates.add(state);

        for (String symbol : nfa.getAlphabet()) {
            List<State> nextStates = getNextStatesForSymbol(state, symbol);
            if (!nextStates.isEmpty()) {
                State newState;
                if (nextStates.size() == 1) {
                    newState = nextStates.get(0);
                    row.add(newState);
                } else {
                    newState = createCompositeState(nextStates);
                    row.add(newState);
                }
                if ((!isStateExist(handledStates, newState)) && (!isStateExist(unhandledStates, newState))) {
                    unhandledStates.add(newState);
                }
            } else {
                State phi = new State("Φ");
                phiStateExist = true;
                row.add(phi);
            }
        }

        table.add(row);
    }

    private List<State> getNextStatesForSymbol(State state, String symbol) {
        List<State> nextStates = new ArrayList<>();
        for (Transition transition : state.getTransitions()) {
            if (transition.getSymbol().equals(symbol)) {
                nextStates.add(transition.getNextState());
            }
        }
        return nextStates;
    }

    private State createCompositeState(List<State> nextStates) {
        StringBuilder nextStateString = new StringBuilder();
        Set<String> sortedName = new TreeSet<>();
        StateType stateType = StateType.NORMAL;

        for (State nextState : nextStates) {
            handledStates.add(nextState);
            sortedName.add(nextState.getName());
            if (nextState.getStateType() != StateType.NORMAL) {
                stateType = nextState.getStateType();
            }
        }

        for (String nextState : sortedName) {
            nextStateString.append(nextState).append("+");
        }
        nextStateString.setLength(nextStateString.length() - 1);

        State newState = new State(nextStateString.toString(), stateType);
        for (State nextState : nextStates) {
            for (Transition t : nextState.getTransitions()) {
                newState.addTransition(t);
            }
        }
        return newState;
    }

    private void getRemainingStates() {
        for(State state:nfa.getStates()){
                if (!isStateExist(handledStates, state))
                    unhandledStates.add(state);
        }
        processUnhandledStates();
    }
}
