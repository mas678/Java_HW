package expression;

public abstract class BinaryOperation implements CommonExpression, BinaryFunction {
    protected String symbol;
    protected int level;
    protected boolean order;
    protected CommonExpression firstExpression;
    protected CommonExpression secondExpression;

    public BinaryOperation (CommonExpression firstExpression, CommonExpression secondExpression,
                            String symbol, boolean order, int level) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.symbol = symbol;
        this.level = level;
        this.order = order;
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

    private void bracketNeeding(StringBuilder into, CommonExpression exp, boolean isSecond) {
        String end = "";
        if (getLevel() > exp.getLevel()
                || (order && getLevel() == exp.getLevel() && isSecond))  {
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
        return level;
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
                    && secondExpression.equals(tmp.secondExpression);
        }
        return false;
    }

    @Override
    public int evaluate(int x) {
        return function(firstExpression.evaluate(x), secondExpression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return function(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }

    @Override
    public int hashCode() {
        return 57 * firstExpression.hashCode() + 57 * 57 * secondExpression.hashCode() + getClass().hashCode();
    }
}
