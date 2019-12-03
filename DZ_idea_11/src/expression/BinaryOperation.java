package expression;

import java.util.Set;

public abstract class BinaryOperation implements FullExpression {
    protected String symbol;
    protected FullExpression firstExpression;
    protected FullExpression secondExpression;

    static final Set<String> ORDER = Set.of(
            "-", "/"
    );

    public BinaryOperation (FullExpression firstExpression, FullExpression secondExpression, String symbol) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.symbol = symbol;
    }

    @Override
    public String toMiniString() {
        StringBuilder into = new StringBuilder();
        toMiniString(into);
        return into.toString();
    }


    public void toMiniString(StringBuilder into) {
        bracketNeeding(into, firstExpression, false);
        into.append(" ");
        into.append(symbol);
        into.append(" ");
        bracketNeeding(into, secondExpression, true);
    }

    private void bracketNeeding(StringBuilder into, FullExpression exp, boolean isSecond) {
        String end = "";
        if (getLevel() > exp.getLevel() || ((symbol.equals("*") && exp.getSymbol().equals("/"))
                || (ORDER.contains(symbol) && getLevel() == exp.getLevel())) && isSecond)  {
            into.append('(');
            end = ")";
        }
        exp.toMiniString(into);
        into.append(end);
    }

    @Override
    public String toString() {
        StringBuilder into = new StringBuilder();
        toString(into);
        return into.toString();
    }

    @Override
    public void toString(StringBuilder into) {
        into.append('(');
        firstExpression.toString(into);
        into.append(" ");
        into.append(symbol);
        into.append(" ");
        secondExpression.toString(into);
        into.append(')');
    }

    @Override
    public int getLevel() {
        return Symbols.LEVEL.get(symbol);
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            BinaryOperation tmp = (BinaryOperation) obj;
            return firstExpression.equals(tmp.firstExpression)
                    && secondExpression.equals(tmp.secondExpression)
                    && symbol.equals(tmp.symbol);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 57 * firstExpression.hashCode() + 57 * 57 * secondExpression.hashCode() + symbol.hashCode();
    }
}
