package cfgtopda;

public class pdaTransition {
    String destination;
    String inputSymbol;
    String stackTop;
    String pushedSymbol;

    public pdaTransition(String destination, String inputSymbol, String stackTop, String pushedSymbol) {
        this.destination = destination;
        this.inputSymbol = inputSymbol;
        this.stackTop = stackTop;
        this.pushedSymbol = pushedSymbol;
    }

    @Override
    public String toString() {
        return ("{ " + destination + " ," + "( " + inputSymbol + " ," + stackTop + " -> " + pushedSymbol + " ) }");
    }
}
