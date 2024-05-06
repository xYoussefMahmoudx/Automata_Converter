package nfatodfa;

import java.util.*;

public class DFAConverter {
    private NFA nfa;
    private List<List<State>> transitionTable;
    private Set<State> handledStates;
    private Set<State> unhandledStates;
    private boolean phiStateExist;
    private boolean minimized;

    public DFAConverter(NFA nfa, boolean minimized) {
        this.nfa = nfa;
        transitionTable = new ArrayList<>();
        handledStates = new HashSet<>();
        unhandledStates = new LinkedHashSet<>();
        phiStateExist = false;
        this.minimized = minimized;
    }

    public List<List<State>> convertToDFA() {
        if (nfa.hasEpsilonTransition())
            nfa = convertToNFA(nfa);

        addTableRow(nfa.getInitialState());

        processUnhandledStates();
        // getRemainingStates();

        if (phiStateExist)
            addPhiRow();
        return transitionTable;
    }

    private NFA convertToNFA(NFA epsilonNFA) {
        NFA convertedNFA = new NFA();
        State initialState = new State(epsilonNFA.getInitialState().getName());
        convertedNFA.addState(initialState);
        convertedNFA.setInitialState(initialState);
        State currentState;
        for (State curr : epsilonNFA.getStates()) {
            currentState = convertedNFA.getStateByName(curr.getName());
            for (char alphabet : epsilonNFA.getAlphabets()) {
                Set<State> epsilon = new HashSet<>();
                List<State> nextStates = new ArrayList<>();

                for (State state : getEpsilonClosure(curr)) {
                    for (State state2 : getNextStatesForAlphabet(state, alphabet)) {
                        nextStates.add(state2);
                    }
                }
                for (State s : nextStates) {
                    for (State state : getEpsilonClosure(s)) {
                        epsilon.add(state);
                    }
                }
                for (State s : epsilon) {
                    State newState = convertedNFA.getStateByName(s.getName());
                    // convertedNFA.addState(newState);
                    convertedNFA.addTransition(currentState, alphabet, newState);
                    if (epsilonNFA.getStateType(s) == StateType.FINAL)
                        convertedNFA.addFinalState(newState);
                }

            }
        }
        return convertedNFA;
    }

    private Set<State> getEpsilonClosure(State state) {
        Set<State> epsilonClosure = new HashSet<>();
        Set<State> visited = new HashSet<>();
        Stack<State> stack = new Stack<>();
        stack.push(state);

        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            if (!visited.contains(currentState)) {
                visited.add(currentState);
                epsilonClosure.add(currentState);

                for (Transition transition : currentState.getTransitions()) {
                    if (transition.getAlphabet() == 'e') { // check if there is any epsilon transition as we represent
                                                           // empty string as epsilon
                        // epsilonClosure.add(transition.getNextState());
                        stack.push(transition.getNextState());
                    }
                }
            }
        }

        return epsilonClosure;
    }

    private void processUnhandledStates() {
        while (!unhandledStates.isEmpty()) {
            Iterator<State> iterator = unhandledStates.iterator();
            State state = iterator.next();

            addTableRow(state);
            unhandledStates.remove(state);
        }
    }

    private boolean isStateExist(Set<State> states, State s) {
        for (State state : states) {
            if (state.getName().equals(s.getName()))
                return true;
        }
        return false;
    }

    private void addPhiRow() {
        State phi = new State("Φ");
        List<State> row = new ArrayList<>();
        for (int i = 0; i <= nfa.getAlphabets().size(); i++)
            row.add(phi);

        transitionTable.add(row);
    }

    private void addTableRow(State state) {
        List<State> row = new ArrayList<>();
        row.add(state);
        handledStates.add(state);

        for (char alphabet : nfa.getAlphabets()) {
            List<State> nextStates = getNextStatesForAlphabet(state, alphabet);
            if (!nextStates.isEmpty()) {
                State newState;
                if (nextStates.size() == 1) {
                    newState = nextStates.get(0);
                    if (!isStateExist(handledStates, newState))
                        unhandledStates.add(newState);
                    row.add(newState);
                } else {
                    newState = createCombinedState(nextStates);

                    row.add(newState);
                }
            } else {
                State phi = new State("Φ");
                phiStateExist = true;
                row.add(phi);
            }
        }

        transitionTable.add(row);
    }

    private List<State> getNextStatesForAlphabet(State state, char alphabet) {
        List<State> nextStates = new ArrayList<>();
        for (Transition transition : state.getTransitions()) {
            if (transition.getAlphabet() == alphabet) {
                nextStates.add(transition.getNextState());
            }
        }
        return nextStates;
    }

    private State createCombinedState(List<State> nextStates) {
        /*
         * using TreeSet because it sorts the new state alphabetically to make sure that
         * there is no repeated state with another name
         */
        Set<String> sortedName = new TreeSet<>();
        StateType stateType = null;

        for (State nextState : nextStates) {
            sortedName.add(nextState.getName());

            if (nfa.getStateType(nextState) == StateType.FINAL)
                stateType = StateType.FINAL;
        }
        String newStateName = String.join(",", sortedName.toArray(new String[0]));

        // StringBuilder newStateName = new StringBuilder();

        // for (String nextState : sortedName)
        // newStateName.append(nextState).append("+");
        //
        // newStateName.setLength(newStateName.length() - 1); // remove the last char
        // '+'

        // for (State nextState : nextStates) {
        // if (minimized) {
        // if (!transitionTable.isEmpty()) // check if not the first row (solve a
        // problem happened in slide 6)
        // handledStates.add(nextState);
        // }
        // }

        // check if the new composited state is in handled or unhandled list so no need
        // to create new one
        for (State s : handledStates) {
            if (s.getName().equals(newStateName.toString()))
                return s;
        }
        for (State s : unhandledStates) {
            if (s.getName().equals(newStateName.toString()))
                return s;
        }

        State newState = new State(newStateName);
        if (stateType == StateType.FINAL)
            nfa.addFinalState(newState);
        for (State nextState : nextStates) {
            for (Transition t : nextState.getTransitions()) {
                newState.addTransition(t);
            }
        }
        unhandledStates.add(newState);
        return newState;
    }

    private void getRemainingStates() {
        // for(State state:nfa.getStates()){
        // if (!isStateExist(handledStates, state))
        // unhandledStates.add(state);
        // }
        processUnhandledStates();
    }

    public NFA getNfa() {
        return nfa;
    }
}
