package cfgtopda;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String epsilon="eps";
        CFG myCFG=new CFG("S",epsilon);
        myCFG.addTerminalSymbol("a");
        myCFG.addTerminalSymbol("b");
        List<String> rule = new LinkedList<String>();
        rule.add("a");
        rule.add("A");
        rule.add("b");
        myCFG.addRule("S",rule);
        rule=new LinkedList<String>();
        rule.add("a");
        rule.add("A");
        rule.add("b");
        rule.add("|");
        rule.add(epsilon);
        myCFG.addRule("A",rule);
        System.out.println("CFG:");
        System.out.println(myCFG.getCfg());
        PDA myPDA=new PDA(epsilon);
        myPDA.CFGToPDA(myCFG);
        System.out.println("PDA:");
        myPDA.printPDA();
    }
}