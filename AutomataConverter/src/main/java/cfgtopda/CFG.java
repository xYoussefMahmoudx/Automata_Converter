package cfgtopda;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CFG {
    private Set<String> terminalSymbols = new HashSet<>();
    private Set<String> nonTerminalSymbols = new HashSet<>(); //non terminal symbols
    private String startSymbol;
    private HashMap<String, List<String>> cfg = new HashMap<>();// rule = key => list
    private final String logicalOR="|";
    private String epsilon;

    public CFG(String startSymbol,String epsilon) {
        this.startSymbol = startSymbol;
        this.epsilon=epsilon;
    }
    public void addRule(String LHS,List<String> RHS){
        this.nonTerminalSymbols.add(LHS);
        this.cfg.put(LHS,RHS);
    }
    public void addTerminalSymbol(String terminal){
        this.terminalSymbols.add(terminal);
    }
    public void addNonTerminalSymbol(String nonTerminal){
        this.nonTerminalSymbols.add(nonTerminal);
    }
    public Set<String> getTerminalSymbols() {
        return terminalSymbols;
    }

    public Set<String> getNonTerminalSymbols() {
        return nonTerminalSymbols;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public HashMap<String, List<String>> getCfg() {
        return cfg;
    }

    public void setTerminalSymbols(Set<String> terminalSymbols) {
        this.terminalSymbols = terminalSymbols;
    }

    public void setNonTerminalSymbols(Set<String> nonTerminalSymbols) {
        this.nonTerminalSymbols = nonTerminalSymbols;
    }
}
