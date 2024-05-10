package cfgtopda;

import java.util.*;

public class PDA {
    private Stack<String> pdaStack = new Stack<>();
    private Set<String> finalStates = new HashSet<>();
    private HashMap<String, List<pdaTransition>> pda = new HashMap<>();
    private String epsilon;
    private int lastState = 3;

    public PDA(String epsilon) {
        this.epsilon = epsilon;
        //initial step of any PDA
        pdaTransition tr = new pdaTransition("q1", epsilon, epsilon, "$");
        List<pdaTransition> trList = new LinkedList<pdaTransition>();
        trList.add(tr);
        this.pda.put("q0", trList);
    }

    public void CFGToPDA(CFG cfg) {
        String startSymbol = cfg.getStartSymbol();
        pdaTransition tr = new pdaTransition("q2", epsilon, epsilon, startSymbol);
        List<pdaTransition> trList = new LinkedList<>();
        trList.add(tr);
        pda.put("q1", trList);
        List<pdaTransition> rulesTransitionList = new LinkedList<>();
        Set<String> terminalSymbols = cfg.getTerminalSymbols();
        for (String terminal : terminalSymbols) {
            rulesTransitionList.add(new pdaTransition("q2", terminal, terminal, epsilon));
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
                            rulesTransitionList.add(new pdaTransition("q2", epsilon, key, l.get(0)));
                        } else {
                            pdaTransition t = new pdaTransition("q" + lastState, epsilon, key, l.get(l.size() - 1));
                            lastState++;
                            rulesTransitionList.add(t);
                            addComplexTransitions(l);
                        }
                    }
                } else {
                    pdaTransition t = new pdaTransition("q" + lastState, epsilon, key, value.get(value.size() - 1));
                    lastState++;
                    rulesTransitionList.add(t);
                    addComplexTransitions(value);
                }
            } else {
                rulesTransitionList.add(new pdaTransition("q2", epsilon, key, value.get(0)));
            }
        }
        pdaTransition lastTransition = new pdaTransition("qf", epsilon, "$", epsilon);
        rulesTransitionList.add(lastTransition);
        pda.put("q2", rulesTransitionList);

        List<pdaTransition> lastTransitionList = new LinkedList<>();
        finalStates.add("qf");
    }

    private void addComplexTransitions(List<String> list) {
        List<pdaTransition> tList;
        pdaTransition t;
        for (int i = list.size() - 2; i > 0; i--) {
            tList = new LinkedList<>();
            t = new pdaTransition("q" + lastState, epsilon, epsilon, list.get(i));
            tList.add(t);
            pda.put("q" + String.valueOf(lastState - 1), tList);
            lastState++;
        }
        tList = new LinkedList<>();
        t = new pdaTransition("q2", epsilon, epsilon, list.get(0));
        tList.add(t);
        pda.put("q" + String.valueOf(lastState - 1), tList);
    }

    public void printPDA() {
        for (String key : pda.keySet()) {
            System.out.println(key + ":");
            List<pdaTransition> v = pda.get(key);
            for (pdaTransition t : v) {
                System.out.println(t.toString());
            }
        }
    }
}
