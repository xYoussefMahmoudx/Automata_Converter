package cfgtopda;

import java.util.*;

public class PDA {
    private Stack<String> pdaStack = new Stack<>();
    private Set<String> finalStates = new HashSet<>();
    private HashMap<String, List<Transition>> pda = new HashMap<>();
    private String epsilon;
    private int lastState = 3;

    public PDA(String epsilon) {
        this.epsilon = epsilon;
        //initial step of any PDA
        Transition tr = new Transition("q1", epsilon, epsilon, "$");
        List<Transition> trList = new LinkedList<Transition>();
        trList.add(tr);
        this.pda.put("q0", trList);
    }

    public void CFGToPDA(CFG cfg) {
        String startSymbol = cfg.getStartSymbol();
        Transition tr = new Transition("q2", epsilon, epsilon, startSymbol);
        List<Transition> trList = new LinkedList<>();
        trList.add(tr);
        pda.put("q1", trList);
        List<Transition> rulesTransitionList = new LinkedList<>();
        Set<String> terminalSymbols = cfg.getTerminalSymbols();
        for (String terminal : terminalSymbols) {
            rulesTransitionList.add(new Transition("q2", terminal, terminal, epsilon));
        }
        HashMap<String, List<String>> cfgMap = cfg.getCfg();
        for (String key : cfgMap.keySet()) {
            List<String> value = cfgMap.get(key);
            if (value.size() > 1) {
                if (value.contains("|")) {
                    List<List<String>> allLists = new LinkedList<>();
                    List<String> temp = new LinkedList<>();
                    int valueSize = value.size();
                    for (int i = 0; i < valueSize; i++) {
                        if (value.get(i) == "|") {
                            allLists.add(temp);
                            temp = new LinkedList<>();
                        } else if (i == (valueSize - 1)) {
                            temp.add(value.get(i));
                            allLists.add(temp);
                            temp = new LinkedList<>();
                        } else {
                            temp.add(value.get(i));
                        }
                    }
                    for (List<String> l : allLists) {
                        if (l.size() == 1) {
                            rulesTransitionList.add(new Transition("q2", epsilon, key, l.get(0)));
                        } else {
                            Transition t = new Transition("q" + lastState, epsilon, key, l.get(l.size() - 1));
                            lastState++;
                            rulesTransitionList.add(t);
                            addComplexTransitions(l);
                        }
                    }
                } else {
                    Transition t = new Transition("q" + lastState, epsilon, key, value.get(value.size() - 1));
                    lastState++;
                    rulesTransitionList.add(t);
                    addComplexTransitions(value);
                }
            } else {
                rulesTransitionList.add(new Transition("q2", epsilon, key, value.get(0)));
            }
        }
        Transition lastTransition = new Transition("qf", epsilon, "$", epsilon);
        rulesTransitionList.add(lastTransition);
        pda.put("q2", rulesTransitionList);

        List<Transition> lastTransitionList = new LinkedList<>();
        finalStates.add("qf");
    }

    private void addComplexTransitions(List<String> list) {
        List<Transition> tList;
        Transition t;
        for (int i = list.size() - 2; i > 0; i--) {
            tList = new LinkedList<>();
            t = new Transition("q" + lastState, epsilon, epsilon, list.get(i));
            tList.add(t);
            pda.put("q" + String.valueOf(lastState - 1), tList);
            lastState++;
        }
        tList = new LinkedList<>();
        t = new Transition("q2", epsilon, epsilon, list.get(0));
        tList.add(t);
        pda.put("q" + String.valueOf(lastState - 1), tList);
    }

    public void printPDA() {
        for (String key : pda.keySet()) {
            System.out.println(key + ":");
            List<Transition> v = pda.get(key);
            for (Transition t : v) {
                System.out.println(t.toString());
            }
        }
    }
}
