package nfatodfa;

import java.util.List;

public class Main {
    public static NFA slide6(){
        NFA nfa = new NFA();
        State s0 = new State("q0", StateType.INITIAL);
        State s1 = new State("q1");
        State s2 = new State("q2", StateType.FINAL);
        nfa.addState(s0);
        nfa.addState(s1);
        nfa.addState(s2);
        nfa.addTransition(s0, "0", s0);
        nfa.addTransition(s0, "1", s1);
        nfa.addTransition(s1, "0", s1);
        nfa.addTransition(s1, "1", s1);
        nfa.addTransition(s1, "0", s2);
        nfa.addTransition(s2, "0", s2);
        nfa.addTransition(s2, "1", s2);
        nfa.addTransition(s2, "1", s1);
        nfa.sortAllTransitions();


        return nfa;
    }

    public static NFA slide10()  {
        NFA nfa = new NFA();
        State s0 = new State("q0", StateType.INITIAL);
        State s1 = new State("q1", StateType.FINAL);

        nfa.addState(s0);
        nfa.addState(s1);
        nfa.addTransition(s0, "0", s0);
        nfa.addTransition(s0, "0", s1);
        nfa.addTransition(s0, "1", s1);
        nfa.addTransition(s1, "1", s0);
        nfa.addTransition(s1, "1", s1);

        nfa.sortAllTransitions();
        System.out.println(nfa);
        return nfa;
    }

    public static NFA neso1(){
        NFA nfa = new NFA();
        State a = new State("A", StateType.INITIAL);
        State b = new State("B", StateType.FINAL);

        nfa.addState(a);
        nfa.addState(b);

        nfa.addTransition(a, "0", a);
        nfa.addTransition(a, "1", a);
        nfa.addTransition(a, "1", b);

        return nfa;
    }

    public static NFA neso2(){
        NFA nfa = new NFA();
        State a = new State("A", StateType.INITIAL);
        State b = new State("B");
        State c = new State("C", StateType.FINAL);

        nfa.addState(a);
        nfa.addState(b);
        nfa.addState(c);

        nfa.addTransition(a, "a", a);
        nfa.addTransition(a, "a", b);
        nfa.addTransition(a, "b", c);

        nfa.addTransition(b, "a", a);
        nfa.addTransition(b, "b", b);

        nfa.addTransition(c, "b", a);
        nfa.addTransition(c, "b", b);

        return nfa;
    }

    public static NFA neso3(){
        NFA nfa = new NFA();
        State a = new State("A", StateType.INITIAL);
        State b = new State("B");
        State c = new State("C", StateType.FINAL);

        nfa.addState(a);
        nfa.addState(b);
        nfa.addState(c);

        nfa.addTransition(a, "0", a);
        nfa.addTransition(a, "1", a);
        nfa.addTransition(a, "0", b);

        nfa.addTransition(b, "1", c);

        return nfa;
    }

    public static NFA neso4(){
        NFA nfa = new NFA();
        State a = new State("A", StateType.INITIAL);
        State b = new State("B");
        State c = new State("C", StateType.FINAL);

        nfa.addState(a);
        nfa.addState(b);
        nfa.addState(c);

        nfa.addTransition(a, "0", a);
        nfa.addTransition(a, "1", a);
        nfa.addTransition(a, "1", b);

        nfa.addTransition(b, "0", c);
        nfa.addTransition(b, "1", c);

        return nfa;
    }



    public static void main(String[] args) {
        NFA nfa = slide6();
        System.out.println(nfa);

        DFAConverter converter = new DFAConverter(nfa);
        System.out.println("--- Transition Table ---");
        System.out.print("\tState \t");
        StringBuilder sb = new StringBuilder();
        for (String s:nfa.getAlphabet())
            sb.append(s).append("\t\t");
        sb.setLength(sb.length()-2);
        System.out.println(sb.append("\t"));

        System.out.println("--------------------------");
        List<List<State>> tables = converter.getTransitionTableStates();
        for (List<State> l:tables) {
            System.out.print("\t");
            for (State s : l)
                System.out.print(s + "\t\t");
            System.out.println();
        }
    }
}