package nfatodfa;

import java.util.List;

public class Main {
    public static NFA slide6() {
        NFA nfa = new NFA();
        State s0 = new State("q0");
        State s1 = new State("q1");
        State s2 = new State("q2");
        nfa.addState(s0);
        nfa.addState(s1);
        nfa.addState(s2);
        nfa.setInitialState(s0);
        nfa.addFinalState(s2);

        nfa.addTransition(s0, '0', s0);
        nfa.addTransition(s0, '1', s1);
        nfa.addTransition(s1, '0', s1);
        nfa.addTransition(s1, '1', s1);
        nfa.addTransition(s1, '0', s2);
        nfa.addTransition(s2, '0', s2);
        nfa.addTransition(s2, '1', s2);
        nfa.addTransition(s2, '1', s1);

        return nfa;
    }

    public static void main(String[] args) {
        NFA nfa = slide6();
        System.out.println(nfa);

        DFAConverter converter = new DFAConverter(nfa, true);
        List<List<State>> transitionTable = converter.convertToDFA();
        printTable(converter.getNfa(), transitionTable);
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
                if (nfa.getStateType(row.get(i)) == StateType.FINAL && i == 0)
                    System.out.print("* ");
                else if (nfa.getInitialState() == row.get(i) && i == 0)
                    System.out.print("> ");
                System.out.print(row.get(i) + "\t\t");
            }
            System.out.println();
        }
    }
}