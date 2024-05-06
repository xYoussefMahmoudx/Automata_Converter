package nfatodfa;

public class Transition{
    private String symbol;
    private State nextState;

    public Transition(String symbol, State nextState) {
        this.symbol = symbol;
        this.nextState = nextState;
    }

    public String getSymbol() {
        return symbol;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState){
        this.nextState = nextState;
    }

    @Override
    public String toString() {
        return "(" + symbol + " -> " + nextState + ")";
    }
}
